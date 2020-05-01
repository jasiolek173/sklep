package models.tables

import models.Category
import slick.jdbc.SQLiteProfile.api._

class CategoryTable(tag: Tag) extends Table[Category](tag, "category") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def name = column[String]("name")

  def * = (id, name) <> ((Category.apply _).tupled, Category.unapply)
}
