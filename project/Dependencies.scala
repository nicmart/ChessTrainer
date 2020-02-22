import sbt._

object Dependencies {
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.8"
  lazy val cats = "org.typelevel" %% "cats-effect" % "2.1.1"
  lazy val catsConsole = "dev.profunktor" %% "console4cats" % "0.8.1"
}
