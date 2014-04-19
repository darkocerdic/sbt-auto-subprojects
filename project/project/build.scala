import sbt._
import Keys._

object SubprojectGenerator {
  def generate(output: File,baseFolder: File): Seq[File] = {
    val appsFolder: File = baseFolder.getParentFile / "apps"
    val subApps = appsFolder.listFiles.filter(_.isDirectory).map(_.name)
    val subappsString = subApps.mkString(",")

    def projFromName(name: String) = "  lazy val " + name + " = Project(id = \"" + name + "\", base = file(\"apps/" + name +"\")).dependsOn(common)"

    val source =
      """
        |import sbt._
        |import Keys._
        |
        |trait Subprojects {
        |  this : Build =>
        |
        |  lazy val common = Project(id = "common", base = file("common"))
      """.stripMargin +
        subApps.map(projFromName).mkString("\n","\n","\n\n") +
        s"  lazy val finalDeps = Seq(common,$subappsString)" +
        """
          |}
          |
        """.stripMargin

    val outputFile = output / "Subprojects.scala"

    println(outputFile.getAbsolutePath)

    IO.write(outputFile,source)

    Seq(outputFile)
  }

}

object build extends Build {
  lazy val root = project.in(file(".")).settings(
    sourceGenerators in Compile <+= (sourceManaged in Compile,baseDirectory in Compile) map { (out,base) =>
      SubprojectGenerator.generate(out,base)
    }
  )
}
