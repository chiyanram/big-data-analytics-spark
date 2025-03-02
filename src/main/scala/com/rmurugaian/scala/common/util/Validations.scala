package com.rmurugaian.scala.common.util

import com.rmurugaian.scala.common.BadRequestException

object Validations {

  @throws[BadRequestException]
  def notEmpty(value: String, propertyName: String): String = {
    if (value == null || value == "")
      throw new BadRequestException(
        s"Config $propertyName property is mandatory"
      )

    value
  }

}
