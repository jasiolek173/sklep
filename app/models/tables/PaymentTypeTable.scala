package models.tables

import models.PaymentType
import slick.jdbc.SQLiteProfile.api._

class PaymentTypeTable(tag: Tag) extends Table[PaymentType](tag, "payment_type") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def name = column[String]("name")

  def description = column[String]("description")

  def * = (id, name, description) <> ((PaymentType.apply _).tupled, PaymentType.unapply)
}
