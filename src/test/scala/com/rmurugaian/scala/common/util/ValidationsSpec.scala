package com.rmurugaian.scala.common.util

import com.rmurugaian.scala.common.BadRequestException
import org.scalatest.GivenWhenThen
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._

class ValidationsSpec extends AnyFlatSpec with GivenWhenThen {

  "Validations.notEmpty" should "return the value if it is not empty" in {
    Given("A non-empty value and a property name")
    val value        = "value"
    val propertyName = "property"

    When("The validation is executed")
    val result = Validations.notEmpty(value, propertyName)

    Then("It should return the input value")
    result shouldBe value
  }

  it should "throw a BadRequestException for an empty value" in {
    Given("An empty value and a property name")
    val value        = ""
    val propertyName = "flex_id"

    When("The validation is executed")
    val exception =
      intercept[BadRequestException](Validations.notEmpty(value, propertyName))

    Then("It should throw a BadRequestException with the correct message")
    exception.getMessage shouldBe "Config flex_id property is mandatory"
  }
}
