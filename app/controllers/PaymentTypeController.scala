package controllers

import javax.inject.{Inject, Singleton}
import models.{PaymentType, PaymentTypeForm, UpdatePaymentTypeForm}
import play.api.libs.json.Json
import play.api.mvc._
import repositories.PaymentTypeRepository

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PaymentTypeController @Inject()(paymentTypeRepository: PaymentTypeRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  def getPaymentTypeWithId(id: Int): Action[AnyContent] = Action.async { implicit request =>
    paymentTypeRepository.getByIdOption(id).map {
      case Some(p) => Ok(views.html.payment_type.paymenttype(p))
      case None => Redirect(routes.PaymentTypeController.getAllPaymentTypes())
    }
  }

  def getAllPaymentTypes: Action[AnyContent] = Action.async { implicit request =>
    paymentTypeRepository.list().map(paymentTypes => Ok(views.html.payment_type.paymenttypes(paymentTypes)))
  }

  def removePaymentTypeWithId(id: Int): Action[AnyContent] = Action {
    paymentTypeRepository.delete(id)
    Redirect("/get_payment_types")
  }

  def createPaymentType: Action[AnyContent] = Action.async { implicit request =>
    PaymentTypeForm.form.bindFromRequest.fold(errorForm => {
      Future.successful(
        BadRequest(views.html.payment_type.paymenttypeadd(errorForm))
      )
    },
      paymentType => {
        paymentTypeRepository.create(paymentType.name, paymentType.description).map { _ =>
          Redirect(routes.PaymentTypeController.createPaymentTypeForm()).flashing("success" -> "paymentType.created")
        }
      }
    )
  }

  def createPaymentTypeForm: Action[AnyContent] = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.payment_type.paymenttypeadd(PaymentTypeForm.form))
  }

  def updatePaymentType(): Action[AnyContent] = Action.async { implicit request =>
    UpdatePaymentTypeForm.form.bindFromRequest.fold(
      errorForm => {
        Future.successful(
          BadRequest(views.html.payment_type.paymenttypeupdate(errorForm))
        )
      },
      paymentType => {
        paymentTypeRepository.update(paymentType.id, PaymentType(paymentType.id, paymentType.name, paymentType.description)).map { _ =>
          Redirect(routes.PaymentTypeController.updatePaymentTypeForm(paymentType.id)).flashing("success" -> "paymentType updated")
        }
      }
    )
  }

  def updatePaymentTypeForm(id: Int): Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    paymentTypeRepository.getByIdOption(id).map {
      case Some(p) =>
        val prodForm = UpdatePaymentTypeForm.form.fill(PaymentType(p.id, p.name, p.description))
        Ok(views.html.payment_type.paymenttypeupdate(prodForm))
      case None =>
        Redirect(routes.PaymentTypeController.getAllPaymentTypes())
    }
  }

  //  REST
  def getPaymentTypes: Action[AnyContent] = Action.async {
    paymentTypeRepository.list().map(types => Json.toJson(types)).map(json => Ok(json))
  }
}
