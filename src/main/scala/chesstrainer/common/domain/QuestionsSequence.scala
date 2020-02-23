package chesstrainer.common.domain

import cats.data.NonEmptyList
import chesstrainer.common.domain.model.History

trait QuestionsSequence[F[_], Q, A] {
  def totalNumberOfQuestions: Int
  def moveToNextQuestion: F[Unit]
  def remainingQuestions: F[Int]
  def currentQuestion: F[Q]
  def checkAnswer(answer: A): F[Boolean]
  def history: F[History[Q, A]]
}
