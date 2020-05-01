package repositories

import javax.inject.{Inject, Singleton}
import models.Comment
import models.tables.{CommentTable, ProductTable}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CommentRepository @Inject()(dbConfigProvider: DatabaseConfigProvider, productRepository: ProductRepository)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  private val comment = TableQuery[CommentTable]
  private val product = TableQuery[ProductTable]

  def list(): Future[Seq[Comment]] = db.run {
    comment.result
  }


}
