package repositories

import javax.inject.{Inject, Singleton}
import models.Coupon
import models.tables.CouponTable
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CouponRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  val coupon = TableQuery[CouponTable]

  def create(name: String, description: String, percentage: Double): Future[Coupon] = db.run {
    (coupon.map(c => (c.name, c.description, c.percentage))
      returning coupon.map(_.id)
      into { case ((name, description, percentage), id) => Coupon(id, name, description, percentage) }
      ) += (name, description, percentage)
  }

  def list(): Future[Seq[Coupon]] = db.run {
    coupon.result
  }

  def getById(id: Int): Future[Coupon] = db.run {
    coupon.filter(_.id === id).result.head
  }

  def getByIdOption(id: Int): Future[Option[Coupon]] = db.run {
    coupon.filter(_.id === id).result.headOption
  }

  def delete(id: Int): Future[Unit] = db.run(coupon.filter(_.id === id).delete).map(_ => ())

  def update(id: Int, newCoupon: Coupon): Future[Unit] = {
    val couponToUpdate: Coupon = newCoupon.copy(id)
    db.run(coupon.filter(_.id === id).update(couponToUpdate)).map(_ => ())
  }

}
