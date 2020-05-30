package controllers

import com.mohiva.play.silhouette.api._
import com.mohiva.play.silhouette.api.services.AuthenticatorResult
import models.Account
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

abstract class AbstractAuthController(
  scc: SilhouetteControllerComponents
)(implicit ex: ExecutionContext) extends SilhouetteController(scc) {

  protected def authenticateUser(user: Account)(implicit request: RequestHeader): Future[AuthenticatorResult] = {
    authenticatorService.create(LoginInfo(user.providerId,user.providerKey)).map(authenticator => authenticator).flatMap { authenticator =>
      eventBus.publish(LoginEvent(user, request))
      authenticatorService.init(authenticator).flatMap { v =>
        authenticatorService.embed(v, Ok("ok"))
      }
    }
  }
}
