package chesstrainer.guessscolor.adapter.console

import chesstrainer.guessscolor.domain.GuessSquareColorCheckAnswer.SquareColor

object ParseColor {

  def parse(color: String): Either[InvalidColor, SquareColor] =
    color.toLowerCase match {
      case "d" => Right(SquareColor.Dark)
      case "l" => Right(SquareColor.Light)
      case _   => Left(InvalidColor(color))
    }

  case class InvalidColor(color: String) extends Throwable
}
