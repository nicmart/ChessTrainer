package chesstrainer.guessscolor.domain

import cats.Monad
import cats.implicits._
import chesstrainer.common.domain.model.Square
import chesstrainer.common.domain.{GenerateQuestion, Random}
import chesstrainer.guessscolor.domain.GuessSquareColorCheckAnswer.GuessSquareColor

final class GenerateGuessSquareColorQuestion[F[_]: Monad](random: Random[F])
    extends GenerateQuestion[F, GuessSquareColor] {

  override def question: F[GuessSquareColor] =
    for {
      file <- random.randomInt(1, 8)
      rank <- random.randomInt(1, 8)
      square = Square.unsafeFrom(file, rank)
    } yield GuessSquareColor(square)
}
