package com.rmurugaian.scala.common.util

import com.rmurugaian.scala.common.BadRequestException
import org.slf4j.LoggerFactory
import scala.util.matching.Regex

object S3Utils {

  private val logger = LoggerFactory.getLogger(S3Utils.getClass)

  private val s3Path: Regex = "(^s3a?://)([^/]*)/(\\S+)".r

  def splitBucketAndKey(sourcePath: String): S3SplitResponse = {
    val source = Validations.notEmpty(sourcePath, "sourcePath")
    val matchData = s3Path.findAllIn(source).matchData
    val groups = matchData.flatMap(it => it.subgroups).toList
    if (groups.isEmpty || groups.size != 3) throw new BadRequestException(
      s"Given sourcePath $source not matches with regex pattern: ${s3Path
          .regex}",
    )
    val s3SplitResponse = S3SplitResponse(groups.head, groups(1), groups(2))
    logger.info("bucket and key {} ", s3SplitResponse)
    s3SplitResponse
  }
}
