package higherkindness.compendiumtest

import cats.effect.Sync
import io.chrisdavenport.log4cats.Logger
import kantan.csv.ops._
import kantan.csv.{RowDecoder, rfc}

trait Reader {

  def read[T, F[_]: Sync](fileName: String)(implicit logger: Logger[F],
                                            decode: RowDecoder[T]): F[List[T]] =
    Sync[F].delay(
      getClass
        .getResource(fileName)
        .asCsvReader[T](rfc.withHeader)
        .map(_.fold({ e =>
          logger.error("¡Error decoding! " + e)
          None
        }, Some(_)))
        .toList
        .flatten
    )

}
