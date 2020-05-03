package controllers

import javax.inject.{Inject, Singleton}
import models.{ShipmentType, ShipmentTypeForm, UpdateShipmentTypeForm}
import play.api.mvc._
import repositories.ShipmentTypeRepository

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ShipmentTypeController @Inject()(shipmentTypeRepository: ShipmentTypeRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  def getShipmentTypeWithId(id: Int): Action[AnyContent] = Action.async { implicit request =>
    shipmentTypeRepository.getByIdOption(id).map {
      case Some(p) => Ok(views.html.shipment_type.shipmenttype(p))
      case None => Redirect(routes.ShipmentTypeController.getAllShipmentTypes())
    }
  }

  def getAllShipmentTypes: Action[AnyContent] = Action.async { implicit request =>
    shipmentTypeRepository.list().map(shipmentTypes => Ok(views.html.shipment_type.shipmenttypes(shipmentTypes)))
  }

  def removeShipmentTypeWithId(id: Int): Action[AnyContent] = Action {
    shipmentTypeRepository.delete(id)
    Redirect("/get_shipment_types")
  }

  def createShipmentType: Action[AnyContent] = Action.async { implicit request =>
    ShipmentTypeForm.form.bindFromRequest.fold(errorForm => {
      Future.successful(
        BadRequest(views.html.shipment_type.shipmenttypeadd(errorForm))
      )
    },
      shipmentType => {
        shipmentTypeRepository.create(shipmentType.name, shipmentType.description, shipmentType.price).map { _ =>
          Redirect(routes.ShipmentTypeController.createShipmentTypeForm()).flashing("success" -> "shipmentType.created")
        }
      }
    )
  }

  def createShipmentTypeForm: Action[AnyContent] = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.shipment_type.shipmenttypeadd(ShipmentTypeForm.form))
  }

  def updateShipmentType(): Action[AnyContent] = Action.async { implicit request =>
    UpdateShipmentTypeForm.form.bindFromRequest.fold(
      errorForm => {
        Future.successful(
          BadRequest(views.html.shipment_type.shipmenttypeupdate(errorForm))
        )
      },
      shipmentType => {
        shipmentTypeRepository.update(shipmentType.id, ShipmentType(shipmentType.id, shipmentType.name, shipmentType.description, shipmentType.price)).map { _ =>
          Redirect(routes.ShipmentTypeController.updateShipmentTypeForm(shipmentType.id)).flashing("success" -> "shipmentType updated")
        }
      }
    )
  }

  def updateShipmentTypeForm(id: Int): Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    shipmentTypeRepository.getByIdOption(id).map {
      case Some(p) =>
        val prodForm = UpdateShipmentTypeForm.form.fill(ShipmentType(p.id, p.name, p.description, p.price))
        Ok(views.html.shipment_type.shipmenttypeupdate(prodForm))
      case None =>
        Redirect(routes.ShipmentTypeController.getAllShipmentTypes())
    }
  }
}
