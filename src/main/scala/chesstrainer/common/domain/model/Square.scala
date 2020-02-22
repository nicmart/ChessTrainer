package chesstrainer.common.domain.model

sealed abstract case class Square(file: Int, rank: Int) {
  def coordinates: String = ('a' + file - 1).toChar + rank.toString
}

object Square {
  def from(file: Int, rank: Int): Either[InvalidCoordinatesError, Square] =
    Either.cond(
      validCoordinate(file) && validCoordinate(rank),
      new Square(file, rank) {},
      InvalidCoordinatesError(file, rank)
    )

  def unsafeFrom(file: Int, rank: Int): Square =
    from(file, rank).fold(throw _, identity)

  case class InvalidCoordinatesError(file: Int, rank: Int) extends Throwable {
    override def getMessage: String = s"Coordinates ($file, $rank) were invalid"
  }

  private def validCoordinate(coordinate: Int): Boolean =
    coordinate >= 1 && coordinate <= 8
}
