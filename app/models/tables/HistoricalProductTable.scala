package models.tables

import models.HistoricalProduct
import slick.jdbc.SQLiteProfile.api._

class HistoricalProductTable(tag: Tag) extends Table[HistoricalProduct](tag, "historical_product") {

  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def name = column[String]("name")

  def description = column[String]("description")

  def category_name = column[String]("category_name")

  def brand_name = column[String]("brand_name")

  def * = (id, name, description, category_name, brand_name) <> ((HistoricalProduct.apply _).tupled, HistoricalProduct.unapply)
}
