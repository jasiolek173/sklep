package controllers

import javax.inject.{Inject, Singleton}
import models.{Comment, CommentForm, UpdateCommentForm}
import play.api.mvc._
import repositories.{CommentRepository, ProductRepository}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class CommentController @Inject()(commentRepository: CommentRepository, productRepository: ProductRepository, cc: MessagesControllerComponents)(implicit ec: ExecutionContext) extends MessagesAbstractController(cc) {

  def getCommentWithId(id: Int): Action[AnyContent] = Action.async { implicit request =>
    commentRepository.getByIdOption(id).map {
      case Some(p) => Ok(views.html.comment.comment(p))
      case None => Redirect(routes.CommentController.getAllComments())
    }
  }

  def getAllComments: Action[AnyContent] = Action.async { implicit request =>
    commentRepository.list().map(comments => Ok(views.html.comment.comments(comments)))
  }

  def removeCommentWithId(id: Int): Action[AnyContent] = Action {
    commentRepository.delete(id)
    Redirect("/get_comments")
  }

  def createComment: Action[AnyContent] = Action.async { implicit request =>
    productRepository.list().flatMap(products =>
      CommentForm.form.bindFromRequest.fold(errorForm => {
        Future.successful(
          BadRequest(views.html.comment.commentadd(errorForm, products))
        )
      },
        comment => {
          commentRepository.create(comment.owner, comment.content, comment.product).map { _ =>
            Redirect(routes.CommentController.createCommentForm()).flashing("success" -> "comment.created")
          }
        }
      )
    )
  }

  def createCommentForm: Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    productRepository.list().map(products =>
      Ok(views.html.comment.commentadd(CommentForm.form, products))
    )
  }

  def updateComment(): Action[AnyContent] = Action.async { implicit request =>
    productRepository.list().flatMap(products =>
      UpdateCommentForm.form.bindFromRequest.fold(
        errorForm => {
          Future.successful(
            BadRequest(views.html.comment.commentupdate(errorForm, products))
          )
        },
        comment => {
          commentRepository.update(comment.id, Comment(comment.id, comment.owner, comment.content, comment.product)).map { _ =>
            Redirect(routes.CommentController.updateCommentForm(comment.id)).flashing("success" -> "comment updated")
          }
        }
      )
    )
  }

  def updateCommentForm(id: Int): Action[AnyContent] = Action.async { implicit request: MessagesRequest[AnyContent] =>
    productRepository.list().flatMap(products =>
      commentRepository.getByIdOption(id).map {
        case Some(p) =>
          val prodForm = UpdateCommentForm.form.fill(Comment(p.id, p.owner, p.content, p.product))
          Ok(views.html.comment.commentupdate(prodForm, products))
        case None =>
          Redirect(routes.CommentController.getAllComments())
      }
    )
  }
}
