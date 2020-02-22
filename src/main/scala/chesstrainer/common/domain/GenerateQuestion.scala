package chesstrainer.common.domain

trait GenerateQuestion[F[_], Question] {
  def question: F[Question]
}
