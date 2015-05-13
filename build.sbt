name := """TrueRandBot"""

version := "0.0.1"

scalaVersion := "2.11.5"

libraryDependencies ++= Seq(
  "org.apache.commons" % "commons-math3" % "3.5",
  "pircbot" % "pircbot" % "1.5.0",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test"
)
