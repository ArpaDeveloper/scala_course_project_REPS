package com.rockthejvm

//Imports
import scala.io.StdIn.readLine

object Main {
  def main(args: Array[String]):Unit={
    var loop=true
    while(loop){
      println("Renewable Energy Plant System Interface")
      println("Option 1")
      println("Option 2")
      println("Option 3")
      println("Enter your choice:")
      val input=readLine()
      val choice = checkInput(input)

      choice match {
        case Some(1) => println("Option1")
        case Some(2) => println("Option2")
        case Some(3) => loop=false
        case _ => println("Not recognized input")
      }
      println("Exiting...")
    }

    def checkInput(s:String):Option[Int]={
      try{
        Some(s.toInt)
      }catch{
        case _: Exception => None
      }
    }

  }
}
