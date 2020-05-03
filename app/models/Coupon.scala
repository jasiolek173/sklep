package models

import play.api.data.Form
import play.api.data.Forms.{mapping, _}
import play.api.data.format.Formats._
import play.api.libs.json.Json

case class Coupon(id: Int, name: String, description: String, percentage: Double)

case class CouponFormData(name: String, description: String, percentage: Double)

object CouponForm {
  val form: Form[CouponFormData] = Form {
    mapping(
      "name" -> nonEmptyText,
      "description" -> nonEmptyText,
      "percentage" -> of(doubleFormat)
    )(CouponFormData.apply)(CouponFormData.unapply)
  }
}

object UpdateCouponForm {
  val form: Form[Coupon] = Form {
    mapping(
      "id" -> number,
      "name" -> nonEmptyText,
      "description" -> nonEmptyText,
      "percentage" -> of(doubleFormat)
    )(Coupon.apply)(Coupon.unapply)
  }
}

object Coupon {
  implicit val couponForm = Json.format[Coupon]
}
