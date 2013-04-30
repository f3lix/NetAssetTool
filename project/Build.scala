import sbt._
import Keys._

object SbtBuild extends Build {
    lazy val root = Project(id = "NetAssetTool", base = file("."))
}