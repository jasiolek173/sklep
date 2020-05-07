package models.tables

import models.OrderItem
import slick.jdbc.SQLiteProfile.api._

class OrderItemTable(tag: Tag) extends Table[OrderItem](tag, "order_item") {
  val orderQuery = TableQuery[OrderTable]

  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def order = column[Int]("order")

  def name = column[String]("name")

  def description = column[String]("description")

  def categoryName = column[String]("category_name")

  def brandName = column[String]("brand_name")

  def imageUrl = column[String]("image_url")

  def quantity = column[Int]("quantity")

  def priceUnit = column[BigDecimal]("price_unit")

  def orderFk = foreignKey("order_fk", order, orderQuery)(_.id)

  def * = (id, order, name, description, categoryName, brandName, imageUrl, quantity, priceUnit) <> ((OrderItem.apply _).tupled, OrderItem.unapply)
}
