package repositories

import com.mohiva.play.silhouette.api.{AuthInfo, LoginInfo}
import com.mohiva.play.silhouette.impl.providers.OAuth2Info
import com.mohiva.play.silhouette.persistence.daos.DelegableAuthInfoDAO
import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider

import scala.concurrent.{ExecutionContext, Future}
import scala.reflect.ClassTag

@Singleton
class AuthRepository @Inject()(dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext,
                                                                         implicit val classTag: ClassTag[OAuth2Info]) extends DelegableAuthInfoDAO[OAuth2Info] {
  override def find(loginInfo: LoginInfo): Future[Option[OAuth2Info]] = ???

  override def add(loginInfo: LoginInfo, authInfo: OAuth2Info): Future[OAuth2Info] = ???

  override def update(loginInfo: LoginInfo, authInfo: OAuth2Info): Future[OAuth2Info] = ???

  override def save(loginInfo: LoginInfo, authInfo: OAuth2Info): Future[OAuth2Info] = ???

  override def remove(loginInfo: LoginInfo): Future[Unit] = ???
}
