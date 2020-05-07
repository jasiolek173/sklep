package repositories

import javax.inject.{Inject, Singleton}
import models.Product
import models.tables.ProductTable
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ProductRepository @Inject()(dbConfigProvider: DatabaseConfigProvider, categoryRepository: CategoryRepository)(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  private val product = TableQuery[ProductTable]

  def createProduct(name: String, description: String, category: Int, brand: Int, price: BigDecimal, imgUrl: String): Future[Product] = db.run {
    (product.map(p => (p.name, p.description, p.category, p.brand, p.price, p.imageUrl))
      returning product.map(_.id)
      into { case ((name, description, category, brand, price, imgUrl), id) => Product(id, name, description, category, brand, price, imgUrl) }
      ) += (name, description, category, brand, price, imgUrl)
  }

  def list(): Future[Seq[Product]] = db.run {
    product.result
  }

  def getByCategory(categoryId: Int): Future[Seq[Product]] = db.run {
    product.filter(_.category === categoryId).result
  }

  def getById(id: Int): Future[Product] = db.run {
    product.filter(_.id === id).result.head
  }

  def getByIdOption(id: Int): Future[Option[Product]] = db.run {
    product.filter(_.id === id).result.headOption
  }

  def getByCategories(categoryIds: List[Int]): Future[Seq[Product]] = db.run {
    product.filter(_.category inSet categoryIds).result
  }

  def delete(id: Int): Future[Unit] = db.run(product.filter(_.id === id).delete).map(_ => ())

  def update(id: Int, newProduct: Product): Future[Unit] = {
    val productToUpdate: Product = newProduct.copy(id)
    db.run(product.filter(_.id === id).update(productToUpdate)).map(_ => ())
  }
}
