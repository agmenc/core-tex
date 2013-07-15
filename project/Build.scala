import sbt._
import Keys._

object Build extends Build {

  def sharedSettings = Seq(
    scalaVersion:= "2.10.2",
    scalacOptions += "-deprecation",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor" % "2.2.0",
      "junit" % "junit" % "4.8" % "test",
      "org.scalatest" %% "scalatest" % "1.9.1" % "test"
    )
  )

  lazy val main = Project(id = "main", base = file(".")).settings(sharedSettings: _*)
}
