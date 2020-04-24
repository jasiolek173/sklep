package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}

@Singleton
class CartController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def getCartWithId(id: Long) = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def createCart = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def getAllCarts = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def removeCartWithId(id: Long) = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def createCartForm= Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def updateCartForm = Action {
    Ok(views.html.index("Your new application is ready."))
  }
}
