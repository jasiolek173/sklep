package repositories

import javax.inject.{Inject, Singleton}
import models.Brand
import models.tables.BrandTable
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class BrandRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  val brand = TableQuery[BrandTable]

  def create(name: String): Future[Brand] = db.run {
    (brand.map(c => (c.name))
      returning brand.map(_.id)
      into ((name, id) => Brand(id, name))
      ) += (name)
  }

  def list(): Future[Seq[Brand]] = db.run {
    brand.result
  }

  def getById(id: Int): Future[Brand] = db.run {
    brand.filter(_.id === id).result.head
  }

  def getByIdOption(id: Int): Future[Option[Brand]] = db.run {
    brand.filter(_.id === id).result.headOption
  }

  def delete(id: Int): Future[Unit] = db.run(brand.filter(_.id === id).delete).map(_ => ())

  def update(id: Int, newBrand: Brand): Future[Unit] = {
    val brandToUpdate: Brand = newBrand.copy(id)
    db.run(brand.filter(_.id === id).update(brandToUpdate)).map(_ => ())
  }

}
