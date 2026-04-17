package com.rockthejvm

//Imports
import scala.annotation.tailrec

//Made by Aarni Viljanen

object DataAnalysis {

  //Add error handling + some type stuff like prob can't have int on all


  //Calculates sum of all elements in the list
  def sum(l:List[Int]):Int={
    if(l.isEmpty) 0
    else if(l.size==1) l.head
    else l.head+sum(l.tail)
  }

  //Sorts the list using Insertion sort
  def insertionSort(l:List[Int]):List[Int]={
    def insert(elem:Int,l:List[Int]):List[Int]=l match{
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
  def mean(l:List[Int]):Float={
    val s=sum(l)
    if(l.isEmpty) 0
    else s/l.size
  }

  //Calculates median of all elements in the list
  @tailrec
  def median(l:List[Int]):Float={
    if(l.isEmpty) 0
    //Sort the list
    val sorted_l=insertionSort(l)
    //If even average of the two numbers in middle
    if(l.size==1){ //If there is one return that
      l.head
    }
    else if(l.size==2){ //If there is two put them together and get avg
      (l.head+l.last)/2
    }
    else{ //Recurse the list without first and last elements
      median(sorted_l.tail.init)
    }
  }

  //FIX MODE
  //Calculates mode of all elements in the list
  def mode(l:List[Int]):Int={
    if(l.isEmpty) 0
    else {
      val sorted_l=insertionSort(l)
      countFreq(sorted_l,0,0)
    }
  }

  //Method to count freq of sorted list
  def countFreq(l:List[Int],current_freq:Int,count:Int):Int={
    if(current_freq==l.head) countFreq(l.tail, current_freq,count+1)
    else{
      countFreq(l.tail,l.head,1)
    }
    countFreq(l.tail, current_freq,count)
  }

  //Calculates range of all elements in the list
  def range(l:List[Int]):Int={
    if(l.isEmpty) 0
    else {
      //Sort the list
      var sorted_l=insertionSort(l)
      //Subtract max from min
      val min=sorted_l.head
      val max=sorted_l.last
      max-min
    }
  }

  //Calculates midrange of all elements in the list
  def midrange(l:List[Int]):Int={
    //Divide range by 2
    range(l)/2
  }

}
