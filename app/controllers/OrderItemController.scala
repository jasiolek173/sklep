package controllers

import com.mohiva.play.silhouette.api.actions.SecuredRequest
import javax.inject.{Inject, Singleton}
import models.{AddOrderItemForm, AddOrderItemFormData, OrderItem, UpdateOrderItemForm}
import play.api.libs.json.Json
import play.api.mvc._
import repositories._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class OrderItemController @Inject()(orderItemRepository: OrderItemRepository,
                                    productRepository: ProductRepository,
                                    categoryRepository: CategoryRepository,
                                    brandRepository: BrandRepository,
                                    orderRepository: OrderRepository,
                                    scc: DefaultSilhouetteControllerComponents)(implicit ec: ExecutionContext) extends SilhouetteController(scc) {

  def getOrderItemWithId(id: Int): Action[AnyContent] = Action.async { implicit request =>
    orderItemRepository.getByIdOption(id).map {
      case Some(p) => Ok(views.html.order_item.orderitem(p))
      case None => Redirect(routes.OrderItemController.getAllOrderItems())
    }
  }

  def getAllOrderItems: Action[AnyContent] = Action.async { implicit request =>
    orderItemRepository.list().map(orderItems => Ok(views.html.order_item.orderitems(orderItems)))
  }

  def removeOrderItemWithId(id: Int): Action[AnyContent] = Action.async {
    orderItemRepository.getByIdOption(id).map {
      case Some(p) =>
        val orderId = p.order
        orderItemRepository.delete(id)
        Redirect(routes.OrderController.getOrderWithId(orderId))
      case None => Redirect(routes.OrderItemController.getAllOrderItems())
    }
  }

  def createOrderItem: Action[AnyContent] = Action.async { implicit request =>
    productRepository.list().flatMap(products =>
      orderRepository.list().flatMap(orders =>
        AddOrderItemForm.form.bindFromRequest.fold(errorForm => {
          Future.successful(
            BadRequest(views.html.order_item.orderitemadd(errorForm, products, orders, orders.head.id))
          )
        },
          orderItem => {
            val p = products.filter(_.id == orderItem.product).head
            categoryRepository.getById(p.category).flatMap(category =>
              brandRepository.getById(p.brand).flatMap(brand =>
                orderItemRepository.create(orderItem.order, category.name, brand.name,orderItem.quantity, p).map { _ =>
                  Redirect(routes.OrderItemController.createOrderItemForm(orderItem.order)).flashing("success" -> "orderItem.created")
                }
              )
            )
          }
        )
      )
    )
  }

  def createOrderItemForm(orderId: Int): Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    productRepository.list().flatMap(products =>
      orderRepository.list().map(orders =>
        Ok(views.html.order_item.orderitemadd(AddOrderItemForm.form, products, orders, orderId))
      )
    )
  }

  def updateOrderItem(): Action[AnyContent] = Action.async { implicit request =>
    UpdateOrderItemForm.form.bindFromRequest.fold(
      errorForm => {
        Future.successful(
          BadRequest(views.html.order_item.orderitemupdate(errorForm))
        )
      },
      orderItem => {
        orderItemRepository.update(orderItem.id, OrderItem(orderItem.id, orderItem.order, orderItem.name, orderItem.description, orderItem.categoryName, orderItem.brandName, orderItem.imageUrl, orderItem.quantity, orderItem.priceUnit)).map { _ =>
          Redirect(routes.OrderItemController.updateOrderItemForm(orderItem.id)).flashing("success" -> "orderItem updated")
        }
      }
    )
  }

  def updateOrderItemForm(id: Int): Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    orderItemRepository.getByIdOption(id).map {
      case Some(p) =>
        val prodForm = UpdateOrderItemForm.form.fill(OrderItem(p.id, p.order, p.name, p.description, p.categoryName, p.brandName, p.imageUrl, p.quantity, p.priceUnit))
        Ok(views.html.order_item.orderitemupdate(prodForm))
      case None =>
        Redirect(routes.OrderItemController.getAllOrderItems())
    }
  }

  //  REST
  def createOrderItemFromJson(): Action[AnyContent] = SecuredAction.async { implicit request: SecuredRequest[EnvType, AnyContent] =>

    val json = request.body.asJson.get
    val order: List[AddOrderItemFormData] = json.as[List[AddOrderItemFormData]]
    order.map(n =>
      productRepository.getById(n.product).flatMap(p =>
        orderRepository.getById(n.order).flatMap(o =>
          categoryRepository.getById(p.category).flatMap(category =>
            brandRepository.getById(p.brand).flatMap(brand =>
              orderItemRepository.create(o.id, category.name, brand.name, n.quantity, p))
            )
          )
        )
      )
    Future(Ok("ok"))
  }
}
