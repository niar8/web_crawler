ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.15"

lazy val root = (project in file("."))
  .settings(
    name := "web_crawler",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "2.1.11",
      "dev.zio" %% "zio-http" % "3.0.1",
      "dev.zio" %% "zio-json" % "0.7.3",
      "org.jsoup" % "jsoup" % "1.18.1"
    )
  )

