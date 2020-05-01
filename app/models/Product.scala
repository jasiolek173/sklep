package models

import play.api.data.Form
import play.api.data.Forms.{mapping, _}
import play.api.libs.json.Json


case class Product(id: Int, name: String, description: String, category: Int, brand: Int, price: BigDecimal)

case class ProductFormData(name: String, description: String, category: Int, brand: Int, price: BigDecimal)

object ProductForm {
  val form: Form[ProductFormData] = Form {
    mapping(
      "name" -> nonEmptyText,
      "description" -> nonEmptyText,
      "category" -> number,
      "brand"->number,
      "price"->bigDecimal
    )(ProductFormData.apply)(ProductFormData.unapply)
  }
}

object Product {
  implicit val productFormat = Json.format[Product]
}
