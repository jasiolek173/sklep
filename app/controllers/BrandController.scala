package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}

@Singleton
class BrandController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def getBrandWithId(id: Int) = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def createBrand = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def getAllBrands = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def removeBrandWithId(id: Int) = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def createBrandForm= Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def updateBrandForm = Action {
    Ok(views.html.index("Your new application is ready."))
  }
}
