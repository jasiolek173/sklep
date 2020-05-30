package forms

import play.api.libs.json.{Json, OFormat}

case class SignInForm(
  email: String,
  password: String)

object SignInForm {
  implicit val format: OFormat[SignInForm] = Json.format[SignInForm]
}