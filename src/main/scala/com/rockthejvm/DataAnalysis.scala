package com.rockthejvm

import scala.annotation.tailrec

object DataAnalysis {

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
  def mean(l:List[Int]):Int={
    val s=sum(l)
    if(l.isEmpty) 0
    else s/l.size
  }

  //Calculates median of all elements in the list
  def median(l:List[Int]):Int={
    if(l.isEmpty) 0
    //Sort the list
    val sorted_l=insertionSort(l)
    //If even average of the two numbers in middle
    median(l.tail)
    //Else the number in middle
    //Has to be fixed

  }

  //Calculates mode of all elements in the list
  //def Mode

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
