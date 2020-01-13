package dev.stef.app

import dev.stef.manager.es.ElasticSearchManager
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

import scala.util.{Try, Success, Failure}

object SparkyQwantApp {

  def readTextFileWithTry(path: String): Try[List[String]] = {
    import dev.stef.utils.Control._
    import scala.io.Source

    Try {
      val lines = using(Source.fromFile(path)) { source =>
        (for (line <- source.getLines) yield line).toList
      }
      lines
    }
  }

  def loadQueries(path: String)= {
    val passwdFile = readTextFileWithTry(path)
    passwdFile match {
      case Success(lines) => lines
      case Failure(e) =>
        new Exception(s"Failed to retrieve queries from file: ${e.printStackTrace()}")
        List[String]("")
    }
  }

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

    val input = "input.txt" // input file - one query per line

    val qwm = QwantManager(spark)
    val queries = loadQueries("src/test/resources/input_test.txt")
    qwm.runWebSearch(queries)


    val esm = new ElasticSearchManager(spark)

    val output = "albumindex"

    esm.writeToIndex(output)

    esm.read(output).show(false)
  }
}
