package models

import play.api.data.Form
import play.api.data.Forms.{mapping, _}
import play.api.libs.json.Json


case class Product(id: Int, name: String, description: String, category: Int, brand: Int, price: BigDecimal, imageUrl: String)

case class ProductFormData(name: String, description: String, category: Int, brand: Int, price: BigDecimal, imageUrl: String)

object ProductForm {
  val form: Form[ProductFormData] = Form {
    mapping(
      "name" -> nonEmptyText,
      "description" -> nonEmptyText,
      "category" -> number,
      "brand" -> number,
      "price" -> bigDecimal,
      "imageUrl" -> nonEmptyText
    )(ProductFormData.apply)(ProductFormData.unapply)
  }
}

object UpdateProductForm {
  val form: Form[Product] = Form {
    mapping(
      "id" -> number,
      "name" -> nonEmptyText,
      "description" -> nonEmptyText,
      "category" -> number,
      "brand" -> number,
      "price" -> bigDecimal,
      "imageUrl" -> nonEmptyText
    )(Product.apply)(Product.unapply)
  }
}

object Product {
  implicit val productFormat = Json.format[Product]
}
