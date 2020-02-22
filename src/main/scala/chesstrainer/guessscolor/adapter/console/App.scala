package chesstrainer.guessscolor.adapter.console

import cats.effect
import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import chesstrainer.common.adapter.catsio.IORandom
import chesstrainer.guessscolor.domain.{GenerateGuessSquareColorQuestion, GuessSquareColorPuzzle, PuzzleLoop}

object App extends IOApp {

  private val generateGuessSquareColorQuestion = new GenerateGuessSquareColorQuestion[IO](new IORandom)
  private val guessSquareColor                 = new GuessSquareColorPuzzle[IO]
  private val interactor                       = new ConsoleInteractor[IO](effect.Console.io)
  private val loop                             = new PuzzleLoop[IO](generateGuessSquareColorQuestion, guessSquareColor, interactor)

  override def run(args: List[String]): IO[ExitCode] =
    for {
      _ <- loop.loop
    } yield ExitCode.Success
}
