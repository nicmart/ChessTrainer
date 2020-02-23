package chesstrainer.common.domain.model

import cats.data.NonEmptyList
import chesstrainer.common.domain.model.History.Item

final case class History[Q, A](items: List[Item[Q, A]])

object History {
  case class Item[Q, A](question: Q, answers: NonEmptyList[CheckedAnswer[A]])
}
