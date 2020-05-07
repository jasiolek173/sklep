package models.tables

import models.Payment
import slick.jdbc.SQLiteProfile.api._

class PaymentTable(tag: Tag) extends Table[Payment](tag, "payment") {
  val orderQuery = TableQuery[OrderTable]

  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def amount = column[BigDecimal]("amount")

  def order=column[Int]("order")

  def orderFk = foreignKey("order_fk", order, orderQuery)(_.id)

  def * = (id, amount,order) <> ((Payment.apply _).tupled, Payment.unapply)
}