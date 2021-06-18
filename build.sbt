val playSlickVersion = "5.0.0"
val scalaTestVersion = "3.2.9"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := "cp-backend",
    maintainer := "Alexander Dobrynin <alexander.dobrynin@th-koeln.de>",
    version := "1.0",
    scalaVersion := "2.13.6",
    libraryDependencies ++= play,
    libraryDependencies ++= database,
    libraryDependencies ++= test,
    scalacOptions ++= Seq(
      "-feature",
      "-deprecation",
      "-Xfatal-warnings"
    )
  )

lazy val play = Seq(
  guice,
  ws,
  "com.typesafe.play" %% "play-slick" % "5.0.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0",
  "com.h2database" % "h2" % "1.4.199",
  specs2 % Test
)

lazy val database = Seq(
  "com.typesafe.play" %% "play-slick" % playSlickVersion,
  "com.typesafe.play" %% "play-slick-evolutions" % playSlickVersion,
  "org.postgresql" % "postgresql" % "42.2.19"
)

lazy val test = Seq(
  "org.scalactic" %% "scalactic" % scalaTestVersion,
  "org.scalatest" %% "scalatest" % scalaTestVersion % "test",
  "org.scalatest" %% "scalatest-wordspec" % scalaTestVersion % "test"
)