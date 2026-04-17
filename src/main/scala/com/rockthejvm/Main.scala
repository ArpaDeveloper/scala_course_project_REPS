package com.rockthejvm

//Imports
import scala.io.StdIn.readLine

//Made by Aarni Viljanen

object Main {
  def main(args: Array[String]):Unit={
    var loop=true
    while(loop){
      println("Renewable Energy Plant System Interface")
      println("1.Monitor and Control resources (Get from API)")
      println("2.Collect Data (Save to File)")
      println("3.View energy generation and storage capacity (Show file)")
      println("4.Data Analysis")
      println("5.Close program")
      println("Enter your choice:")
      val input=readLine()
      val choice = checkInput(input)

      //Test list
      val l:List[Int]=List(1,5,3,8,1,10,19,20)

      choice match {
        case Some(1) => {
          println("Api")
        }
        case Some(2) => {
          println("Save file")
        }
        case Some(3) => {
          println("Show file")
        }
        //Data Analysis
        case Some(4) => {
          val inner_choice=dataAnalysisChoice()
          inner_choice match{
            //Mean
            case Some(1)=>{
              val mean=DataAnalysis.mean(l)
              println(s"Mean is ${mean}")
            }
            //Median
            case Some(2)=>{
              val median=DataAnalysis.median(l)
              println(s"Median is ${median}")
            }
            //Mode
            case Some(3)=>{
              val mode=DataAnalysis.mode(l)
              println(s"Mode is ${mode}")
            }
            //Range
            case Some(4)=>{
              val range=DataAnalysis.range(l)
              println(s"Range is ${range}")
            }
            //Midrange
            case Some(5)=>{
              val midrange=DataAnalysis.midrange(l)
              println(s"Midrange is ${midrange}")
            }
            //Bad input
            case _ => println("Not recognized operation")
          }
        }
        //Close
        case Some(5) => {
          println("Exiting...")
          loop=false
        }
        //Wrong input
        case _ => println("Not recognized input")
      }
    }

    //Helper function for checking input
    def checkInput(s:String):Option[Int]={
      try{
        Some(s.toInt)
      }catch{
        case _: Exception => None
      }
    }

    //DataAnalysis helper func
    def dataAnalysisChoice():Option[Int]={
      println("Select operation")
      println("1.Mean")
      println("2.Median")
      println("3.Mode")
      println("4.Range")
      println("5.Midrange")
      println("Enter your choice:")
      val input=readLine()
      val choice = checkInput(input)
      choice
    }

  }
}
