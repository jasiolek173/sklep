package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}

@Singleton
class CouponController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def getCouponWithId(id: Long) = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def createCoupon = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def getAllCoupons = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def removeCouponWithId(id: Long) = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def createCouponForm = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def updateCouponForm = Action {
    Ok(views.html.index("Your new application is ready."))
  }
}
