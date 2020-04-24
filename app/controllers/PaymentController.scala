package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}

@Singleton
class PaymentController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def getPaymentWithId(id: Long) = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def createPayment = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def getAllPayments = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def removePaymentWithId(id: Long) = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def createPaymentForm = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def updatePaymentForm = Action {
    Ok(views.html.index("Your new application is ready."))
  }
}
