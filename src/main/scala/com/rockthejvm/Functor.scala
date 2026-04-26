package com.rockthejvm

//Made by Aarni & Eeli

//Sources
//https://rockthejvm.com/articles/what-the-functor
//https://medium.com/@PerrottaFrancisco/functors-with-scala-cats-ef64153ba8d5

object Functor extends App {

  //Definition of Functor
  trait Functor[C[_]] {
    def map[A,B](container: C[A])(f:A=>B):C[B]
  }
  //Functor takes argument C (Lists, Options, etc)
  //Then it maps the initial argument C[A]->C[B]
  //Example it takes Option[Int] with Some[1]
  //then it map(_+1) and returns Some[2]

  //Example 1 (List)
  val nums1=List(1,2,3,4,5)
  nums1.map(x=>x+1)

  //Example 2 (Option)
  val nums2:Option[Int]=Some(69)
  val result=nums2.map(_-2)

  //Example 3 (Either)
  val x:Either[String,Int]=Right(2)
  val y:Either[String,Int]=Left("VilleValo")
  x.map(_+8)
  y.map(_+666)

  //Example 4 (Custom)
  case class pirateShip[A](treasure: A){
    def map[B](f:A=>B):pirateShip[B]=pirateShip(f(treasure))
  }

  val plunder=pirateShip(100).map(_+1)
}
