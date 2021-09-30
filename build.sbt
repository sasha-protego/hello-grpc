import play.grpc.gen.scaladsl.PlayScalaServerCodeGenerator

ThisBuild / organization := "org.example"
ThisBuild / version := "1.0-SNAPSHOT"

// the Scala version that will be used for cross-compiled libraries
ThisBuild / scalaVersion := "2.12.10"

val macwire = "com.softwaremill.macwire" %% "macros" % "2.3.3" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.1.1" % Test
val playGrpcRuntime = "com.lightbend.play" %% "play-grpc-runtime" % "0.9.1"

enablePlugins(AkkaGrpcPlugin)
akkaGrpcExtraGenerators += PlayScalaServerCodeGenerator
libraryDependencies += "com.lightbend.play" %% "play-grpc-generators" % "0.9.1"

lazy val `hello` = (project in file("."))
  .aggregate(
    `greeter-service-api`,
    `greeter-service-impl`
  )

lazy val `greeter-service-api` = (project in file("greeter-api"))
  .settings(
    libraryDependencies ++= Seq(lagomScaladslApi)
  )

lazy val `greeter-service-impl` = (project in file("greeter-impl"))
  .enablePlugins(LagomScala)
  .enablePlugins(AkkaGrpcPlugin)
  // enables serving HTTP/2
  .enablePlugins(PlayAkkaHttp2Support)
  .settings(
    // Using Scala
    akkaGrpcGeneratedLanguages := Seq(AkkaGrpc.Scala),
    akkaGrpcGeneratedSources := Seq(AkkaGrpc.Server, AkkaGrpc.Client),
    Compile / akkaGrpcExtraGenerators += PlayScalaServerCodeGenerator,
    lagomServiceHttpPort := 11000,
    libraryDependencies ++= Seq(macwire, playGrpcRuntime)
  )
  .dependsOn(`greeter-service-api`)

ThisBuild / lagomCassandraEnabled := false
ThisBuild / lagomKafkaEnabled := false