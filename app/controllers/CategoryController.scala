package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}

@Singleton
class CategoryController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def getCategoryWithId(id: Long) = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def createCategory = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def getAllCategories = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def removeCategoryWithId(id: Long) = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def createCategoryForm= Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def updateCategoryForm = Action {
    Ok(views.html.index("Your new application is ready."))
  }
}
