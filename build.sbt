name := """RandBot"""

version := "0.2"

scalaVersion := "2.12.1"

libraryDependencies ++= Seq(
  "org.apache.commons" % "commons-math3" % "3.6.1",
  "pircbot" % "pircbot" % "1.5.0",
  "org.scalatest" %% "scalatest" % "3.0.1" % "test"
)
