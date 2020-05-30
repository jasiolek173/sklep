package controllers

import com.mohiva.play.silhouette.api.actions.SecuredRequest
import com.mohiva.play.silhouette.api.exceptions.ProviderException
import com.mohiva.play.silhouette.api.util.Credentials
import com.mohiva.play.silhouette.api.{LoginInfo, LogoutEvent, SignUpEvent}
import com.mohiva.play.silhouette.impl.exceptions.IdentityNotFoundException
import com.mohiva.play.silhouette.impl.providers.CredentialsProvider
import forms.{SignInForm, SignUpForm}
import javax.inject.Inject
import play.api.mvc._
import play.filters.csrf.{CSRF, CSRFAddToken}

import scala.concurrent.{ExecutionContext, Future}

class AuthenticationController @Inject()(
                                          token: CSRFAddToken,
                                          components: SilhouetteControllerComponents
                                        )(implicit ex: ExecutionContext) extends AbstractAuthController(components) {

  def signIn: Action[AnyContent] = token(Action.async { implicit request: Request[AnyContent] =>
    val json = request.body.asJson.get
    val signInData = json.as[SignInForm]
    val credentials = Credentials(signInData.email, signInData.password)
    val t = CSRF.getToken(request).get
    credentialsProvider.authenticate(credentials).flatMap { loginInfo =>
      userService.retrieve(loginInfo).flatMap {
        case Some(user) => authenticateUser(user).map(_.withCookies(Cookie(t.name, t.value, httpOnly = false)))
        case None => Future.failed(new IdentityNotFoundException("Couldn't find user"))
      }
    }.recover {
      case _: ProviderException =>
        Forbidden("invalid credentails")
    }
  })

  def signOut: Action[AnyContent] = SecuredAction.async { implicit request: SecuredRequest[EnvType, AnyContent] =>
    val result = Ok("logged out")
    eventBus.publish(LogoutEvent(request.identity, request))
    authenticatorService.discard(request.authenticator, result)
      .map(_.discardingCookies(DiscardingCookie(name = "csrfToken"),
        DiscardingCookie(name = "PLAY_SESSION"),
        DiscardingCookie(name = "OAuth2State")))
  }

  def signUp: Action[AnyContent] = UnsecuredAction.async { implicit request: Request[AnyContent] =>
    val json = request.body.asJson.get
    val signUpForm = json.as[SignUpForm]
    val loginInfo = LoginInfo(CredentialsProvider.ID, signUpForm.email)
    userService.retrieve(loginInfo).flatMap {
      case Some(user) =>
        Future.successful(Conflict("Account with this email already exists."))
      case None =>
        val authInfo = passwordHasherRegistry.current.hash(signUpForm.password)
        for {
          user <- userService.create(signUpForm.email, signUpForm.firstName, signUpForm.lastName, loginInfo.providerID, loginInfo.providerKey)
          authInfo <- authInfoRepository.add(loginInfo, authInfo)
          authToken <- authTokenService.create(user.id)
        } yield {
          eventBus.publish(SignUpEvent(user, request))
          Created("created")
        }
    }
  }
}
