package models.tables


import models.Coupon
import slick.jdbc.SQLiteProfile.api._

class CouponTable(tag: Tag) extends Table[Coupon](tag, "coupon") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def name = column[String]("name")

  def description = column[String]("description")

  def percentage = column[Double]("percentage")

  def * = (id, name,description, percentage) <> ((Coupon.apply _).tupled, Coupon.unapply)
}
