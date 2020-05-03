package models

import play.api.data.Form
import play.api.data.Forms.{mapping, _}
import play.api.libs.json.Json

case class ShipmentType(id: Int, name: String, description: String, price: BigDecimal)

case class ShipmentTypeFormData(name: String, description: String, price: BigDecimal)

object ShipmentTypeForm {
  val form: Form[ShipmentTypeFormData] = Form {
    mapping(
      "name" -> nonEmptyText,
      "description" -> nonEmptyText,
      "price" -> bigDecimal
    )(ShipmentTypeFormData.apply)(ShipmentTypeFormData.unapply)
  }
}

object UpdateShipmentTypeForm {
  val form: Form[ShipmentType] = Form {
    mapping(
      "id" -> number,
      "name" -> nonEmptyText,
      "description" -> nonEmptyText,
      "price" -> bigDecimal
    )(ShipmentType.apply)(ShipmentType.unapply)
  }
}

object ShipmentType {
  implicit val shipmentTypeFormat = Json.format[ShipmentType]
}