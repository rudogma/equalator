package org.rudogma.equalator.tests

import org.rudogma.equalator._
import org.rudogma.equalator.Implicits._
import org.scalatest.{FlatSpec, Matchers}

class Test extends FlatSpec with Matchers {

  it should "work" in {

    val v1 = T1(1, "2", Array(3,4))
    val v2 = T1(1, "2", Array(3,5))

    Equalator[T1].deepEquals(v1, v2) match {
      case Left(e) => e.message shouldBe "Left != Right, at $root.field2.[1]"
      case Right(x) => ???
    }

    val v3 = T2(5, v1)
    val v4 = T2(5, v2)

    Equalator[T2].deepEquals(v3, v4) match {
      case Left(e) => e.message shouldBe "Left != Right, at $root.field1.field2.[1]"
      case Right(x) => ???
    }

    val v5 = T2(5, null)
    val v6 = T2(5, null)

    Equalator[T2].deepEquals(v5, v6) match {
      case Left(e) =>
        println(e.message)
        e.printStackTrace()
        ???
      case Right(x) => //pass
    }
  }


  case class T1(field0:Int, field1:String, field2:Array[Int])
  case class T2(field0:Int, field1:T1)
}
