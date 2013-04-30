import sbt._
import sbt.Keys._
import spray.revolver.RevolverPlugin.Revolver
import LWJGLPlugin._

object TopEditorBuild extends Build {

  val builder = new samskivert.ProjectBuilder("pom.xml") {
    override val globalSettings = Seq(
      crossPaths    := false,
      javacOptions  ++= Seq("-Xlint", "-Xlint:-serial", "-source", "1.6", "-target", "1.6"),
      javaOptions   ++= Seq("-ea"),
      fork in Compile := true,
      autoScalaLibrary in Compile := false // no scala-library dependency (except for tests)
    )
    override def projectSettings (name :String, pom :pomutil.POM) = name match {
      case "java" => Revolver.settings ++ lwjglSettings ++ seq(
        lwjgl.version := pom.getAttr("lwjgl.version").get
      )
      case _ => Nil
    }
  }

  lazy val assets = builder("assets")
  lazy val core   = builder("core")
  lazy val java   = builder("java")
  lazy val main   = Project("main", file(".")) aggregate(assets, core, java)
}
