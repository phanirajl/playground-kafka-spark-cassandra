addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.10.0-RC1")
addSbtPlugin("com.eed3si9n"     % "sbt-assembly"         % "0.14.10")
addSbtPlugin("com.thesamet"     % "sbt-protoc"           % "0.99.23")

libraryDependencies += "com.thesamet.scalapb" %% "compilerplugin" % "0.9.0"
