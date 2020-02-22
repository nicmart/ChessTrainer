package chesstrainer.common.domain

trait Random[F[_]] {
  def randomInt(from: Int, to: Int): F[Int]
}
