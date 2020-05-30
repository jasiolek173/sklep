package forms

import play.api.libs.json.{Json, OFormat}

case class SignUpForm(
  firstName: String,
  lastName: String,
  email: String,
  password: String)

object SignUpForm {
  implicit val format: OFormat[SignUpForm] = Json.format[SignUpForm]
}