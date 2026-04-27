package com.rockthejvm

//Imports
import scala.io.StdIn.readLine

//Made by Aarni Viljanen, Eeli Remes

object Main {
  def main(args: Array[String]):Unit={
    var loop=true
    var l:List[Double]=List()
    while(loop){
      println("Renewable Energy Plant System Interface")
      println("1.Monitor and Control resources (Get from API)")
      println("2.Collect Data (Save to File)")
      println("3.View energy generation and storage capacity (Show file)")
      println("4.Data Analysis")
      println("5.Close program")
      println("Enter your choice:")
      var input=readLine()
      var choice=checkInput(input)

      choice match{

        //Get data from API
        case Some(1) =>
          //Choose renewable energy source
          val energySource=innerChoice(1)
          //Print according to choice
          energySource match{
            case Some(1) => //Wind power (ID:75)
              Data.handleData(75) match{
                case Right(data)=>
                  println(s"Success getting wind power\n")
                  l=data
                case Left(error)=>println(error+"\n")
              }
            case Some(2) => //Solar power (ID:248)
              Data.handleData(248) match{
                case Right(data)=>
                  println(s"Success getting solar power\n")
                  l=data
                case Left(error)=>println(error+"\n")
              }
            case Some(3) => //Water power (ID:191)
              Data.handleData(191) match{
                case Right(data)=>
                  println(s"Success getting water power\n")
                  l=data
                case Left(error)=>println(error+"\n")
              }
            //Bad input
            case _ => println("Not valid choice\n")
          }

        //Save Data to file
        case Some(2) => {
          if (l.isEmpty) {
            println("No data to save. Please fetch data from the API first.\n")
          } else {
            FileHandler.saveData(l) match {
              case Right(_) => println("Data successfully saved to file.\n")
              case Left(error) => println(error + "\n")
            }
          }
        }
        //Get Data from file
        case Some(3) => {
          FileHandler.loadData() match {
            case Right(data) =>
              l = data // Update the local state `l` with data from the file
              println(s"Successfully loaded ${l.size} values from file.\n")
            case Left(error) => println(error + "\n")
          }
        }

        //Data Analysis
        case Some(4) => {
          val inner_choice=innerChoice(2)
          inner_choice match{
            //Mean
            case Some(1)=>
              val mean=DataAnalysis.mean(l)
              println(s"Mean is ${mean}")
            //Median
            case Some(2)=>
              val median=DataAnalysis.median(l)
              println(s"Median is ${median}")
            //Mode
            case Some(3)=>
              val mode=DataAnalysis.mode(l)
              println(s"Mode is ${mode}")
            //Range
            case Some(4)=>
              val range=DataAnalysis.range(l)
              println(s"Range is ${range}")
            //Midrange
            case Some(5)=>
              val midrange=DataAnalysis.midrange(l)
              println(s"Midrange is ${midrange}")
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
  }

  //Helper function for checking input
  def checkInput(s:String):Option[Int]={
    try{
      Some(s.toInt)
    }catch{
      case _: Exception => None
    }
  }

  //InnerChoice helper func
  def innerChoice(c:Int):Option[Int]={
    if(c==1){
      println("Select energy source\n1.Wind power\n2.Solar power\n3.Water power")
    }
    else if(c==2){
      println("Select operation\n1.Mean\n2.Median\n3.Mode\n4.Range\n5.Midrange")
    }
    println("Enter your choice:")
    val input=readLine()
    val choice = checkInput(input)
    choice
  }
}
