package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}

@Singleton
class ProductController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def getProductWithId(id: Long) = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def createProduct = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def getAllProducts = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def removeProductWithId(id: Long) = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def createProductForm = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def updateProductForm = Action {
    Ok(views.html.index("Your new application is ready."))
  }
}
