package dev.stef.es

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.elasticsearch.spark.sql._

case class AlbumIndex(artist:String, yearOfRelease:Int, albumName: String)

class ElasticSearchManager(spark: SparkSession) {


  def read(path: String): DataFrame = {

    // DataFrame schema automatically inferred
    spark.read.format("es").load(path)
  }


  def writeToIndex(pathToWrite: String): Unit = {

    import spark.implicits._

    val indexDocuments = Seq(
      AlbumIndex("Led Zeppelin",1969,"Led Zeppelin"),
      AlbumIndex("Boston",1976,"Boston"),
      AlbumIndex("Fleetwood Mac", 1979,"Tusk")
    ).toDF

    indexDocuments.saveToEs(pathToWrite)
//    //indexDocuments.saveToEs(pathToWrite)
//    indexDocuments.write
//      .format("org.elasticsearch.spark.sql")
//      .mode("overwrite")
//      .saveAsTable(pathToWrite)
  }
}


object ElasticSearchManager {}