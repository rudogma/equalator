package org.rudogma.equalator

import shapeless.labelled.FieldType
import shapeless._

object Implicits {

  implicit val EqualatorByte: Equalator[Byte] = Equalator((a, b) => a == b)
  implicit val EqualatorShort: Equalator[Short] = Equalator((a, b) => a == b)
  implicit val EqualatorInt: Equalator[Int] = Equalator((a, b) => a == b)
  implicit val EqualatorLong: Equalator[Long] = Equalator((a, b) => a == b)
  implicit val EqualatorBoolean: Equalator[Boolean] = Equalator((a, b) => a == b)
  implicit val EqualatorFloat: Equalator[Float] = Equalator((a, b) => a == b)
  implicit val EqualatorDouble: Equalator[Double] = Equalator((a, b) => a == b)
  implicit val EqualatorChar: Equalator[Char] = Equalator((a, b) => a == b)

  implicit val EqualatorString: Equalator[String] = Equalator((a, b) => a == b)


  implicit def equalatorArray[T: Equalator](implicit itemEqualator:Equalator[T]): Equalator[Array[T]] = new Equalator[Array[T]] {

    override protected def deepEqualsImpl(a: Array[T], b: Array[T])(implicit path: Path) = {

      if(a == null || b == null){
        if(a != null){
          Left(Error("Arrays. Left array is null"))
        }else if(b != null){
          Left(Error("Arrays. Right array is null"))
        }else{
          Right(Equalator.True)
        }
      }else if(a.length != b.length){
        Left(Error(s"Non equal array length. Left.length=${a.length}, Right.length=${b.length}"))
      }else{

        a.zip(b).zipWithIndex.map{ case ((a, b), index) => itemEqualator.deepEquals(a,b)(path :+ Symbol(s"[${index}]"))}.collectFirst {
          case x @ Left(e) => x
        } match {
          case Some(x) => x
          case None => Right(Equalator.True)
        }
      }
    }
  }

  implicit val equalatorForHList_HNil:Equalator[HNil] = new Equalator[HNil] {
    override protected def deepEqualsImpl(a: HNil, b: HNil)(implicit path: Path) = Right(Equalator.True)
  }

  implicit def equalatorForHList_Item[Name <: Symbol, Type, Tail <: HList](implicit name:Witness.Aux[Name], head:Lazy[Equalator[Type]], nested:Equalator[Tail]):Equalator[FieldType[Name,Type] :: Tail] = new Equalator[FieldType[Name,Type] :: Tail] {
    protected def deepEqualsImpl(a: FieldType[Name,Type] :: Tail, b: FieldType[Name,Type] :: Tail)(implicit path: Path) = {

      for {
        _ <- head.value.deepEquals(a.head, b.head)(path :+ name.value).right
        x <- nested.deepEquals(a.tail, b.tail).right
      } yield x
    }
  }


  implicit def equalatorForGeneric[T, Fields <: HList](implicit generic:LabelledGeneric.Aux[T, Fields], nested:Lazy[Equalator[Fields]]):Equalator[T] = new Equalator[T] {

    override protected def deepEqualsImpl(a: T, b: T)(implicit path: Path) = {

      nested.value.deepEquals(
        generic.to(a),
        generic.to(b)
      )
    }
  }
}
