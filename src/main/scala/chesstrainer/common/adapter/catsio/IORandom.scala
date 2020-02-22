package chesstrainer.common.adapter.catsio

import cats.effect.IO
import chesstrainer.common.domain.Random

import scala.util.Random.nextInt

final class IORandom extends Random[IO] {
  override def randomInt(from: Int, to: Int): IO[Int] =
    IO(nextInt(to + 1 - from) + from)
}
