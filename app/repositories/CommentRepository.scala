package repositories

import javax.inject.{Inject, Singleton}
import models.Comment
import models.tables.CommentTable
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CommentRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  private val comment = TableQuery[CommentTable]

  def create(owner: String, content: String, product: Int): Future[Comment] = db.run {
    (comment.map(c => (c.owner, c.content, c.product))
      returning comment.map(_.id)
      into { case ((owner, content, product), id) => Comment(id, owner, content, product) }
      ) += (owner, content, product)
  }

  def list(): Future[Seq[Comment]] = db.run {
    comment.result
  }

  def listByProductId(productId: Int): Future[Seq[Comment]] = db.run {
    comment.filter(_.product === productId).result
  }

  def getById(id: Int): Future[Comment] = db.run {
    comment.filter(_.id === id).result.head
  }

  def getByIdOption(id: Int): Future[Option[Comment]] = db.run {
    comment.filter(_.id === id).result.headOption
  }

  def delete(id: Int): Future[Unit] = db.run(comment.filter(_.id === id).delete).map(_ => ())

  def update(id: Int, newComment: Comment): Future[Unit] = {
    val commentToUpdate: Comment = newComment.copy(id)
    db.run(comment.filter(_.id === id).update(commentToUpdate)).map(_ => ())
  }
}
