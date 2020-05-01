package models.tables

import models.Brand
import slick.jdbc.SQLiteProfile.api._

class BrandTable(tag: Tag) extends Table[Brand](tag, "brand") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def name = column[String]("name")

  def * = (id, name) <> ((Brand.apply _).tupled, Brand.unapply)
}

