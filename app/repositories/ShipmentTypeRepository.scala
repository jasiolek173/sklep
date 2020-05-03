package repositories

import javax.inject.Inject
import models.ShipmentType
import models.tables.ShipmentTypeTable
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

class ShipmentTypeRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  val shipmentType = TableQuery[ShipmentTypeTable]

  def create(name: String, description: String, price: BigDecimal): Future[ShipmentType] = db.run {
    (shipmentType.map(c => (c.name, c.description, c.price))
      returning shipmentType.map(_.id)
      into { case ((name, description, price), id) => ShipmentType(id, name, description, price) }
      ) += (name, description, price)
  }

  def list(): Future[Seq[ShipmentType]] = db.run {
    shipmentType.result
  }

  def getById(id: Int): Future[ShipmentType] = db.run {
    shipmentType.filter(_.id === id).result.head
  }

  def getByIdOption(id: Int): Future[Option[ShipmentType]] = db.run {
    shipmentType.filter(_.id === id).result.headOption
  }

  def delete(id: Int): Future[Unit] = db.run(shipmentType.filter(_.id === id).delete).map(_ => ())

  def update(id: Int, newShipmentType: ShipmentType): Future[Unit] = {
    val shipmentTypeToUpdate: ShipmentType = newShipmentType.copy(id)
    db.run(shipmentType.filter(_.id === id).update(shipmentTypeToUpdate)).map(_ => ())
  }

}
