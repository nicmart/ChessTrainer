package chesstrainer.common.adapter.catseffect

import cats.effect.{IO, Sync}
import chesstrainer.common.domain.Random

import scala.util.Random.nextInt

final class ScalaRandom[F[_]: Sync] extends Random[F] {

  override def randomInt(from: Int, to: Int): F[Int] =
    Sync[F].delay(nextInt(to + 1 - from) + from)
}
