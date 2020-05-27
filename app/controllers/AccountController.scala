package controllers

import javax.inject.{Inject, Singleton}
import models.{Account, AccountForm, UpdateAccountForm}
import play.api.mvc._
import repositories.AccountRepository

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class AccountController @Inject()(accountRepository: AccountRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  def getAccountWithId(id: Int): Action[AnyContent] = Action.async { implicit request =>
    accountRepository.getByIdOption(id).map {
      case Some(p) => Ok(views.html.account.account(p))
      case None => Redirect(routes.AccountController.getAllAccounts())
    }
  }

  def getAllAccounts: Action[AnyContent] = Action.async { implicit request =>
    accountRepository.list().map(accounts => Ok(views.html.account.accounts(accounts)))
  }

  def removeAccountWithId(id: Int): Action[AnyContent] = Action {
    accountRepository.delete(id)
    Redirect("/get_accounts")
  }

  def createAccount: Action[AnyContent] = Action.async { implicit request =>
    AccountForm.form.bindFromRequest.fold(errorForm => {
      Future.successful(
        BadRequest(views.html.account.accountadd(errorForm))
      )
    },
      account => {
        accountRepository.create(account.email, account.firstName, account.lastName, account.providerId, account.providerKey).map { _ =>
          Redirect(routes.AccountController.createAccountForm()).flashing("success" -> "account.created")
        }
      }
    )
  }

  def createAccountForm: Action[AnyContent] = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.account.accountadd(AccountForm.form))
  }

  def updateAccount(): Action[AnyContent] = Action.async { implicit request =>
    UpdateAccountForm.form.bindFromRequest.fold(
      errorForm => {
        Future.successful(
          BadRequest(views.html.account.accountupdate(errorForm))
        )
      },
      account => {
        accountRepository.update(account.id, Account(account.id, account.email, account.firstName, account.lastName, account.providerId, account.providerKey)).map { _ =>
          Redirect(routes.AccountController.updateAccountForm(account.id)).flashing("success" -> "account updated")
        }
      }
    )
  }

  def updateAccountForm(id: Int): Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    accountRepository.getByIdOption(id).map {
      case Some(p) =>
        val prodForm = UpdateAccountForm.form.fill(Account(p.id, p.email, p.firstName, p.lastName, p.providerId, p.providerKey))
        Ok(views.html.account.accountupdate(prodForm))
      case None =>
        Redirect(routes.AccountController.getAllAccounts())
    }
  }
}

