package controllers

import javax.inject.{Inject, Singleton}
import models.{Order, OrderForm, OrderFormData, UpdateOrderForm}
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
                                cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

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

  def updateOrder: Action[AnyContent] = Action.async { implicit request =>
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


  def createOrderFromJson(): Action[AnyContent] = Action.async { implicit request =>
    val json = request.body.asJson.get
    val order = json.as[OrderFormData]
    orderRepository.create(order.shipmentType,order.account,order.paymentType,order.coupon)
      .map(o=> Json.toJson(o))
      .map(json=>Created(json))
  }
}
