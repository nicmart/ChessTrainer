package chesstrainer.guessscolor.domain

import cats.MonadError
import cats.implicits._
import chesstrainer.guessscolor.adapter.console.ParseColor
import chesstrainer.guessscolor.domain.GuessSquareColorCheckAnswer.GuessSquareColor

final class PuzzleLoop[F[_]](
    questionGenerator: GenerateGuessSquareColorQuestion[F],
    puzzle: GuessSquareColorCheckAnswer[F],
    interactor: Interactor[F]
)(implicit M: MonadError[F, Throwable]) {
  import interactor._

  def loop: F[Nothing] = iteration.foreverM

  def iteration: F[Unit] =
    for {
      question <- questionGenerator.question
      _        <- askUntilCorrect(question)
    } yield ()

  private def askUntilCorrect(question: GuessSquareColor): F[Unit] =
    for {
      isAnswerCorrect <- ask(question)
      _               <- if (isAnswerCorrect) ().pure[F] else tryAgain >> askUntilCorrect(question)
    } yield ()

  private def ask(question: GuessSquareColor): F[Boolean] =
    for {
      input           <- askColorOfSquare(question.square)
      squareColor     <- M.fromEither(ParseColor.parse(input))
      isAnswerCorrect <- puzzle.check(question)(squareColor)
      _               <- showResult(isAnswerCorrect)
    } yield isAnswerCorrect
}
