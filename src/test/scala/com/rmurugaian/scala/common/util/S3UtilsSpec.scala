package com.rmurugaian.scala.common.util

import com.rmurugaian.scala.common.BadRequestException
import com.rmurugaian.scala.common.util.S3Utils.splitBucketAndKey
import org.scalatest.GivenWhenThen
import org.scalatest.featurespec.AnyFeatureSpec
import org.scalatest.matchers.should.Matchers._

class S3UtilsSpec extends AnyFeatureSpec with GivenWhenThen {

  Feature("Repartition Config Parsing") {

    Scenario("Successfully parse a valid S3 URL") {
      Given("a valid S3 URL")
      val s3Url = "s3://dx-viewing-artifactory/artifactory/common/"

      When("the S3 URL is split into components")
      val result = splitBucketAndKey(s3Url)

      Then("the bucket, key, and storage type should be correctly identified")
      result.bucketKey shouldBe "artifactory/common/"
      result.bucketName shouldBe "dx-viewing-artifactory"
      result.storageType shouldBe "s3://"
    }

    Scenario("Handle invalid S3 URL with Bad Request Exception") {
      Given("an invalid S3 URL")
      val invalidUrl = "flex_id"

      When("attempting to split the invalid URL")
      val exception =
        intercept[BadRequestException](S3Utils.splitBucketAndKey(invalidUrl))

      Then(
        "a BadRequestException should be thrown with the appropriate message",
      )
      exception.getMessage should
        include("Given sourcePath flex_id not matches with regex pattern")
    }
  }

}
