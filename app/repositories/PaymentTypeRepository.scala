package repositories

import javax.inject.{Inject, Singleton}
import models.PaymentType
import models.tables.PaymentTypeTable
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PaymentTypeRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  val paymentType = TableQuery[PaymentTypeTable]

  def create(name: String, description: String): Future[PaymentType] = db.run {
    (paymentType.map(c => (c.name, c.description))
      returning paymentType.map(_.id)
      into { case ((name, description), id) => PaymentType(id, name, description) }
      ) += (name, description)
  }

  def list(): Future[Seq[PaymentType]] = db.run {
    paymentType.result
  }

  def getById(id: Int): Future[PaymentType] = db.run {
    paymentType.filter(_.id === id).result.head
  }

  def getByIdOption(id: Int): Future[Option[PaymentType]] = db.run {
    paymentType.filter(_.id === id).result.headOption
  }

  def delete(id: Int): Future[Unit] = db.run(paymentType.filter(_.id === id).delete).map(_ => ())

  def update(id: Int, newPaymentType: PaymentType): Future[Unit] = {
    val paymentTypeToUpdate: PaymentType = newPaymentType.copy(id)
    db.run(paymentType.filter(_.id === id).update(paymentTypeToUpdate)).map(_ => ())
  }

}