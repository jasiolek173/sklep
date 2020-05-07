package controllers

import javax.inject._
import models.{Product, ProductForm, ProductRepresentation, UpdateProductForm}
import play.api.libs.json.Json
import play.api.mvc._
import repositories.{BrandRepository, CategoryRepository, ProductRepository}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ProductController @Inject()(productsRepo: ProductRepository, categoryRepo: CategoryRepository, brandRepo: BrandRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  def getProductWithId(id: Int): Action[AnyContent] = Action.async { implicit request =>
    productsRepo.getByIdOption(id).map {
      case Some(p) => Ok(views.html.product.product(p))
      case None => Redirect(routes.ProductController.getAllProducts())
    }
  }

  def createProduct: Action[AnyContent] = Action.async { implicit request =>
    categoryRepo.list().flatMap(categories =>
      brandRepo.list().flatMap(brands =>
        ProductForm.form.bindFromRequest.fold(errorForm => {
          Future.successful(
            BadRequest(views.html.product.productadd(errorForm, categories, brands))
          )
        },
          product => {
            productsRepo.createProduct(product.name, product.description, product.category, product.brand, product.price, product.imageUrl).map { _ =>
              Redirect(routes.ProductController.createProductForm()).flashing("success" -> "product.created")
            }
          }
        )
      )
    )
  }

  def updateProduct(): Action[AnyContent] = Action.async { implicit request =>
    categoryRepo.list().flatMap(categories =>
      brandRepo.list().flatMap(brands =>
        UpdateProductForm.form.bindFromRequest.fold(
          errorForm => {
            Future.successful(
              BadRequest(views.html.product.productupdate(errorForm, categories, brands))
            )
          },
          product => {
            productsRepo.update(product.id, Product(product.id, product.name, product.description, product.category, product.brand, product.price, product.imageUrl)).map { _ =>
              Redirect(routes.ProductController.updateProductForm(product.id)).flashing("success" -> "product updated")
            }
          }
        )
      )
    )
  }

  def getAllProducts: Action[AnyContent] = Action.async { implicit request =>
    productsRepo.list().map(products => Ok(views.html.product.products(products)))
  }

  def removeProductWithId(id: Int): Action[AnyContent] = Action {
    productsRepo.delete(id)
    Redirect("/get_products")
  }


  def createProductForm: Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    categoryRepo.list().flatMap(categories =>
      brandRepo.list().map(brands =>
        Ok(views.html.product.productadd(ProductForm.form, categories, brands))
      )
    )
  }

  def updateProductForm(id: Int): Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    categoryRepo.list().flatMap(categories =>
      brandRepo.list().flatMap(brands =>
        productsRepo.getByIdOption(id).map {
          case Some(p) =>
            val prodForm = UpdateProductForm.form.fill(Product(p.id, p.name, p.description, p.category, p.brand, p.price, p.imageUrl))
            Ok(views.html.product.productupdate(prodForm, categories, brands))
          case None =>
            Redirect(routes.ProductController.getAllProducts())
        }
      )
    )
  }

  //REST
  def getProducts: Action[AnyContent] = Action.async {
    productsRepo.list().map(products => Json.toJson(products)).map(json => Ok(json))
  }

  def getProductsWithCategoryId(id: Int): Action[AnyContent] = Action.async {
    productsRepo.getByCategory(id).map(products => Json.toJson(products)).map(json => Ok(json))
  }

  def getProductRepresentationWithId(id: Int): Action[AnyContent] = Action.async {
    productsRepo.getById(id).flatMap(product =>
      categoryRepo.getById(product.category).flatMap(category =>
        brandRepo.getById(product.brand).map(brand =>
          Json.toJson(ProductRepresentation(product.name, product.description, category.name, brand.name, product.price, product.imageUrl))
        ).map(json => Ok(json))
      )
    )
  }
}
