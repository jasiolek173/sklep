package models.tables

import models.Password
import slick.jdbc.SQLiteProfile.api._


class PasswordTable(tag: Tag) extends Table[Password](tag, "password_info") {

  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def providerId = column[String]("provider_id")

  def providerKey = column[String]("provider_key")

  def hasher = column[String]("hasher")

  def password = column[String]("password")

  def salt = column[Option[String]]("salt")

  def * = (id, providerId, providerKey, hasher, password, salt) <> ((Password.apply _).tupled, Password.unapply)
}
