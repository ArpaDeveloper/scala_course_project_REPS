package com.rockthejvm

//Imports
import sttp.client4._

//Made by Aarni Viljanen

//This file handles the API calls and structures data for Data analysis
//CLAUDE was used to help with importing sttp.client4 (In build.sbt) & API call
object Data {
  def getData(datasetID:Int):Either[List[Double],Unit]={ //Add date in parameters
    val backend = DefaultSyncBackend()

    //Set API-key locally with
    val apiKey = ""
    //val datasetId = 75 //Wind power generated
    //val datasetId = 248 //Solar power
    //val datasetId = 191 //Water power


    //Response (This was made with help of Claude)
    val response = basicRequest
      .get(uri"https://data.fingrid.fi/api/datasets/$datasetID/data"
        .addParam("startTime", "2026-04-17T00:00:00Z")
        .addParam("endTime", "2026-04-17T23:59:59Z")
        .addParam("format", "json")
        .addParam("page", "1")
        .addParam("pageSize", "100"))
      .header("x-api-key", apiKey)
      .send(backend)

    //Close backend
    backend.close()

    response.body match {
      //List is returned if success
      case Right(body) => Left(jsonToList(body))
      //Else print error
      case Left(error) => Right(println(s"Error: $error"))
    }
  }

  //jsonToList made with help of Claude
  def jsonToList(json:String):List[Double]={
    val pattern=""""value"\s*:\s*([\d.]+)""".r
    pattern.findAllMatchIn(json)
      .map(_.group(1).toDouble)
      .toList
  }
}
