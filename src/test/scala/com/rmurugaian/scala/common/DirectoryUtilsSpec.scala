package com.rmurugaian.scala.common

import com.rmurugaian.scala.common.util.DirectoryUtils
import org.scalatest.GivenWhenThen
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class DirectoryUtilsSpec extends AnyFlatSpec with GivenWhenThen {

  "DirectoryUtils.findDirs" should
    "return unique directories for valid file paths" in {
      Given("A base directory and a list of valid file paths")
      val basePath =
        "development/data/akhare200/digital_classification_parquet_output_temp"
      val files = Seq(
        s"$basePath/header_day_id=2020-10-27/asset=eas/part-00367-tid-123.parquet",
        s"$basePath/header_day_id=2020-10-28/asset=eas/part-00367-tid-456.parquet"
      )

      When("The directories are extracted")
      val result = DirectoryUtils.findDirs(basePath, files)

      Then("The result should contain unique directories")
      result shouldBe Set("header_day_id=2020-10-27", "header_day_id=2020-10-28")
    }

  it should "handle duplicate file paths and return unique directories" in {
    Given("A base directory and a list of duplicate file paths")
    val basePath =
      "development/data/akhare200/digital_classification_parquet_output_temp"
    val files = Seq(
      s"$basePath/header_day_id=2020-10-27/asset=eas/part-00367-tid-123.parquet",
      s"$basePath/header_day_id=2020-10-27/asset=eas/part-00367-tid-123.parquet"
    )

    When("The directories are extracted")
    val result = DirectoryUtils.findDirs(basePath, files)

    Then("The result should contain only unique directories")
    result shouldBe Set("header_day_id=2020-10-27")
  }

  it should
    "handle files with different inner directories and return all unique directories" in {
      Given(
        "A base directory and a list of files with different inner directories"
      )
      val basePath =
        "development/data/akhare200/digital_classification_parquet_output_temp"
      val files = Seq(
        s"$basePath/header_day_id=2020-10-27/asset=eas/part-00367-tid-123.parquet",
        s"$basePath/header_day_id=2020-10-27/asset=lan/part-00367-tid-456.parquet",
        s"$basePath/header_day_id=2020-10-28/asset=eas/part-00367-tid-789.parquet"
      )

      When("The directories are extracted")
      val result = DirectoryUtils.findDirs(basePath, files)

      Then("The result should contain all unique directories")
      result shouldBe Set("header_day_id=2020-10-27", "header_day_id=2020-10-28")
    }

  it should "return an empty set for invalid file paths" in {
    Given("A base directory and a list of invalid file paths")
    val basePath = "invalid"
    val files    = Seq("invalid")

    When("The directories are extracted")
    val result = DirectoryUtils.findDirs(basePath, files)

    Then("The result should be an empty set")
    result shouldBe Set()
  }

  it should "return an empty set for an empty input file list" in {
    Given("A base directory and an empty list of file paths")
    val basePath =
      "development/data/akhare200/digital_classification_parquet_output_temp"
    val files = Seq.empty[String]

    When("The directories are extracted")
    val result = DirectoryUtils.findDirs(basePath, files)

    Then("The result should be an empty set")
    result shouldBe Set()
  }
}
