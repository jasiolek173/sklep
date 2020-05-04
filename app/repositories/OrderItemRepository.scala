package repositories

import javax.inject.{Inject, Singleton}
import models.OrderItem
import models.tables.OrderItemTable
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class OrderItemRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  val orderItem = TableQuery[OrderItemTable]

  def create(order: Int, name: String, description: String, categoryName: String, brandName: String, imageUrl: String, quantity: Int, priceUnit: BigDecimal): Future[OrderItem] = db.run {
    (orderItem.map(c => (c.order, c.name, c.description, c.category_name, c.brand_name, c.image_url, c.quantity, c.priceUnit))
      returning orderItem.map(_.id)
      into { case ((order, name, description, categoryName, brandName, imageUrl, quantity, priceUnit), id) => OrderItem(id, order, name, description, categoryName, brandName, imageUrl, quantity, priceUnit) }
      ) += (order, name, description, categoryName, brandName, imageUrl, quantity, priceUnit)
  }

  def list(): Future[Seq[OrderItem]] = db.run {
    orderItem.result
  }

  def listByOrderId(id: Int): Future[Seq[OrderItem]] = db.run {
    orderItem.filter(_.order === id).result
  }

  def getById(id: Int): Future[OrderItem] = db.run {
    orderItem.filter(_.id === id).result.head
  }

  def getByIdOption(id: Int): Future[Option[OrderItem]] = db.run {
    orderItem.filter(_.id === id).result.headOption
  }

  def delete(id: Int): Future[Unit] = db.run(orderItem.filter(_.id === id).delete).map(_ => ())

  def update(id: Int, newOrderItem: OrderItem): Future[Unit] = {
    val orderItemToUpdate: OrderItem = newOrderItem.copy(id)
    db.run(orderItem.filter(_.id === id).update(orderItemToUpdate)).map(_ => ())
  }

}