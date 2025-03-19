package com.rmurugaian.scala.common.util

import com.rmurugaian.scala.common.BadRequestException
import com.rmurugaian.scala.common.ValueRepartition
import org.scalatest.featurespec.AnyFeatureSpec
import org.scalatest.matchers.should.Matchers

class MultiRepartitionParserSpec extends AnyFeatureSpec with Matchers {

  Scenario("Parse repartition config") {
    val result = MultiRepartitionParser.parse("flex_id|A:12,B:13,C:13")
    result.columnName shouldBe "flex_id"
    result.partitions.size shouldBe 3
    result.partitions.head shouldBe ValueRepartition("A", 12)
    result.partitions.tail shouldBe
      Set(ValueRepartition("B", 13), ValueRepartition("C", 13))
  }

  Scenario("Verify Bad Request Exception for invalid partition format") {
    val result =
      intercept[BadRequestException](MultiRepartitionParser.parse("flex_id"))

    result.getMessage shouldBe "multi partition format is wrong"
  }

}
