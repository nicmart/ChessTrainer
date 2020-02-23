package chesstrainer.common.domain

trait CheckAnswer[F[_], Question, Answer] {
  def check(question: Question): Answer => F[Boolean]
}
