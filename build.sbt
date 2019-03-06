name := """play-java-starter-example"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.8"

crossScalaVersions := Seq("2.11.12", "2.12.4")

libraryDependencies += guice

libraryDependencies += javaJdbc

libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.47"

// Test Database
libraryDependencies += "com.h2database" % "h2" % "1.4.197"

libraryDependencies += "com.google.code.gson" % "gson" % "2.8.+"


// Testing libraries for dealing with CompletionStage...
libraryDependencies += "org.assertj" % "assertj-core" % "3.6.2" % Test

libraryDependencies += "org.awaitility" % "awaitility" % "2.0.0" % Test

libraryDependencies += "com.pusher" % "pusher-http-java" % "1.0.0"

libraryDependencies += "com.pusher" % "push-notifications-server-java" % "1.1.0"

// Make verbose tests
testOptions in Test := Seq(Tests.Argument(TestFrameworks.JUnit, "-a", "-v"))

