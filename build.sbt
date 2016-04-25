name := """play-spring-aop"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

val javaVersion = "1.8"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  javaWs,
  "org.springframework" % "spring-context" % "4.1.6.RELEASE",
  "org.springframework" % "spring-aspects" % "4.1.6.RELEASE",
  "org.projectlombok" % "lombok" % "1.16.6",
  "javax.inject" % "javax.inject" % "1"
)
