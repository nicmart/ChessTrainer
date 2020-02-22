package chesstrainer.guessscolor.adapter.console

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import cats.effect.Console.io._
import chesstrainer.common.adapter.catsio.IORandom
import chesstrainer.guessscolor.domain.{
  GenerateGuessSquareColorQuestion,
  GuessSquareColorPuzzle
}

object App extends IOApp {
  val generateGuessSquareColorQuestion =
    new GenerateGuessSquareColorQuestion[IO](new IORandom)
  val guessSquareColor = new GuessSquareColorPuzzle[IO]
  override def run(args: List[String]): IO[ExitCode] =
    for {
      _ <- loop
    } yield ExitCode.Success

  private def loop: IO[Unit] =
    for {
      question <- generateGuessSquareColorQuestion.question
      _ <- putStrLn(
        s"What is the color of the square ${question.square.coordinates}? [D/L]"
      )
      input <- readLn
      squareColor <- IO.fromEither(ParseColor.parse(input))
      isAnswerCorrect <- guessSquareColor.guess(question)(squareColor)
      _ <- putStrLn(if (isAnswerCorrect) "Correct!" else "Wrong!")
      _ <- loop
    } yield ExitCode.Success
}
