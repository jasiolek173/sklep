package models

import play.api.data.Form
import play.api.data.Forms.{mapping, _}
import play.api.libs.json.Json

case class PaymentType(id: Int, name: String, description: String)

case class PaymentTypeFormData(name: String, description: String)

object PaymentTypeForm {
  val form: Form[PaymentTypeFormData] = Form {
    mapping(
      "name" -> nonEmptyText,
      "description" -> nonEmptyText
    )(PaymentTypeFormData.apply)(PaymentTypeFormData.unapply)
  }
}

object UpdatePaymentTypeForm {
  val form: Form[PaymentType] = Form {
    mapping(
      "id" -> number,
      "name" -> nonEmptyText,
      "description" -> nonEmptyText
    )(PaymentType.apply)(PaymentType.unapply)
  }
}

object PaymentType {
  implicit val paymentTypeFormat = Json.format[PaymentType]
}