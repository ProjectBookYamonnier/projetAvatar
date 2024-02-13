name := "Avatar"

version := "0.1"

scalaVersion := "2.13.9"

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-xml" % "2.0.0-M3",
  "org.scala-lang.modules" %% "scala-swing" % "2.1.1",
  "junit" % "junit" % "4.12" % Test,
  "com.novocode" % "junit-interface" % "0.11" % Test exclude ("junit", "junit-dep")
  // "com.eed3si9n" % "sbt-assembly" % "2.1.1"
)

// assembly / mainClass := Some("gui.Main") // le nom de la classe main
// assembly / assemblyJarName := "avatar.jar"
