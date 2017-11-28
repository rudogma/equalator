package org.rudogma.equalator

case class Error(path:List[Symbol], message:String, cause:Option[Throwable]) extends Exception(message, cause.getOrElse(null))

object Error {

  def apply
  (message:String, cause:Throwable = null)
  (implicit path:Path)
  :Error = new Error(path, s"${message}, at ${path.map(_.name).mkString(".")}", Option(cause))
}
