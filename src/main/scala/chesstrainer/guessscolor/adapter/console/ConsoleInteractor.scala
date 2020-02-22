package chesstrainer.guessscolor.adapter.console

import cats.Monad
import cats.effect.Console
import cats.implicits._
import chesstrainer.common.domain.model.Square
import chesstrainer.guessscolor.domain.Interactor

final class ConsoleInteractor[F[_]: Monad](console: Console[F]) extends Interactor[F] {
  import console._

  override def askColorOfSquare(square: Square): F[String] =
    for {
      _     <- putStrLn(s"What is the color of the square ${square.coordinates}? [D/L]")
      input <- readLn
    } yield input

  override def showResult(correct: Boolean): F[Unit] =
    console.putStrLn(if (correct) "Correct!" else "Wrong!")

  override def tryAgain: F[Unit] =
    putStrLn("Try Again!")
}
