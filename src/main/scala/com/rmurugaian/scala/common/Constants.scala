package com.rmurugaian.scala.common

import java.time.format.DateTimeFormatter

object Constants {

  // val custom broker aws access properties
  val PIPE_DELI = "\\|"
  val TEXT = "text"
  val METRICS_CAPTURE_REPARTITION = "1"
  val METRICS_CAPTURE_INPUT_VIEW_NAME = "metrics_capture_view"
  val METRICS_CAPTURE_INPUT_FORMAT = "parquet"
  val METRIC_GROUPS_VIEW_NAME = "dq_metric_groups"
  val METRIC_VALUES_VIEW_NAME = "dq_metric_values"
  val ETL_CHECK_THRESHOLDS_VIEW_NAME = "dq_etl_check_thresholds"
  private val DAY_ID_FORMAT = "yyyy-MM-dd"

  lazy val TEMP_OUTPUT_FORMAT: String = "parquet"

  val MULTIPART_FILE_SIZE = 5368709120L

  lazy val DAY_ID_FORMATTER: DateTimeFormatter = DateTimeFormatter
    .ofPattern(DAY_ID_FORMAT)
  lazy val ADHOC_PROCESSING_DATE = "adhoc.processing.date"
  lazy val PLUS_OR_MINUS_DAYS = "plus.or.minus.days"
  lazy val ETL_CHECK_PLUS_OR_MINUS_DAYS = "etl.check.plus.or.minus.days"
  lazy val METRICS_DATE_INPUT_PATH = "get.metrics.date.input.path"
  lazy val METRICS_PROCESSING_DATE = "processing.day.from.file"
  lazy val ETL_CHECK_BY_WEEK_OVER_WEEK = "etl.check.by.week.over.week"
  lazy val DQ_LAG_DAYS_FROM_CURRENT = "dq_lag_days_from_current"

}
