package repositories

import javax.inject.{Inject, Singleton}
import models.Payment
import models.tables.PaymentTable
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PaymentRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  val payment = TableQuery[PaymentTable]

  def create(amount: BigDecimal, order: Int): Future[Payment] = db.run {
    (payment.map(c => (c.amount, c.order))
      returning payment.map(_.id)
      into { case ((amount, order), id) => Payment(id, amount, order) }
      ) += (amount, order)
  }

  def list(): Future[Seq[Payment]] = db.run {
    payment.result
  }

  def getById(id: Int): Future[Payment] = db.run {
    payment.filter(_.id === id).result.head
  }

  def getByIdOption(id: Int): Future[Option[Payment]] = db.run {
    payment.filter(_.id === id).result.headOption
  }

  def delete(id: Int): Future[Unit] = db.run(payment.filter(_.id === id).delete).map(_ => ())

  def update(id: Int, newPayment: Payment): Future[Unit] = {
    val paymentToUpdate: Payment = newPayment.copy(id)
    db.run(payment.filter(_.id === id).update(paymentToUpdate)).map(_ => ())
  }

}
