package chesstrainer.common.domain

import cats.Monad
import cats.implicits._
import chesstrainer.common.domain.GuessColorStateMachine.Input._
import chesstrainer.common.domain.GuessColorStateMachine.State._
import chesstrainer.common.domain.GuessColorStateMachine.{Input, State}
import chesstrainer.common.domain.model.Square
import chesstrainer.guessscolor.domain.GuessSquareColorCheckAnswer.{GuessSquareColor, SquareColor}

class GuessColorStateMachine[F[+_]: Monad](square: Square, check: CheckAnswer[F, GuessSquareColor, SquareColor]) {

  def next(currentState: State, input: Input): F[State] =
    (currentState, input) match {
      case (Start, Tick)                       => AskingColorOfSquare.pure[F]
      case (AskingColorOfSquare, Invalid(raw)) => InvalidAnswerWasGiven(raw).pure[F]
      case (AskingColorOfSquare, Answer(sq))   => answer(sq)
      case (AskingColorOfSquare, Exit)         => Stop.pure[F]
      case (InvalidAnswerWasGiven(_), Tick)    => AskingColorOfSquare.pure[F]
      case (CorrectAnswerWasGiven(_), Tick)    => AskingColorOfSquare.pure[F]
      case (WrongAnswerWasGiven(_), _)         => AskingColorOfSquare.pure[F]
      case (Stop, _)                           => Stop.pure[F]
      case _                                   => Error.pure[F]
    }

  private def answer(squareColor: SquareColor): F[State] =
    for {
      isAnswerCorrect <- check.check(GuessSquareColor(square))(squareColor)
      nextState = if (isAnswerCorrect) CorrectAnswerWasGiven(squareColor) else WrongAnswerWasGiven(squareColor)
    } yield nextState
}

object GuessColorStateMachine {

  sealed trait Input

  object Input {
    case class Answer(squareColor: SquareColor) extends Input
    case class Invalid(raw: String)             extends Input
    case object Exit                            extends Input
    case object Tick                            extends Input
  }

  sealed trait State

  object State {
    case object Start                                          extends State
    case object AskingColorOfSquare                            extends State
    case class InvalidAnswerWasGiven(answer: String)           extends State
    case class CorrectAnswerWasGiven(squareColor: SquareColor) extends State
    case class WrongAnswerWasGiven(squareColor: SquareColor)   extends State
    case object Error                                          extends State
    case object Stop                                           extends State
  }
}
