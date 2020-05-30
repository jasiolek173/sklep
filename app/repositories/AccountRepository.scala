package repositories

import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.api.services.IdentityService
import javax.inject.Inject
import models.Account
import models.tables.AccountTable
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

class AccountRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) extends IdentityService[Account] {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  val account = TableQuery[AccountTable]

  override def retrieve(loginInfo: LoginInfo): Future[Option[Account]] = db.run {
    account.filter(_.providerId === loginInfo.providerID)
      .filter(_.providerKey === loginInfo.providerKey)
      .result
      .headOption
  }

  def create(email: String, firstName: String, lastName: String, providerId: String, providerKey: String): Future[Account] = db.run {
    (account.map(c => (c.email, c.firstName, c.lastName, c.providerId, c.providerKey))
      returning account.map(_.id)
      into { case ((email: String, firstName: String, lastName: String, providerId: String, providerKey: String), id) => Account(id, email, firstName, lastName, providerId, providerKey) }
      ) += (email, firstName, lastName, providerId, providerKey)
  }

  def list(): Future[Seq[Account]] = db.run {
    account.result
  }

  def getById(id: Int): Future[Account] = db.run {
    account.filter(_.id === id).result.head
  }

  def getByIdOption(id: Int): Future[Option[Account]] = db.run {
    account.filter(_.id === id).result.headOption
  }

  def delete(id: Int): Future[Unit] = db.run(account.filter(_.id === id).delete).map(_ => ())

  def update(id: Int, newAccount: Account): Future[Unit] = {
    val accountToUpdate: Account = newAccount.copy(id)
    db.run(account.filter(_.id === id).update(accountToUpdate)).map(_ => ())
  }

  def save(email: Option[String], loginInfo: LoginInfo, firstName: Option[String], lastName: Option[String]): Future[Account] =
    retrieve(loginInfo).flatMap {
      case Some(p) => update(p.id, Account(p.id, email.get, firstName.get, lastName.get, loginInfo.providerID, loginInfo.providerKey))
        getById(p.id)
      case None =>
        create(email.get, firstName.get, lastName.get, loginInfo.providerID, loginInfo.providerKey)
    }
}

