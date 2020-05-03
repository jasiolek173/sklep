package controllers

import javax.inject.{Inject, Singleton}
import models.{Brand, BrandForm, UpdateBrandForm}
import play.api.mvc._
import repositories.BrandRepository

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class BrandController @Inject()(brandRepository: BrandRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  def getBrandWithId(id: Int): Action[AnyContent] = Action.async { implicit request =>
    brandRepository.getByIdOption(id).map {
      case Some(p) => Ok(views.html.brand.brand(p))
      case None => Redirect("/get_brands")
    }
  }

  def getAllBrands: Action[AnyContent] = Action.async { implicit request =>
    brandRepository.list().map(brands => Ok(views.html.brand.brands(brands)))
  }

  def removeBrandWithId(id: Int): Action[AnyContent] = Action {
    brandRepository.delete(id)
    Redirect("/get_brands")
  }

  def createBrand: Action[AnyContent] = Action.async { implicit request =>
    BrandForm.form.bindFromRequest.fold(errorForm => {
      Future.successful(
        BadRequest(views.html.brand.brandadd(errorForm))
      )
    },
      brand => {
        brandRepository.create(brand.name).map { _ =>
          Redirect("/create_brand_form").flashing("success" -> "brand.created")
        }
      }
    )
  }

  def createBrandForm: Action[AnyContent] = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.brand.brandadd(BrandForm.form))
  }

  def updateBrand(): Action[AnyContent] = Action.async { implicit request =>
    UpdateBrandForm.form.bindFromRequest.fold(
      errorForm => {
        Future.successful(
          BadRequest(views.html.brand.brandupdate(errorForm))
        )
      },
      brand => {
        brandRepository.update(brand.id, Brand(brand.id, brand.name)).map { _ =>
          Redirect(routes.BrandController.updateBrandForm(brand.id)).flashing("success" -> "brand updated")
        }
      }
    )
  }

  def updateBrandForm(id: Int): Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    brandRepository.getByIdOption(id).map {
      case Some(p) =>
        val prodForm = UpdateBrandForm.form.fill(Brand(p.id, p.name))
        Ok(views.html.brand.brandupdate(prodForm))
      case None =>
        Redirect(routes.CategoryController.getAllCategories())
    }
  }
}