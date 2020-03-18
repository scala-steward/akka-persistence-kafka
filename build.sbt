val scala212Version     = "2.12.10"
val scala213Version     = "2.13.1"
val akkaVersion         = "2.6.4"
val kafkaVersion        = "2.4.0"
val alpakkaKafkaVersion = "2.0.2"

val coreSettings = Seq(
  sonatypeProfileName := "com.github.j5ik2o",
  organization := "com.github.j5ik2o",
  scalaVersion := scala213Version,
  crossScalaVersions ++= Seq(scala212Version, scala213Version),
  scalacOptions ++= {
    Seq(
      "-feature",
      "-deprecation",
      "-unchecked",
      "-encoding",
      "UTF-8",
      "-language:_"
    ) ++ {
      CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((2L, scalaMajor)) if scalaMajor >= 12 =>
          Seq.empty
        case Some((2L, scalaMajor)) if scalaMajor <= 11 =>
          Seq(
            "-Yinline-warnings"
          )
      }
    }
  },
  publishMavenStyle := true,
  publishArtifact in Test := false,
  pomIncludeRepository := { _ => false },
  pomExtra := {
    <url>https://github.com/j5ik2o/akka-persistence-kafka-awesome</url>
      <licenses>
        <license>
          <name>The MIT License</name>
          <url>http://opensource.org/licenses/MIT</url>
        </license>
      </licenses>
      <scm>
        <url>git@github.com:j5ik2o/akka-persistence-kafka-awesome.git</url>
        <connection>scm:git:github.com/j5ik2o/akka-persistence-kafka-awesome</connection>
        <developerConnection>scm:git:git@github.com:j5ik2o/akka-persistence-kafka-awesome.git</developerConnection>
      </scm>
      <developers>
        <developer>
          <id>j5ik2o</id>
          <name>Junichi Kato</name>
        </developer>
      </developers>
  },
  publishTo in ThisBuild := sonatypePublishTo.value,
  credentials := {
    val ivyCredentials = (baseDirectory in LocalRootProject).value / ".credentials"
    Credentials(ivyCredentials) :: Nil
  },
  resolvers ++= Seq(
      "Sonatype OSS Snapshot Repository" at "https://oss.sonatype.org/content/repositories/snapshots/",
      "Sonatype OSS Release Repository" at "https://oss.sonatype.org/content/repositories/releases/"
    ),
  libraryDependencies ++= Seq(
      "org.scala-lang"          % "scala-reflect"              % scalaVersion.value,
      "com.iheart"              %% "ficus"                     % "1.4.7",
      "org.slf4j"               % "slf4j-api"                  % "1.7.25",
      "com.typesafe.akka"       %% "akka-stream"               % akkaVersion,
      "com.typesafe.akka"       %% "akka-slf4j"                % akkaVersion,
      "com.typesafe.akka"       %% "akka-stream-kafka"         % alpakkaKafkaVersion,
      "com.typesafe.akka"       %% "akka-persistence"          % akkaVersion,
      "com.typesafe.akka"       %% "akka-persistence-query"    % akkaVersion,
      "org.scalatest"           %% "scalatest"                 % "3.1.1" % Test,
      "org.scalacheck"          %% "scalacheck"                % "1.14.3" % Test,
      "ch.qos.logback"          % "logback-classic"            % "1.2.3" % Test,
      "com.typesafe.akka"       %% "akka-stream-testkit"       % akkaVersion % Test,
      "com.typesafe.akka"       %% "akka-persistence-tck"      % akkaVersion % Test,
      "com.typesafe.akka"       %% "akka-testkit"              % akkaVersion % Test,
      "com.typesafe.akka"       %% "akka-stream-kafka-testkit" % alpakkaKafkaVersion % Test,
      "io.github.embeddedkafka" %% "embedded-kafka"            % kafkaVersion % Test
    ),
  PB.targets in Compile := Seq(
      scalapb.gen() -> (sourceManaged in Compile).value
    ),
  parallelExecution in Test := false
)

lazy val `root` = (project in file("."))
  .settings(coreSettings)
  .settings(
    name := "akka-persistence-kafka-awesome"
  )
