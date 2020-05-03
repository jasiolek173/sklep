package controllers

import javax.inject.{Inject, Singleton}
import models.{Coupon, CouponForm, UpdateCouponForm}
import play.api.mvc._
import repositories.CouponRepository

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CouponController @Inject()(couponRepository: CouponRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  def getCouponWithId(id: Int): Action[AnyContent] = Action.async { implicit request =>
    couponRepository.getByIdOption(id).map {
      case Some(p) => Ok(views.html.coupon.coupon(p))
      case None => Redirect(routes.CouponController.getAllCoupons())
    }
  }

  def getAllCoupons: Action[AnyContent] = Action.async { implicit request =>
    couponRepository.list().map(coupons => Ok(views.html.coupon.coupons(coupons)))
  }

  def removeCouponWithId(id: Int): Action[AnyContent] = Action {
    couponRepository.delete(id)
    Redirect("/get_coupons")
  }

  def createCoupon: Action[AnyContent] = Action.async { implicit request =>
    CouponForm.form.bindFromRequest.fold(errorForm => {
      Future.successful(
        BadRequest(views.html.coupon.couponadd(errorForm))
      )
    },
      coupon => {
        couponRepository.create(coupon.name, coupon.description, coupon.percentage).map { _ =>
          Redirect(routes.CouponController.createCouponForm()).flashing("success" -> "coupon.created")
        }
      }
    )
  }

  def createCouponForm: Action[AnyContent] = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.coupon.couponadd(CouponForm.form))
  }

  def updateCoupon(): Action[AnyContent] = Action.async { implicit request =>
    UpdateCouponForm.form.bindFromRequest.fold(
      errorForm => {
        Future.successful(
          BadRequest(views.html.coupon.couponupdate(errorForm))
        )
      },
      coupon => {
        couponRepository.update(coupon.id, Coupon(coupon.id, coupon.name, coupon.description, coupon.percentage)).map { _ =>
          Redirect(routes.CouponController.updateCouponForm(coupon.id)).flashing("success" -> "coupon updated")
        }
      }
    )
  }

  def updateCouponForm(id: Int): Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    couponRepository.getByIdOption(id).map {
      case Some(p) =>
        val prodForm = UpdateCouponForm.form.fill(Coupon(p.id, p.name, p.description, p.percentage))
        Ok(views.html.coupon.couponupdate(prodForm))
      case None =>
        Redirect(routes.CouponController.getAllCoupons())
    }
  }
}
