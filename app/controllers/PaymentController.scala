package controllers

import javax.inject.{Inject, Singleton}
import models.{Payment, PaymentForm, UpdatePaymentForm}
import play.api.mvc._
import repositories.PaymentRepository

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PaymentController @Inject()(paymentRepository: PaymentRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  def getPaymentWithId(id: Int): Action[AnyContent] = Action.async { implicit request =>
    paymentRepository.getByIdOption(id).map {
      case Some(p) => Ok(views.html.payment.payment(p))
      case None => Redirect(routes.PaymentController.getAllPayments())
    }
  }

  def getAllPayments: Action[AnyContent] = Action.async { implicit request =>
    paymentRepository.list().map(payments => Ok(views.html.payment.payments(payments)))
  }

  def removePaymentWithId(id: Int): Action[AnyContent] = Action {
    paymentRepository.delete(id)
    Redirect("/get_payments")
  }

  def createPayment: Action[AnyContent] = Action.async { implicit request =>
    PaymentForm.form.bindFromRequest.fold(errorForm => {
      Future.successful(
        BadRequest(views.html.payment.paymentadd(errorForm))
      )
    },
      payment => {
        paymentRepository.create(payment.amount, payment.order).map { _ =>
          Redirect(routes.PaymentController.createPaymentForm()).flashing("success" -> "payment.created")
        }
      }
    )
  }

  def createPaymentForm: Action[AnyContent] = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.payment.paymentadd(PaymentForm.form))
  }

  def updatePayment(): Action[AnyContent] = Action.async { implicit request =>
    UpdatePaymentForm.form.bindFromRequest.fold(
      errorForm => {
        Future.successful(
          BadRequest(views.html.payment.paymentupdate(errorForm))
        )
      },
      payment => {
        paymentRepository.update(payment.id, Payment(payment.id, payment.amount, payment.order)).map { _ =>
          Redirect(routes.PaymentController.updatePaymentForm(payment.id)).flashing("success" -> "payment updated")
        }
      }
    )
  }

  def updatePaymentForm(id: Int): Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    paymentRepository.getByIdOption(id).map {
      case Some(p) =>
        val prodForm = UpdatePaymentForm.form.fill(Payment(p.id, p.amount, p.order))
        Ok(views.html.payment.paymentupdate(prodForm))
      case None =>
        Redirect(routes.PaymentController.getAllPayments())
    }
  }
}
