package models

import play.api.data.Form
import play.api.data.Forms.{mapping, _}
import play.api.libs.json.Json

case class Account(id: Int, login: String, password: String)

case class AccountFormData(login: String, password: String)

object AccountForm {
  val form: Form[AccountFormData] = Form {
    mapping(
      "login" -> nonEmptyText,
      "password" -> nonEmptyText
    )(AccountFormData.apply)(AccountFormData.unapply)
  }
}

object UpdateAccountForm {
  val form: Form[Account] = Form {
    mapping(
      "id" -> number,
      "login" -> nonEmptyText,
      "password" -> nonEmptyText
    )(Account.apply)(Account.unapply)
  }
}

object Account {
  implicit val accountFormat = Json.format[Account]
}