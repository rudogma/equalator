package org.rudogma.equalator

trait Equalator[T] {

  def deepEquals(a: T, b: T)(implicit path:Path = List('$root)): Either[Error, Equalator.True] = {
    try{
      deepEqualsImpl(a,b)
    }catch{
      case e:Throwable => Left(Error(s"Exception: ${e.getMessage}", e))
    }
  }

  protected def deepEqualsImpl(a: T, b: T)(implicit path:Path):Either[Error, Equalator.True]
}

object Equalator {

  case object True
  type True = True.type


  def apply[T]( f: (T,T) => Boolean):Equalator[T] = new Equalator[T] {
    override def deepEqualsImpl(a: T, b: T)(implicit path:Path): Either[Error, Equalator.True] = {
      if(f(a,b)){
        Right(Equalator.True)
      }else{
        Left(Error("Left != Right"))
      }
    }
  }

  def apply[T:Equalator]:Equalator[T] = implicitly[Equalator[T]]
}
