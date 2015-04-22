// Comment to get more information during initialization
logLevel := Level.Info

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

// Use the Play sbt plugin for Play projects
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.3.7")

//resolvers ++= Seq(
//  Classpaths.sbtPluginReleases,
//  Resolver.sonatypeRepo("releases"),
//  Resolver.typesafeRepo("releases")
//)
