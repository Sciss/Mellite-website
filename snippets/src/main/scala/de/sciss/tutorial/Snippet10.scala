package de.sciss.tutorial

// #snippet10
import de.sciss.proc.Transport

object Snippet10 extends InMemorySoundApp {
  ??? // no longer works
  def run(t: Transport[T])(implicit tx: T): Unit = {
//    val body = new ActionRaw.Body {
//      def apply[T <: Sys[T]](universe: ActionRaw.Universe[T])(implicit tx: T): Unit = {
//        println("Any kind of tentacles will do:")
//        for (fooObj <- universe.self.attr.$[StringObj]("foo")) {
//          val v = fooObj.value
//          println(v)
//          if (v.contains("second")) {
//            println("Quitting...")
//            sys.exit()
//          }
//        }
//      }
    }
//
//    ActionRaw.registerPredef("my-action", body)
//
//    val tl    = Timeline[S]()
//    val act1  = ActionRaw.predef[S]("my-action")
//    act1.attr.put("foo", StringObj.newConst("first occurrence"))
//    tl.add(Span.From((2 * TimeRef.SampleRate).toLong), act1)
//    val act2  = ActionRaw.predef[S]("my-action")
//    act2.attr.put("foo", StringObj.newConst("second occurrence"))
//    tl.add(Span.From((8 * TimeRef.SampleRate).toLong), act2)
//    t.addObject(tl)
//    t.play()
//  }
}
// #snippet10