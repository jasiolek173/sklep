package controllers

import com.mohiva.play.silhouette.api.actions.SecuredRequest
import javax.inject.{Inject, Singleton}
import models._
import play.api.libs.json.Json
import play.api.mvc._
import repositories._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class OrderController @Inject()(categoryRepository: CategoryRepository,
                                shipmentRepository: ShipmentTypeRepository,
                                paymentRepository: PaymentTypeRepository,
                                accountRepository: AccountRepository,
                                orderRepository: OrderRepository,
                                couponRepository: CouponRepository,
                                orderItemRepository: OrderItemRepository,
                                scc: DefaultSilhouetteControllerComponents)(implicit ec: ExecutionContext) extends SilhouetteController(scc) {

  def getOrderWithId(id: Int): Action[AnyContent] = Action.async { implicit request =>
    orderRepository.getById(id).flatMap(order =>
      shipmentRepository.getById(order.shipmentType).flatMap(shipment =>
        accountRepository.getById(order.account).flatMap(client =>
          paymentRepository.getById(order.paymentType).flatMap(payment =>
            couponRepository.getById(order.coupon).flatMap(coupon =>
              orderItemRepository.listByOrderId(order.id).map(orderItems =>
                Ok(views.html.order.order(order, shipment, client, payment, coupon, orderItems))
              )
            )
          )
        )
      )
    )
  }

  def createOrder: Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    accountRepository.list().flatMap(accounts =>
      shipmentRepository.list().flatMap(shipmentTypes =>
        paymentRepository.list().flatMap(paymentTypes =>
          couponRepository.list().flatMap(coupons =>
            OrderForm.form.bindFromRequest.fold(errorForm => {
              Future.successful(
                BadRequest(views.html.order.orderadd(errorForm, accounts, shipmentTypes, paymentTypes, coupons))
              )
            },
              order => {
                orderRepository.create(order.shipmentType, order.account, order.paymentType, order.coupon).map { _ =>
                  Redirect(routes.OrderController.createOrderForm()).flashing("success" -> "order.created")
                }
              }
            )
          )
        )
      )
    )
  }

  def updateOrder(): Action[AnyContent] = Action.async { implicit request =>
    shipmentRepository.list().flatMap(shipmentTypes =>
      paymentRepository.list().flatMap(paymentTypes =>
        couponRepository.list().flatMap(coupons =>
          UpdateOrderForm.form.bindFromRequest.fold(
            errorForm => {
              Future.successful(
                BadRequest(views.html.order.orderupdate(errorForm, shipmentTypes, paymentTypes, coupons))
              )
            },
            order => {
              orderRepository.update(order.id, Order(order.id, order.account, order.shipmentType, order.paymentType, order.coupon)).map { _ =>
                Redirect(routes.OrderController.updateOrderForm(order.id)).flashing("success" -> "order updated")
              }
            }
          )
        )
      )
    )
  }

  def getAllOrders: Action[AnyContent] = Action.async { implicit request =>
    orderRepository.list().map(orders => Ok(views.html.order.orders(orders)))
  }

  def removeOrderWithId(id: Int): Action[AnyContent] = Action {
    orderRepository.delete(id)
    Redirect("/get_orders")
  }

  def createOrderForm: Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    accountRepository.list().flatMap(accounts =>
      shipmentRepository.list().flatMap(shipmentTypes =>
        paymentRepository.list().flatMap(paymentTypes =>
          couponRepository.list().map(coupons =>
            Ok(views.html.order.orderadd(OrderForm.form, accounts, shipmentTypes, paymentTypes, coupons))
          )
        )
      )
    )
  }

  def updateOrderForm(id: Int): Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    shipmentRepository.list().flatMap(shipmentTypes =>
      paymentRepository.list().flatMap(paymentTypes =>
        couponRepository.list().flatMap(coupons =>
          orderRepository.getByIdOption(id).map {
            case Some(p) =>
              val prodForm = UpdateOrderForm.form.fill(Order(p.id, p.account, p.shipmentType, p.paymentType, p.coupon))
              Ok(views.html.order.orderupdate(prodForm, shipmentTypes, paymentTypes, coupons))
            case None =>
              Redirect(routes.OrderController.getAllOrders())
          }
        )
      )
    )
  }


  def createOrderFromJson(): Action[AnyContent] = SecuredAction.async { implicit request: SecuredRequest[EnvType, AnyContent] =>
    val json = request.body.asJson.get
    val order = json.as[OrderJsonFormData]
    val user = request.identity.id
    orderRepository.create(order.shipmentType, user, order.paymentType, order.coupon)
      .map(o => Json.toJson(o))
      .map(json => Created(json))
  }

  private def mapOrderToOrderRepresentation(o: Order): Future[OrderRepresentation] =
    shipmentRepository.getById(o.shipmentType).flatMap(shipment =>
      accountRepository.getById(o.account).flatMap(client =>
        paymentRepository.getById(o.paymentType).flatMap(payment =>
          couponRepository.getById(o.coupon).map(coupon =>
            OrderRepresentation(o.id, client.firstName + " " + client.lastName, shipment.name, payment.name, coupon.name)
          )
        )
      )
    )

  def mapFutures[A, B](xs: Future[Seq[A]], f: A => Future[B]): Future[Seq[B]] = {
    for {
      list <- xs
      mapped <- Future.sequence(list map f)
    } yield mapped
  }

  def getJsonAllOrders: Action[AnyContent] = SecuredAction.async { implicit request: SecuredRequest[EnvType, AnyContent] =>
    val user = request.identity.id
    val list = orderRepository.getByAccountId(user)
    val ordersRep = mapFutures(list,mapOrderToOrderRepresentation)
    ordersRep.map(orders => Json.toJson(orders)).map(json => Ok(json))
  }

  def getJsonOrder(orderId: Int): Action[AnyContent] = SecuredAction.async { implicit request: SecuredRequest[EnvType, AnyContent] =>
    val user = request.identity.id
    val order = orderRepository.getByAccountIdThenOrderId(user, orderId)
    order.flatMap {
      case Some(o) =>
        orderItemRepository.listByOrderId(orderId).flatMap(items =>
          shipmentRepository.getById(o.shipmentType).flatMap(shipment =>
            accountRepository.getById(o.account).flatMap(client =>
              paymentRepository.getById(o.paymentType).flatMap(payment =>
                couponRepository.getById(o.coupon).map(coupon =>
                  Json.toJson(OrderAllInformation(o.id, client.firstName + " " + client.lastName, shipment.name, payment.name, coupon.name, items))
                ).map(json => Ok(json))
              )
            )
          )
        )
      case None =>
        Future(NotFound("Not found order with id!"))
    }
  }
}
