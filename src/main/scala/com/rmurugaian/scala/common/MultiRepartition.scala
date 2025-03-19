package com.rmurugaian.scala.common

case class MultiRepartition(
    columnName: String,
    partitions: Set[ValueRepartition],
)

case class ValueRepartition(value: String, repartition: Int)
