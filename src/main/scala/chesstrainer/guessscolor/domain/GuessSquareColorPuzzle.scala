package chesstrainer.guessscolor.domain

import cats.Monad
import cats.syntax.applicative._
import chesstrainer.common.domain.Puzzle
import chesstrainer.common.domain.model.Square
import chesstrainer.guessscolor.domain.GuessSquareColorPuzzle.{
  GuessSquareColor,
  SquareColor
}

final class GuessSquareColorPuzzle[F[_]: Monad]
    extends Puzzle[F, GuessSquareColor, SquareColor] {

  override def guess(question: GuessSquareColor): SquareColor => F[Boolean] =
    guessedColor => {
      val parity = question.square.file + question.square.rank
      val color = if (parity % 2 == 0) SquareColor.Dark else SquareColor.Light
      (color == guessedColor).pure[F]
    }
}

object GuessSquareColorPuzzle {
  case class GuessSquareColor(square: Square)

  sealed trait SquareColor

  object SquareColor {
    case object Light extends SquareColor
    case object Dark extends SquareColor
  }
}
