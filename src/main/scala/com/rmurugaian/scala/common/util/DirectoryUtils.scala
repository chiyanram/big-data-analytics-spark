package com.rmurugaian.scala.common.util

object DirectoryUtils {

  def findDirs(key: String, paths: Seq[String]): Set[String] = paths
    .map(it => split(key, it)).filter(it => it.nonEmpty).map(it => it.get).toSet

  private def split(key: String, it: String) =
    try {
      val split = it.split(key.concat("/")).last
      Option(split.substring(0, split.indexOf("/")))
    } catch { case _: RuntimeException => Option.empty }
}
