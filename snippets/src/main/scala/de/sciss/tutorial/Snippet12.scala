package de.sciss.tutorial

// #snippet12
import de.sciss.lucre.expr.StringObj
import de.sciss.lucre.stm.Obj
import de.sciss.span.Span
import de.sciss.synth.proc.{ActionRaw, TimeRef, Timeline, Transport}

object Snippet12 extends InMemorySoundApp {
  def run(t: Transport[S])(implicit tx: S#Tx): Unit = {
    import de.sciss.synth.proc.MacroImplicits._

    val act1 = ActionRaw.apply[S] { universe =>
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

    val tl = Timeline[S]()
    act1.attr.put("foo", StringObj.newConst("first occurrence"))
    tl.add(Span.From((2 * TimeRef.SampleRate).toLong), act1)
    val act2: ActionRaw[S] = Obj.copy(act1)
    act2.attr.put("foo", StringObj.newConst("second occurrence"))
    tl.add(Span.From((8 * TimeRef.SampleRate).toLong), act2)
    t.addObject(tl)
    t.play()
  }
}
// #snippet12