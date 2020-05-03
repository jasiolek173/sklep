package controllers

import javax.inject.{Inject, Singleton}
import models.{Category, CategoryForm, UpdateCategoryForm}
import play.api.mvc._
import repositories.CategoryRepository

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CategoryController @Inject()(categoryRepository: CategoryRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  def getCategoryWithId(id: Int): Action[AnyContent] = Action.async { implicit request =>
    categoryRepository.getByIdOption(id).map {
      case Some(p) => Ok(views.html.category.category(p))
      case None => Redirect(routes.CategoryController.getAllCategories())
    }
  }

  def getAllCategories: Action[AnyContent] = Action.async { implicit request =>
    categoryRepository.list().map(categories => Ok(views.html.category.categories(categories)))
  }

  def removeCategoryWithId(id: Int): Action[AnyContent] = Action {
    categoryRepository.delete(id)
    Redirect("/get_categories")
  }

  def createCategory: Action[AnyContent] = Action.async { implicit request =>
    CategoryForm.form.bindFromRequest.fold(errorForm => {
      Future.successful(
        BadRequest(views.html.category.categoryadd(errorForm))
      )
    },
      category => {
        categoryRepository.create(category.name).map { _ =>
          Redirect(routes.CategoryController.createCategoryForm()).flashing("success" -> "category.created")
        }
      }
    )
  }

  def createCategoryForm: Action[AnyContent] = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.category.categoryadd(CategoryForm.form))
  }

  def updateCategory(): Action[AnyContent] = Action.async { implicit request =>
    UpdateCategoryForm.form.bindFromRequest.fold(
      errorForm => {
        Future.successful(
          BadRequest(views.html.category.categoryupdate(errorForm))
        )
      },
      category => {
        categoryRepository.update(category.id, Category(category.id, category.name)).map { _ =>
          Redirect(routes.CategoryController.updateCategoryForm(category.id)).flashing("success" -> "category updated")
        }
      }
    )
  }

  def updateCategoryForm(id: Int): Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    categoryRepository.getByIdOption(id).map {
      case Some(p) =>
        val prodForm = UpdateCategoryForm.form.fill(Category(p.id, p.name))
        Ok(views.html.category.categoryupdate(prodForm))
      case None =>
        Redirect(routes.CategoryController.getAllCategories())
    }
  }
}

