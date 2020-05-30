package models

import play.api.data.Form
import play.api.data.Forms.{mapping, _}
import play.api.libs.json.{Json, OFormat}


case class Order(id: Int, account: Int, shipmentType: Int, paymentType: Int, coupon: Int)

case class OrderFormData(account: Int, shipmentType: Int, paymentType: Int, coupon: Int)

case class OrderJsonFormData(shipmentType: Int, paymentType: Int, coupon: Int)

case class OrderRepresentation(id: Int, account: String, shipment: String, payment: String, coupon: String)

case class OrderAllInformation(id: Int, account: String, shipment: String, payment: String, coupon: String, orderItems: Seq[OrderItem])

object OrderForm {
  val form: Form[OrderFormData] = Form {
    mapping(
      "account" -> number,
      "shipmentType" -> number,
      "paymentType" -> number,
      "coupon" -> number
    )(OrderFormData.apply)(OrderFormData.unapply)
  }
}

object UpdateOrderForm {
  val form: Form[Order] = Form {
    mapping(
      "id" -> number,
      "account" -> number,
      "shipmentType" -> number,
      "paymentType" -> number,
      "coupon" -> number
    )(Order.apply)(Order.unapply)
  }
}

object OrderJsonFormData {
  implicit val format: OFormat[OrderJsonFormData] = Json.format[OrderJsonFormData]
}

object Order {
  implicit val format: OFormat[Order] = Json.format[Order]
}

object OrderRepresentation {
  implicit val format: OFormat[OrderRepresentation] = Json.format[OrderRepresentation]
}

object OrderAllInformation {
  implicit val format: OFormat[OrderAllInformation] = Json.format[OrderAllInformation]
}