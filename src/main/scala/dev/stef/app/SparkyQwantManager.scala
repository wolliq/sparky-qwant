package dev.stef.app

import dev.stef.es.ElasticSearchManager
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

object SparkyQwantManager {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf()
      .set("spark.es.nodes","127.0.0.1")
      .set("spark.es.port","9200")
      .set("spark.es.index.auto.create", "true")
//      .set("spark.es.resource", "writeindex/albumindex")
//      .set("spark.es.resource.read", "writeindex/albumindex")
//      .set("spark.es.resource.write", "readindex/albumindex")
      .setAppName("sparky-qwant")
      .setMaster("local[*]")

    val sc = new SparkContext(conf)

    val spark = SparkSession.builder
      .config(sc.getConf)
      .getOrCreate()

    val path = "albumindex"

    val esm = new ElasticSearchManager(spark)

    esm.writeToIndex(path)

    esm.read(path).show(false)

  }
}
