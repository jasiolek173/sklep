package models

import play.api.data.Form
import play.api.data.Forms.{mapping, _}

case class OrderItem(id: Int, order: Int, name: String, description: String, categoryName: String, brandName: String, imageUrl: String, quantity: Int, priceUnit: BigDecimal)

case class OrderItemFormData(order: Int, name: String, description: String, categoryName: String, brandName: String, imageUrl: String, quantity: Int, priceUnit: BigDecimal)

case class AddOrderItemFormData(order: Int, product: Int, quantity: Int)

object OrderItemForm {
  val form: Form[OrderItemFormData] = Form {
    mapping(
      "order" -> number,
      "name" -> nonEmptyText,
      "description" -> nonEmptyText,
      "categoryName" -> nonEmptyText,
      "brandName" -> nonEmptyText,
      "imageUrl" -> nonEmptyText,
      "quantity" -> number,
      "priceUnit" -> bigDecimal
    )(OrderItemFormData.apply)(OrderItemFormData.unapply)
  }
}

object UpdateOrderItemForm {
  val form: Form[OrderItem] = Form {
    mapping(
      "id" -> number,
      "order" -> number,
      "name" -> nonEmptyText,
      "description" -> nonEmptyText,
      "categoryName" -> nonEmptyText,
      "brandName" -> nonEmptyText,
      "imageUrl" -> nonEmptyText,
      "quantity" -> number,
      "priceUnit" -> bigDecimal
    )(OrderItem.apply)(OrderItem.unapply)
  }
}

object AddOrderItemForm {
  val form: Form[AddOrderItemFormData] = Form {
    mapping(
      "order" -> number,
      "product" -> number,
      "quantity" -> number
    )(AddOrderItemFormData.apply)(AddOrderItemFormData.unapply)
  }
}