package models

import play.api.data.Form
import play.api.data.Forms.{mapping, _}
import play.api.libs.json.Json

case class Payment(id: Int, amount: BigDecimal, order: Int)

case class PaymentFormData(amount: BigDecimal, order: Int)

object PaymentForm {
  val form: Form[PaymentFormData] = Form {
    mapping(
      "amount" -> bigDecimal,
      "order" -> number
    )(PaymentFormData.apply)(PaymentFormData.unapply)
  }
}

object UpdatePaymentForm {
  val form: Form[Payment] = Form {
    mapping(
      "id" -> number,
      "amount" -> bigDecimal,
      "order" -> number
    )(Payment.apply)(Payment.unapply)
  }
}

object Payment {
  implicit val paymentFormat = Json.format[Payment]
}
