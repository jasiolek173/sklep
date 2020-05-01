package models.tables

import models.Account
import slick.jdbc.SQLiteProfile.api._

class AccountTable(tag: Tag) extends Table[Account](tag, "account") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def login = column[String]("login")

  def password = column[String]("password")

  def * = (id, login, password) <> ((Account.apply _).tupled, Account.unapply)
}
