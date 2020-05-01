package models.tables

import models.ShipmentType
import slick.jdbc.SQLiteProfile.api._

class ShipmentTypeTable(tag: Tag) extends Table[ShipmentType](tag, "shipment_type") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def name = column[String]("name")

  def description = column[String]("description")

  def price = column[BigDecimal]("price")

  def * = (id, name, description, price) <> ((ShipmentType.apply _).tupled, ShipmentType.unapply)
}
