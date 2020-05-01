package models

import play.api.data.Form
import play.api.data.Forms.{mapping, _}
import play.api.libs.json.Json

case class Category(id: Int, name: String)

case class CategoryFormData(name: String)

object CategoryForm {
  val form: Form[CategoryFormData] = Form {
    mapping(
      "name" -> nonEmptyText
    )(CategoryFormData.apply)(CategoryFormData.unapply)
  }
}

object Category {
  implicit val categoryFormat = Json.format[Category]
}

