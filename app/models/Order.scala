package models

import play.api.data.Form
import play.api.data.Forms.{mapping, _}


case class Order(id: Int, account: Int, shipmentType: Int, paymentType: Int, coupon: Int)

case class OrderFormData(account: Int, shipmentType: Int, paymentType: Int, coupon: Int)

case class OrderRepresentation(id: Int, account: String, shipment: String, payment: String, coupon: String)

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
