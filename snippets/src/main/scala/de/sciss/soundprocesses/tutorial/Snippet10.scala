package de.sciss.soundprocesses.tutorial

// #snippet10
import de.sciss.lucre.expr.StringObj
import de.sciss.lucre.stm.Sys
import de.sciss.span.Span
import de.sciss.synth.proc.{Action, TimeRef, Timeline, Transport}

object Snippet10 extends InMemorySoundApp {
  def run(t: Transport[S])(implicit tx: S#Tx): Unit = {
    val body = new Action.Body {
      def apply[T <: Sys[T]](universe: Action.Universe[T])(implicit tx: T#Tx): Unit = {
        println("Any kind of tentacles will do:")
        for (fooObj <- universe.self.attr.$[StringObj]("foo")) {
          val v = fooObj.value
          println(v)
          if (v.contains("second")) {
            println("Quitting...")
            sys.exit()
          }
        }
      }
    }

    Action.registerPredef("my-action", body)

    val tl    = Timeline[S]
    val act1  = Action.predef[S]("my-action")
    act1.attr.put("foo", StringObj.newConst("first occurrence"))
    tl.add(Span.From((2 * TimeRef.SampleRate).toLong), act1)
    val act2  = Action.predef[S]("my-action")
    act2.attr.put("foo", StringObj.newConst("second occurrence"))
    tl.add(Span.From((8 * TimeRef.SampleRate).toLong), act2)
    t.addObject(tl)
    t.play()
  }
}
// #snippet10