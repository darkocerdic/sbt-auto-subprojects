import sbt._
import Keys._

object build extends Build with Subprojects{
  lazy val root1 = Project(id="main", base=file("."))
    .aggregate(finalDeps.map(d=>sbt.Project.projectToRef(d)):_*)
    .dependsOn(finalDeps.map(ClasspathDependency(_, None)):_*)
}
