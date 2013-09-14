name := "stackexchange"

organization := "ATAL"

version := "1.0"

scalaVersion := "2.10.2"

libraryDependencies ++= Seq(
	"net.sf.opencsv"           %  "opencsv"         % "2.0",
    "org.apache.logging.log4j" %  "log4j-api"       % "2.0-beta8",
    "org.apache.logging.log4j" %  "log4j-core"      % "2.0-beta8",
    "org.apache.opennlp"       %  "opennlp-tools"   % "1.5.3",
    "com.typesafe.slick"       %% "slick"           % "1.0.1",
    "org.postgresql"           %  "postgresql"      % "9.2-1003-jdbc4"
)