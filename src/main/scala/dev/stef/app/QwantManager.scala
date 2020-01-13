package dev.stef.app

import org.apache.spark.sql.SparkSession

case class QwantManager(spark: SparkSession) {

  def run(q: String) = {

  }

  def runWebSearch(queries: List[String]) = {

    queries.map(q => run(q))

  }

}
