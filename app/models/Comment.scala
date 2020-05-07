package models

import play.api.data.Form
import play.api.data.Forms.{mapping, _}
import play.api.libs.json.Json

case class Comment(id: Int, owner: String, content: String, product: Int)

case class CommentFormData(owner: String, content: String, product: Int)

object CommentForm {
  val form: Form[CommentFormData] = Form {
    mapping(
      "owner" -> nonEmptyText,
      "content" -> nonEmptyText,
      "product" -> number
    )(CommentFormData.apply)(CommentFormData.unapply)
  }
}

object UpdateCommentForm {
  val form: Form[Comment] = Form {
    mapping(
      "id" -> number,
      "owner" -> nonEmptyText,
      "content" -> nonEmptyText,
      "product" -> number
    )(Comment.apply)(Comment.unapply)
  }
}

object Comment {
  implicit val commentFormat = Json.format[Comment]
}

object CommentFormData {
  implicit val commentFormat = Json.format[CommentFormData]
}

