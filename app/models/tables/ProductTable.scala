package models.tables

import models.Product
import slick.jdbc.SQLiteProfile.api._

class ProductTable(tag: Tag) extends Table[Product](tag, "product") {
  val cat = TableQuery[CategoryTable]
  val brandQuery = TableQuery[BrandTable]

  /** The ID column, which is the primary key, and auto incremented */
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  /** The name column */
  def name = column[String]("name")

  /** The age column */
  def description = column[String]("description")

  def category = column[Int]("category")

  def brand = column[Int]("brand")

  def price = column[BigDecimal]("price")

  def category_fk = foreignKey("cat_fk", category, cat)(_.id)

  def brand_fk = foreignKey("brand_fk", brand, brandQuery)(_.id)

  def * = (id, name, description, category,brand,price) <> ((Product.apply _).tupled, Product.unapply)

}