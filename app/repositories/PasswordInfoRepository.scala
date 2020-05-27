package repositories

import com.mohiva.play.silhouette.api.LoginInfo
import com.mohiva.play.silhouette.api.util.PasswordInfo
import com.mohiva.play.silhouette.persistence.daos.DelegableAuthInfoDAO
import javax.inject.Inject
import models.Password
import models.tables.PasswordTable
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}
import scala.reflect.ClassTag

class PasswordInfoRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext,
                                                                                 implicit val classTag: ClassTag[PasswordInfo]) extends DelegableAuthInfoDAO[PasswordInfo] {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  val passwordTable = TableQuery[PasswordTable]

  override def find(loginInfo: LoginInfo): Future[Option[PasswordInfo]] =
    findPassword(loginInfo)
      .map {
        case Some(p) => Some(PasswordInfo(p.hasher, p.password, p.salt))
        case None => None
      }

  override def add(loginInfo: LoginInfo, authInfo: PasswordInfo): Future[PasswordInfo] = db.run {
    (passwordTable.map(c => (c.providerId, c.providerKey, c.hasher, c.password, c.salt))
      returning passwordTable.map(_.id)
      into { case ((providerId: String, providerKey: String, hasher: String, password: String, salt: Option[String]), id)
    => Password(id, providerId, providerKey, hasher, password, salt)
    }
      ) += (loginInfo.providerID, loginInfo.providerKey, authInfo.hasher, authInfo.password, authInfo.salt)
  }.map(p => PasswordInfo(p.hasher, p.password, p.salt))

  override def update(loginInfo: LoginInfo, authInfo: PasswordInfo): Future[PasswordInfo] = findPassword(loginInfo)
    .flatMap {
      case Some(p) =>
        val newPassword = Password(p.id, loginInfo.providerID, loginInfo.providerKey, authInfo.hasher, authInfo.password, authInfo.salt)
        db.run(passwordTable.filter(_.id === p.id).update(newPassword)).map(_ => ())
        db.run(passwordTable.filter(_.providerId === loginInfo.providerID)
          .filter(_.providerKey === loginInfo.providerKey)
          .result.head).map(p => PasswordInfo(p.hasher, p.password, p.salt))
      case None => add(loginInfo, authInfo)
    }

  override def save(loginInfo: LoginInfo, authInfo: PasswordInfo): Future[PasswordInfo] = findPassword(loginInfo)
    .flatMap {
      case Some(p) => update(loginInfo, authInfo)
      case None => add(loginInfo, authInfo)
    }

  override def remove(loginInfo: LoginInfo): Future[Unit] = db.run {
    passwordTable.filter(_.providerId === loginInfo.providerID)
      .filter(_.providerKey === loginInfo.providerKey)
      .delete
      .map(_ => ())
  }

  private def findPassword(loginInfo: LoginInfo): Future[Option[Password]] = db.run {
    passwordTable.filter(_.providerId === loginInfo.providerID)
      .filter(_.providerKey === loginInfo.providerKey)
      .result.headOption
  }
}
