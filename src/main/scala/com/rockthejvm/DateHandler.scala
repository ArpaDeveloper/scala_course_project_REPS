package com.rockthejvm

//Imports
import java.time.LocalDateTime
import java.time.format.{DateTimeFormatter, DateTimeParseException}
import scala.io.StdIn.readLine

//Made by Aarni Viljanen

//Handles everything related to date chosen for the API call
object DateHandler {

  //InputHandles users input choice regarding date
  //Returns List(startDate,endDate)
  def inputDate(energySource:Int):Either[String,(String,String)]={
    //Prompt user to select time period
    println("Select range (starting from your input)\n1.Hour\n2.Day\n3.Week\n4.Month")
    //Get input
    val input=readLine()
    //Validate input with checkInput
    val choice=Main.checkInput(input)
    choice match{
      case Some(1)=> //Hourly
        println("Enter year-month-date(Time)hour; Example:(2026-04-17T12)")
        //Call createDateRange(UserInput,TimePeriod,EnergySource)
        createDateRange(readLine(),1,energySource)
      case Some(2)=> //Daily
        println("Enter year-month-date; Example:(2026-04-17)")
        //Call createDateRange(UserInput,TimePeriod,EnergySource)
        //T00 is added in userInput, because we presume then that the hour is 00:00
        createDateRange(readLine()+"T00",2,energySource)
      case Some(3)=> //Weekly
        println("Enter year-month-date; Example:(2026-04-17)")
        //Call createDateRange(UserInput,TimePeriod,EnergySource)
        //T00 is added in userInput, because we presume then that the hour is 00:00
        createDateRange(readLine()+"T00",3,energySource)
      case Some(4)=> //Monthly
        println("Enter year-month-date; Example:(2026-04-17)")
        //Call createDateRange(UserInput,TimePeriod,EnergySource)
        //T00 is added in userInput, because we presume then that the hour is 00:00
        createDateRange(readLine()+"T00",4,energySource)
      case _=>
        //Error not valid choice (Not in 1-4)
        Left("Invalid choice for range")
    }
  }

  //parseDate checks if the user input the date in correct format
  //Returns Either[Error,ParsedDate]
  //HELP SOURCE: https://www.hackingnote.com/en/scala/datetime/
  def parseDate(s:String):Either[String,LocalDateTime]={
    //yyyy-MM-ddThh:mm:ssZ (set Format)
    val format=DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH")
    try {
      //Parse date to right format
      Right(LocalDateTime.parse(s,format))
    } catch {
      //If parsing fails return error
      case _: DateTimeParseException=>Left("Invalid date format. Example of correct:(2026-04-17)")
    }
    //(HELP SOURCE END)
  }

  //createDateRange process the parsed date & returns a valid range
  //Returns Either[Error,List(startDate,endDate)]
  //HELP SOURCES:
  //https://www.baeldung.com/scala/map-vs-flatmap
  //https://www.hackingnote.com/en/scala/datetime/
  //https://www.geeksforgeeks.org/java/localdatetime-withhour-method-in-java-with-examples/
  def createDateRange(s:String,timePeriod:Int,energySource:Int):Either[String,(String,String)]={
    parseDate(s).flatMap{parsed =>
      //Check if parsed date is in the future
      if(parsed.isAfter(LocalDateTime.now())){
        //Return error Date can't be in Future
        Left("Date can't be in Future")
      }else{
        //Else check if the date is not too far in the past
        //RETURN Either[Error,EarliestDateForChosenEnergySource]
        val minDateEither:Either[String,LocalDateTime]=energySource match {
          //For water & wind power we can access data starting from 1.1.2024
          case 75|191 =>
            //Return minimum date for chosen energy source
            Right(LocalDateTime.of(2014,1,1,0,0))
          //For solar power we can access data starting from 24.2.2017
          case 248 =>
            //Return minimum date for chosen energy source
            Right(LocalDateTime.of(2017,2,24,0,0))
          case _ =>
            //Return error
            Left("Invalid energy source")
        }

        minDateEither.flatMap{minDate =>
          //Check that parsed date is not before the minimum date for chosen energy source
          if(parsed.isBefore(minDate)){
            //Return error, because there is no data available before minimum date
            Left(s"No data available before ${minDate.toLocalDate}")
          }else{
            //Date is now validated, we need to return the right range
            val result=timePeriod match {
              case 1 => //Hourly (Range is start-end=1h)
                //We need to add minutes,seconds to start, end time
                (parsed.withMinute(0).withSecond(0).toString+"Z",
                  parsed.withMinute(59).withSecond(59).toString+"Z")
              case 2 => //Daily (Range is start-end=24h)
                //We need to add hour,minutes,seconds to start, end time
                (parsed.withHour(0).withMinute(0).withSecond(0).toString+"Z",
                  parsed.withHour(23).withMinute(59).withSecond(59).toString+"Z")
              case 3 => //Weekly (Range is start-end=7d)
                //We need to add day,hour,minutes,seconds to start, end time
                (parsed.withHour(0).withMinute(0).withSecond(0).toString+"Z",
                  parsed.withHour(0).withMinute(0).withSecond(0).plusDays(7).toString+"Z")
              case 4 => //Monthly (Range is start-end=1month)
                (parsed.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).toString+"Z",
                  parsed.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).plusMonths(1).toString+"Z")
              case _ =>
                //Return error invalid time period
                return Left("Invalid time period")
            }
            //Return valid dateRange
            Right(result)
          }
        }
      }
    }
    //(HELP SOURCE END)
  }

}
