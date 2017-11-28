
[![Build status](https://img.shields.io/travis/rudogma/equalator/master.svg)](https://travis-ci.org/rudogma/equalator)
[![Maven Central](https://img.shields.io/maven-central/v/org.rudogma/equalator_2.12.svg)](https://maven-badges.herokuapp.com/maven-central/org.rudogma/equalator_2.12)


Deep equals. Micro library. Type safe, compile time comparison chain. Based on shapeless.LabelledGeneric


## sbt

Scala: 2.11.11, 2.12.2+
```scala
libraryDependencies += "org.rudogma" %% "equalator" % "1.1"
```

ScalaJS (compiled with 0.6.21)
```scala
libraryDependencies += "org.rudogma" %%% "equalator" % "1.1"
```

## usage

```scala
import org.rudogma.equalator._
import org.rudogma.equalator.Implicits._


case class Test(field0:Int, field1:String, field2:Array[Int])

val v1 = Test(1, "2", Array(3,4))
val v2 = Test(1, "2", Array(3,5))


Equalator[Test].deepEquals(v1, v2) // will be Left(Error(... org.rudogma.equalator.Error: Left != Right, at $root.field2.[1] ...))


```