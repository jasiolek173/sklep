package services

import java.util.UUID

import com.mohiva.play.silhouette.api.util.Clock
import javax.inject.Inject
import models.AuthToken
import org.joda.time.DateTimeZone

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}
import scala.language.postfixOps

class AuthTokenServiceImpl @Inject()(
  authTokenDAO: AuthTokenDAO,
  clock: Clock)(implicit ex: ExecutionContext) {

  def create(userID: Int, expiry: FiniteDuration = 5 minutes): Future[AuthToken] = {
    val token = AuthToken(UUID.randomUUID(), userID, clock.now.withZone(DateTimeZone.UTC).plusSeconds(expiry.toSeconds.toInt))
    authTokenDAO.save(token)
  }

  def validate(id: UUID): Future[Option[AuthToken]] = authTokenDAO.find(id)

  def clean: Future[Seq[AuthToken]] = authTokenDAO.findExpired(clock.now.withZone(DateTimeZone.UTC)).flatMap { tokens =>
    Future.sequence(tokens.map { token =>
      authTokenDAO.remove(token.id).map(_ => token)
    })
  }
}
