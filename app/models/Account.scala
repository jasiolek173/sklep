package models

import play.api.data.Form
import play.api.data.Forms.{mapping, _}
import play.api.libs.json.Json

case class Account(id: Int, email: String, firstName: String, lastName: String, providerId: String, providerKey: String)

case class AccountFormData(email: String, firstName: String, lastName: String, providerId: String, providerKey: String)

object AccountForm {
  val form: Form[AccountFormData] = Form {
    mapping(
      "email" -> nonEmptyText,
      "firstName" -> nonEmptyText,
      "lastName" -> nonEmptyText,
      "providerId" -> nonEmptyText,
      "providerKey" -> nonEmptyText
    )(AccountFormData.apply)(AccountFormData.unapply)
  }
}

object UpdateAccountForm {
  val form: Form[Account] = Form {
    mapping(
      "id" -> number,
      "email" -> nonEmptyText,
      "firstName" -> nonEmptyText,
      "lastName" -> nonEmptyText,
      "providerId" -> nonEmptyText,
      "providerKey" -> nonEmptyText
    )(Account.apply)(Account.unapply)
  }
}

object Account {
  implicit val accountFormat = Json.format[Account]
}