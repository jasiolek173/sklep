package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}

@Singleton
class CommentController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def getCommentWithId(id: Long) = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def createComment = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def getAllComments = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def removeCommentWithId(id: Long) = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def createCommentForm= Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def updateCommentForm = Action {
    Ok(views.html.index("Your new application is ready."))
  }
}
