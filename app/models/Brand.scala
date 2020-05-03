package models

import play.api.data.Form
import play.api.data.Forms.{mapping, _}

case class Brand(id: Int, name: String)

case class BrandFormData(name: String)

object BrandForm {

  val form: Form[BrandFormData] = Form {
    mapping(
      "name" -> nonEmptyText
    )(BrandFormData.apply)(BrandFormData.unapply)
  }
}

object UpdateBrandForm {

  val form: Form[Brand] = Form {
    mapping(
      "id" -> number,
      "name" -> nonEmptyText
    )(Brand.apply)(Brand.unapply)
  }
}