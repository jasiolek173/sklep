package models.tables

import models.Order
import slick.jdbc.SQLiteProfile.api._

class OrderTable(tag: Tag) extends Table[Order](tag, "order") {

  val ship = TableQuery[ShipmentTypeTable]
  val acc = TableQuery[AccountTable]
  val paymentTypeTable = TableQuery[PaymentTypeTable]
  val couponTable = TableQuery[CouponTable]
  val paymentTable = TableQuery[PaymentTable]

  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def account = column[Int]("client")

  def shipmentType = column[Int]("shipment")

  def paymentType = column[Int]("payment")

  def coupon = column[Int]("coupon")

  def accountFk = foreignKey("account_fk", account, acc)(_.id)

  def shipmentTypeFk = foreignKey("shipment_type_fk", shipmentType, ship)(_.id)

  def paymentTypeFk = foreignKey("payment_type_fk", paymentType, paymentTypeTable)(_.id)

  def couponFk = foreignKey("coupon_fk", coupon, couponTable)(_.id)

  def * = (id, account, shipmentType, paymentType, coupon) <> ((Order.apply _).tupled, Order.unapply)

}
