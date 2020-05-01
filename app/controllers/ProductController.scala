package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import repositories.{CategoryRepository, ProductRepository}

import scala.concurrent.ExecutionContext

@Singleton
class ProductController @Inject()(productsRepo: ProductRepository, categoryRepo: CategoryRepository, cc: ControllerComponents)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def getProductWithId(id: Long): Action[AnyContent] = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def createProduct = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def getAllProducts: Action[AnyContent] = Action.async { implicit request =>
    val allProducts = productsRepo.list()
    allProducts.map(products => Ok(views.html.products(products)))
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
