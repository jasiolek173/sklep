package services

import java.util.UUID

import models.AuthToken
import org.joda.time.DateTime

import scala.collection.mutable
import scala.concurrent.Future

class AuthTokenDAO {

  def find(id: UUID): Future[Option[AuthToken]] = Future.successful(AuthTokenDAO.tokens.get(id))

  def findExpired(dateTime: DateTime): Future[Seq[AuthToken]] = Future.successful {
    AuthTokenDAO.tokens.filter {
      case (_, token) =>
        token.expiry.isBefore(dateTime)
    }.values.toSeq
  }

  def save(token: AuthToken): Future[AuthToken] = {
    AuthTokenDAO.tokens += (token.id -> token)
    Future.successful(token)
  }

  def remove(id: UUID): Future[Unit] = {
    AuthTokenDAO.tokens -= id
    Future.successful(())
  }
}

object AuthTokenDAO {

  val tokens: mutable.HashMap[UUID, AuthToken] = mutable.HashMap()
}
