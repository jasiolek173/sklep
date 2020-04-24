package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}

@Singleton
class AccountController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def getAccountWithId(id: Long) = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def createAccount = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def getAllAccounts = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def removeAccountWithId(id: Long) = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def createAccountForm = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def updateAccountForm = Action {
    Ok(views.html.index("Your new application is ready."))
  }
}
