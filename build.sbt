name := "stackexchange"

organization := "ATAL"

version := "1.0"

scalaVersion := "2.10.2"

libraryDependencies ++= Seq(
    "org.apache.opennlp" % "opennlp-tools" % "1.5.3",
    "org.postgresql"     % "postgresql"    % "9.2-1003-jdbc4"
)