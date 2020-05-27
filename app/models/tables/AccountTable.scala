package models.tables

import models.Account
import slick.jdbc.SQLiteProfile.api._

class AccountTable(tag: Tag) extends Table[Account](tag, "account") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def email = column[String]("email")
  def firstName = column[String]("first_name")
  def lastName = column[String]("last_name")
  def providerId = column[String]("provider_id")
  def providerKey = column[String]("provider_key")

  def * = (id, email, firstName,lastName,providerId,providerKey) <> ((Account.apply _).tupled, Account.unapply)
}
