package chesstrainer.common.domain

trait Puzzle[F[_], Question, Answer] {
  def guess(question: Question): Answer => F[Boolean]
}
