package models

case class Password(id: Int, providerId: String, providerKey: String, hasher: String, password: String, salt:Option[String])
