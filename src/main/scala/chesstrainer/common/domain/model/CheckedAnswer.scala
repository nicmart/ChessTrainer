package chesstrainer.common.domain.model

final case class CheckedAnswer[Answer](answer: Answer, correct: Boolean)
