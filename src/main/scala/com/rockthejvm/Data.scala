package com.rockthejvm

//Imports
import sttp.client4._

//Made by Aarni Viljanen

//This file handles the API calls and structures data for Data analysis
//CLAUDE was used to help with importing sttp.client4 (In build.sbt) & API call & jsonToList
object Data {

  //HandleData validates the API call's params before calling getData
  //RETURN Either[Error,List(power values)]
  def handleData(datasetId:Int):Either[String,List[Double]]={
    //Call inputDate (which prompts user to give date and the returns valid dateRange)
    DateHandler.inputDate(datasetId) match{
      //DateRange is validated, continue
      case Right((start,end)) => val dateRange=List(start,end)
        //Call getData (Which can now make the API call with valid datasetID and dateRange)
        Data.getData(datasetId,dateRange) match{
          //API call was successful & returns list of data
          case Right(data) => Right(data)
          //API call was failure & return error message what went wrong
          case Left(error) => Left(error)
        }
      //Date range is not valid & returns error message what went wrong
      case Left(error) => Left(error)
    }
  }

  //getData makes API calls (to get data)
  //RETURN Either[Error,List(power values)]
  //CLAUDE WAS USED WHILE MAKING THE RESPONSE PART (MARKED BELOW)
  def getData(datasetID:Int,dateRange:List[String]):Either[String,List[Double]]={ //Add date in parameters
    val backend = DefaultSyncBackend()

    //Set API-key locally with
    val apiKey = ""
    //val datasetId = 75 //Wind power generated
    //val datasetId = 248 //Solar power
    //val datasetId = 191 //Water power

    //Response (THIS PART WAS MADE WITH HELP OF CLAUDE)
    val response = basicRequest
      .get(uri"https://data.fingrid.fi/api/datasets/$datasetID/data"
        .addParam("startTime", dateRange(0))
        .addParam("endTime", dateRange(1))
        .addParam("format", "json")
        .addParam("page", "1")
        .addParam("pageSize", "100"))
      .header("x-api-key", apiKey)
      .send(backend)

    //Close backend
    backend.close()
    //(CLAUDE HELP PART ENDS HERE)

    response.body match {
      //List is returned if success
      case Right(body) => Right(jsonToList(body))
      //Else return error
      case Left(error) => Left(s"Error: $error")
    }
  }

  //jsonToList turns returned JSON from API call to List[Double]
  //RETURN List(power values)
  //THIS FUNCTION WAS MADE WITH HELP OF CLAUDE
  def jsonToList(json:String):List[Double]={
    val pattern=""""value"\s*:\s*([\d.]+)""".r
    pattern.findAllMatchIn(json)
      .map(_.group(1).toDouble)
      .toList
  }
  //(CLAUDE HELP PART ENDS HERE)
}
