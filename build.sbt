name := "ObservaParo"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "postgresql" % "postgresql" % "8.4-702.jdbc4",
  "org.apache.poi" % "poi-ooxml" % "3.9",
  "com.google.inject" % "guice" % "3.0" % "test",
  "info.cukes" % "cucumber-guice" % "1.1.5" % "test",
  "info.cukes" % "cucumber-java" % "1.1.5" % "test",
  "info.cukes" % "cucumber-junit" % "1.1.5" % "test",
  "org.assertj" % "assertj-core" % "1.5.0" % "test",
  "org.mockito" % "mockito-all" % "1.9.5" % "test"
)     

play.Project.playJavaSettings

play.Keys.lessEntryPoints <<= baseDirectory { base =>
   (base / "app" / "assets" / "stylesheets" / "bootstrap" * "bootstrap.less") +++
   (base / "app" / "assets" / "stylesheets" / "bootstrap" * "responsive.less") +++
   (base / "app" / "assets" / "stylesheets" * "*.less")
}
