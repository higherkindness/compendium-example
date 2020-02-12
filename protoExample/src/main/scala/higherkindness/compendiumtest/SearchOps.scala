package higherkindness.compendiumtest

import cats.implicits._
import cats.syntax._
import cats.effect.Sync
import Decoder._
import higherkindness.compendiumtest.compendium.{
  Client,
  Product,
  Sale,
  SearchOps
}
import io.chrisdavenport.log4cats.Logger

case class ClientNotFound(msg: String) extends Exception
case class ProductNotFound(msg: String) extends Exception

object SearchOps extends Reader {

  def apply[F[_]: Sync]()(implicit logger: Logger[F]): SearchOps[F] =
    new SearchOps[F] {

      val products: F[List[Product]] = read[Product, F]("/product.csv")
      val sales: F[List[Sale]] = read[Sale, F]("/sale.csv")
      val clients: F[List[Client]] = read[Client, F]("/client.csv")

      override def FindClients(req: compendium.Product): F[compendium.Client] =
        for {
          sale <- sales
          clIds = sale.flatMap(
            s =>
              if (matchOpt(s.product.map(_.id_prod), Some(req.id_prod)))
                Some(req.id_prod)
              else None
          )
          cli <- clients.map(_.find(c => clIds.contains(c.id_client)))
          res <- cli match {
            case None =>
              ClientNotFound(s"Client with id(s) ${clIds.mkString("-")}")
                .raiseError[F, Client]
            case Some(v) => Sync[F].pure(v)
          }
        } yield res

      override def FindProducts(req: compendium.Client): F[compendium.Product] =
        for {
          sale <- sales
          prodIds = sale.flatMap(
            s =>
              if (matchOpt(s.client.map(_.id_client), Some(req.id_client)))
                Some(req.id_client)
              else None
          )
          prods <- products.map(_.find(c => prodIds.contains(c.id_prod)))
          res <- prods match {
            case None =>
              ProductNotFound(s"Client with id(s) ${prodIds.mkString("-")}")
                .raiseError[F, Product]
            case Some(v) => Sync[F].pure(v)
          }
        } yield res

      def matchOpt[A](opt: Option[A], value: Option[A]): Boolean =
        (opt, value) match {
          case (Some(v1), Some(v2)) if v1 == v2 => true
          case _                                => false
        }
    }

}
