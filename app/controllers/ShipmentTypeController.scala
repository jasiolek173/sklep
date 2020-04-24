package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}

@Singleton
class ShipmentTypeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def getShipmentTypeWithId(id: Long) = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def createShipmentType = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def getAllShipmentTypes = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def removeShipmentTypeWithId(id: Long) = Action {
    Ok(views.html.index("Your new application is ready."))
  }

}
