package models.tables

import models.{OrderItem, Payment}
import slick.jdbc.SQLiteProfile.api._

class OrderItemTable(tag: Tag) extends Table[OrderItem](tag, "order_item") {
  val orderQuery = TableQuery[OrderTable]
  val historicalProductQuery = TableQuery[HistoricalProductTable]

  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def order = column[Int]("order")

  def product=column[Int]("product")

  def quantity=column[Int]("quantity")

  def priceUnit= column[BigDecimal]("price_unit")

  def order_fk = foreignKey("order_fk", order, orderQuery)(_.id)
  def historical_product_fk = foreignKey("historical_product_fk", product, historicalProductQuery)(_.id)

  def * = (id,order,product,quantity,priceUnit) <> ((OrderItem.apply _).tupled, OrderItem.unapply)
}
