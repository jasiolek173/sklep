package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}

@Singleton
class OrderController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def getOrderWithId(id: Long) = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def createOrder = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def getAllOrders = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def removeOrderWithId(id: Long) = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def createOrderForm= Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def updateOrderForm = Action {
    Ok(views.html.index("Your new application is ready."))
  }
}
