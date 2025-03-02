package com.rmurugaian.scala.common.util

import scala.util.matching.Regex

import com.rmurugaian.scala.common.{BadRequestException, MultiRepartition, ValueRepartition}

object MultiRepartitionParser {

  private val PIPE: Regex  = "\\|".r
  private val COLON: Regex = ":".r
  private val COMMA: Regex = ",".r

  // multiPartition=flex_id|A:12,B:13,C:13
  def parse(multiPartition: String): MultiRepartition = {
    val firstSplit = PIPE.split(multiPartition)

    if (firstSplit.length != 2)
      throw new BadRequestException("multipartition format is wrong")

    val columnName = firstSplit(0)
    val valueRepartitions = COMMA
      .split(firstSplit(1))
      .map(COLON.split(_))
      .map(it => ValueRepartition(it(0), it(1).toInt))
      .toSet

    MultiRepartition(columnName, valueRepartitions)
  }

}
