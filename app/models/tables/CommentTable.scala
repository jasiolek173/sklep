package models.tables

import models.Comment
import slick.jdbc.SQLiteProfile.api._

class CommentTable(tag: Tag) extends Table[Comment](tag, "comment") {
  val prod = TableQuery[ProductTable]

  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def owner = column[String]("owner")

  def content = column[String]("content")

  def product = column[Int]("product")

  def product_fk = foreignKey("product_fk", product, prod)(_.id)

  def * = (id, owner, content, product) <> ((Comment.apply _).tupled, Comment.unapply)

}
