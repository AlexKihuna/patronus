val akkaVersion      = "2.4.17"
val akkaHttpVersion  = "10.0.6"
val scalatestVersion = "3.0.1"

lazy val sharedSettings = Seq(
  organization := "com.patronus",
  version      := "0.1.0",
  scalaVersion := "2.12.2",
  resolvers    ++= Seq(
  ),
  scalacOptions ++= Seq(
    "-deprecation",
    "-feature",
    "-unchecked"
  )
)

lazy val patronous = (project in file("."))
  .aggregate(core, web, nlp)
  .settings(sharedSettings)

lazy val core = (project in file("core"))
  .settings(
    sharedSettings,
    libraryDependencies ++= Seq(
      "com.typesafe"         %  "config"               % "1.3.1",
      "ch.qos.logback"       %  "logback-classic"      % "1.2.3",
      "joda-time"            %  "joda-time"            % "2.9.9",
      "commons-daemon"       %  "commons-daemon"       % "1.0.15",
      "com.typesafe.akka"   %%  "akka-actor"           % akkaVersion,
      "com.typesafe.akka"   %%  "akka-slf4j"           % akkaVersion,
      "com.typesafe.akka"   %%  "akka-http"            % akkaHttpVersion,
      "com.typesafe.akka"   %%  "akka-http-spray-json" % akkaHttpVersion,
      "com.typesafe.akka"   %%  "akka-testkit"         % akkaVersion        % "test",
      "com.typesafe.akka"   %%  "akka-http-testkit"    % akkaHttpVersion    % "test",
      "org.scalactic"       %%  "scalactic"            % scalatestVersion   % "test",
      "org.scalatest"       %%  "scalatest"            % scalatestVersion   % "test"
    )
  )

lazy val nlp = (project in file("nlp"))
  .settings(
    sharedSettings,
    libraryDependencies ++= Seq(
      "edu.stanford.nlp"     %  "stanford-corenlp"      % "3.7.0",
      "edu.stanford.nlp"     %  "stanford-corenlp"      % "3.7.0" classifier "models",
      "org.scalactic"       %%  "scalactic"             % scalatestVersion   % "test",
      "org.scalatest"       %%  "scalatest"             % scalatestVersion   % "test"
    )
  ).dependsOn(core)

lazy val web = (project in file("web"))
  .settings(
    sharedSettings,
    libraryDependencies ++= Seq(
      "org.scalactic"       %%  "scalactic"            % scalatestVersion   % "test",
      "org.scalatest"       %%  "scalatest"            % scalatestVersion   % "test"
    )
  ).dependsOn(core, nlp)

