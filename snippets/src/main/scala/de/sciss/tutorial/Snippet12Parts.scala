package de.sciss.tutorial

import de.sciss.proc.Transport

trait Snippet12Parts extends InMemorySoundApp {
  ??? // No longer works
  def run(t: Transport[T])(implicit tx: T): Unit = ???

//  def run(t: Transport[S])(implicit tx: S#Tx): Unit = {
//    import de.sciss.synth.proc.MacroImplicits._
//
//    ActionRaw.apply[S] { universe =>
//      // #snippet12self
//      for (fooObj <- universe.self.attr.$[StringObj]("foo")) {
//        val v = fooObj.value
//        println(v)
//        if (v.contains("second")) {
//          println("Quitting...")
//          sys.exit()
//        }
//      }
//      // #snippet12self
//    }
//
//    ActionRaw.apply[S] { universe =>
//      // #snippet12nodollar
//      universe.self.attr.get("foo").foreach {
//        case fooObj: StringObj[S] =>
//          val v = fooObj.value
//          println(v)
//        case _ => println("Oh oh, no string object?")
//      }
//      // #snippet12nodollar
//    }
//
//    ActionRaw.apply[S] { universe =>
//      // #snippet12multiple
//      val attr = universe.self.attr
//      for {
//        nameObj <- attr.$[StringObj ]("name")
//        muteObj <- attr.$[BooleanObj]("mute")
//      } {
//        val name  = nameObj.value
//        val muted = muteObj.value
//        println(s"Action! Name is $name, muted state is $muted")
//      }
//      // #snippet12multiple
//    }
//  }
}