//import sbt._
//import Keys._
//
//trait Subprojects2 {
//  this : Build =>
//
//  lazy val common = Project(id = "common", base = file("common"))
//  lazy val app1 = Project(id = "app1", base = file("apps/app1")).dependsOn(common)
//  lazy val app2 = Project(id = "app2", base = file("apps/app2")).dependsOn(common)
//
//  lazy val finalDeps = Seq(app1,app2,common)
//}