package com.rockthejvm

//Imports
import sttp.client4._

// This file handles the API calls and structures data for Data analysis
//CLAUDE was used to help with importing sttp.client4 & API call
object Data {
  def getData(): Unit = {
    val backend = DefaultSyncBackend()

    val apiKey = "Api_Key" //Don't push this to github
    val datasetId = 181 //Testing

    val response = basicRequest
      .get(uri"https://data.fingrid.fi/api/v1/variable/$datasetId/events/json"
        .addParam("startTime", "2026-01-01T00:00:00Z")
        .addParam("endTime", "2026-01-02T00:00:00Z")
        .addParam("format", "json")
        .addParam("page", "1")
        .addParam("pageSize", "100"))
      .header("x-api-key", apiKey)
      .send(backend)

    println(response.code)
    response.body match {
      case Right(body) => println(s"Success: $body")
      case Left(error) => println(s"Error: $error")
    }
    backend.close()
  }

  def main(args: Array[String]):Unit={
    getData()
  }
}
