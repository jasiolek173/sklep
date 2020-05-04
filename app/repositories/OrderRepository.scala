package repositories

import javax.inject.{Inject, Singleton}
import models.tables._
import models.{Order, OrderRepresentation}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class OrderRepository @Inject()(dbConfigProvider: DatabaseConfigProvider,
                                accountRepository: AccountRepository,
                                shipmentTypeRepository: ShipmentTypeRepository,
                                paymentTypeRepository: PaymentTypeRepository,
                                couponRepository: CouponRepository)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  val order = TableQuery[OrderTable]
  val accountTable = TableQuery[AccountTable]
  val shipmentTable = TableQuery[ShipmentTypeTable]
  val paymentTable = TableQuery[PaymentTypeTable]
  val couponTable = TableQuery[CouponTable]

  def create(shipmentType: Int, client: Int, paymentType: Int, coupon: Int): Future[Order] = db.run {
    (order.map(c => (c.account, c.shipmentType, c.paymentType, c.coupon))
      returning order.map(_.id)
      into { case ((account, shipmentType, paymentType, coupon), id) => Order(id, account, shipmentType, paymentType, coupon) }
      ) += (client, shipmentType, paymentType, coupon)
  }

  def list(): Future[Seq[Order]] = db.run {
    order.result
  }

//  def listOrderRepresentations(): Seq[OrderRepresentation] = {
//    list().flatMap(orders => orders.map(o => orderRepresentation(o.id)))
//  }
//
//  def orderRepresentation(orderId: Int): OrderRepresentation = {
//    getById(orderId).flatMap(o =>
//      accountRepository.getById(o.account).flatMap(client =>
//        shipmentTypeRepository.getById(o.shipmentType).flatMap(shipment =>
//          paymentTypeRepository.getById(o.paymentType).flatMap(payment =>
//            couponRepository.getById(o.coupon).flatMap(coupon =>
//              OrderRepresentation(o.id, client.login, shipment.name, payment.name, coupon.name)
//            )
//          )
//        )
//      )
//    )
//  }

  def getById(id: Int): Future[Order] = db.run {
    order.filter(_.id === id).result.head
  }

  def getByIdOption(id: Int): Future[Option[Order]] = db.run {
    order.filter(_.id === id).result.headOption
  }

  def delete(id: Int): Future[Unit] = db.run(order.filter(_.id === id).delete).map(_ => ())

  def update(id: Int, newOrder: Order): Future[Unit] = {
    val orderToUpdate: Order = newOrder.copy(id)
    db.run(order.filter(_.id === id).update(orderToUpdate)).map(_ => ())
  }
}
