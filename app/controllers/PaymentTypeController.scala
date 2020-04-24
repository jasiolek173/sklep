package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}

@Singleton
class PaymentTypeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def getPaymentTypeWithId(id: Long) = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def createPaymentType = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def getAllPaymentTypes = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def removePaymentTypeWithId(id: Long) = Action {
    Ok(views.html.index("Your new application is ready."))
  }
}
