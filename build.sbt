import sbt.Keys.scalacOptions
import sbt._

val SCALA_VERSION = "2.11.12"
val SPARK_VERSION = "2.4.4"
val SCALATEST_VERSION = "3.0.7"

lazy val commonSettings = Seq(
  version := (version in ThisBuild).value,//"0.1-SNAPSHOT",
  organization := "dev.stef",
  scalaVersion := SCALA_VERSION,
  scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked","-target:jvm-1.8"),
  javacOptions in Compile ++= Seq("-source", "1.8", "-target", "1.8"),
)

lazy val core = (project in file("core"))

lazy val elasticsearch = (project in file("elasticsearch"))

lazy val root = (project in file("."))
  .settings(commonSettings)
  .settings(
    name := "sparky-qwant",
    version := (version in ThisBuild).value,//"0.1-SNAPSHOT",
    test in assembly := {},
    libraryDependencies ++= Seq(
      // COMMON
      "org.scalactic" %% "scalactic" % SCALATEST_VERSION,
      "org.scalatest" %% "scalatest" % SCALATEST_VERSION % "test",
      // SPARK
      "org.apache.spark" %% "spark-core" % SPARK_VERSION,//% "provided",
      "org.apache.spark" %% "spark-sql" % SPARK_VERSION,//% "provided",
      "org.apache.spark" %% "spark-mllib" % SPARK_VERSION,//% "provided",
      "org.apache.spark" %% "spark-graphx" % SPARK_VERSION,//% "provided")
      // ELASTIC SEARCH
      "org.elasticsearch" %% "elasticsearch-spark-20" % "7.5.1")
  )

// ASSEMBLY
assemblyJarName in assembly := "sparky-qwant.jar"

mainClass in assembly := Some("dev.stef.SparkyQwantManager")

assemblyMergeStrategy in assembly := {
  case PathList("javax", "servlet", xs @ _*)         => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".html" => MergeStrategy.first
  case "application.conf"                            => MergeStrategy.concat
  case "unwanted.txt"                                => MergeStrategy.discard
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}