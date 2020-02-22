package chesstrainer.guessscolor.domain

import chesstrainer.common.domain.model.Square

trait Interactor[F[_]] {
  def askColorOfSquare(square: Square): F[String]
  def showResult(correct: Boolean): F[Unit]
  def tryAgain: F[Unit]
}
