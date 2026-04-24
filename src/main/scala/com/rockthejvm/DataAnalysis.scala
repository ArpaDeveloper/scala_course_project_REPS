package com.rockthejvm

//Imports
import scala.annotation.tailrec

//Made by Aarni Viljanen

//HELP SOURCE: (This was used for all the rounding in all functions)
//https://stackoverflow.com/questions/11106886/scala-doubles-and-precision
object DataAnalysis {

  //Calculates sum of all elements in the list
  //RETURN Sum
  def sum(l:List[Double]):Double={
    if(l.isEmpty) 0
    else if(l.size==1) l.head
    else l.head+sum(l.tail)
  }

  //Sorts the list using Insertion sort
  //RETURN Sorted List[Double]
  def insertionSort(l:List[Double]):List[Double]={
    def insert(elem:Double,l:List[Double]):List[Double]=l match{
      //List is empty return elem
      case Nil=>List(elem)
      //Divide list to head and rest (tail)
      case head::tail =>
        //If elem is smaller than head, put it in front
        if (elem<=head)elem::l
        //Else recurse elem should be deeper
        else head::insert(elem,tail)
    }

    l match{
      //Empty list
      case Nil=>Nil
      //Sort the tail first & insert head into sorted tail
      case head::tail =>insert(head,insertionSort(tail))
    }
  }


  //Calculates mean of all elements in the list
  //RETURN mean
  def mean(l:List[Double]):Double={
    val s=sum(l)
    if(l.isEmpty) 0
    else BigDecimal(s/l.size).setScale(2,BigDecimal.RoundingMode.HALF_UP).toDouble
  }

  //Calculates median of all elements in the list
  //RETURN Median
  @tailrec
  def median(l:List[Double]):Double={
    if(l.isEmpty) 0
    //Sort the list
    val sorted_l=insertionSort(l)
    //If even average of the two numbers in middle
    if(l.size==1){ //If there is one return that
      BigDecimal(l.head).setScale(2,BigDecimal.RoundingMode.HALF_UP).toDouble
    }
    else if(l.size==2){ //If there is two put them together and get avg
      val avg=(l.head+l.last)/2
      BigDecimal(avg).setScale(2,BigDecimal.RoundingMode.HALF_UP).toDouble
    }
    else{ //Recurse the list without first and last elements
      median(sorted_l.tail.init)
    }
  }

  //Calculates mode of all elements in the list
  //RETURN mode
  def mode(l:List[Double]):List[Double]={
    if(l.isEmpty) List(0)
    else {
      //Sort the list & count frequencies
      val sorted_l=insertionSort(l)
      val freq_l=countFreq(sorted_l)
      //Find Mode
      def findMax(l:List[(Double,Int)],max:Int,acc:List[(Double,Int)]):List[(Double,Int)]={
        if(l.isEmpty) acc
        else{
          val (x,y)=l.head
          if (y>max) {
            //New mode is found reset list
            findMax(l.tail,y,List((x,y)))
          }
          else if(y==max) {
            //If its tie add it to list
            findMax(l.tail,max,(x,y)::acc)
          }
          else {
            //Recurse
            findMax(l.tail,max,acc)
          }
        }
      }
      //Get mode
      val mode=findMax(freq_l,0,Nil)
      //Make it to list + round it up
      mode.map{
        case (x,_)=>
        BigDecimal(x).setScale(2,BigDecimal.RoundingMode.HALF_UP).toDouble
      }
    }
  }

  //Method to count freq of sorted list
  //RETURN List[(Value,valueCount),...]
  def countFreq(l:List[Double]):List[(Double,Int)]=
    l.foldLeft(List.empty[(Double,Int)]){
      //Acc is empty First number (New number,Count=1)
      case (Nil,x)=>
        List((x,1))
      //Value is same as last one (Number->Count+1)
      case ((value,count)::rest,x)
        if(value==x)=>
        (value,count+1)::rest
      //New number is encountered Acc(New number,Count=1)
      case (acc,x)=>
        (x,1)::acc
    }

  //Calculates range of all elements in the list
  //RETURN range
  def range(l:List[Double]):Double={
    if(l.isEmpty) 0
    else {
      //Sort the list
      val sorted_l = insertionSort(l)
      //Subtract max from min
      val min=sorted_l.head
      val max=sorted_l.last
      BigDecimal(max-min).setScale(2,BigDecimal.RoundingMode.HALF_UP).toDouble
    }
  }

  //Calculates midrange of all elements in the list
  //RETURN midrange
  def midrange(l:List[Double]):Double={
    //Divide range by 2 + return rounded
    BigDecimal(range(l)/2).setScale(2,BigDecimal.RoundingMode.HALF_UP).toDouble
  }

}
