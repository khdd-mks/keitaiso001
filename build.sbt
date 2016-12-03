name := "AKB Keitaiso 001"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.scalikejdbc"     %% "scalikejdbc-async" % "0.6.+",
  "com.github.mauricio" %% "postgresql-async"  % "0.2.+",
  "com.github.mauricio" %% "mysql-async"       % "0.2.+",
  "org.slf4j"           %  "slf4j-simple"      % "1.7.+", // slf4j implementation
  "com.atilika.kuromoji" % "kuromoji-ipadic" % "0.9.0"
)

