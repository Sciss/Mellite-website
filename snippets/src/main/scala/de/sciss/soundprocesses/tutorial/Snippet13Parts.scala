package de.sciss.soundprocesses.tutorial

import de.sciss.lucre.expr.{BooleanObj, IntObj}
import de.sciss.synth.SynthGraph
import de.sciss.synth.proc._

trait Snippet13Parts extends InMemorySoundApp {
  def run(t: Transport[S])(implicit tx: S#Tx): Unit = {
    import de.sciss.synth.proc.MacroImplicits._

    // #snippet13scaffold
    val f     = Folder[S]
    val p     = Proc[S]
    val g     = SynthGraph { }
    p.graph() = g
    f.addLast(p)

    val play  = BooleanObj.newVar[S](true)
    val count = IntObj.newVar[S](4)
    val ens   = Ensemble[S](f, 0L, play)

    val act   = Action.apply[S] { universe => }
    act.attr.put("play" , play )
    act.attr.put("count", count)

    p.attr.put("done", act)

    t.addObject(ens)
    t.play()
    // #snippet13scaffold

    SynthGraph {
      import de.sciss.synth._
      import de.sciss.synth.ugen._
      import de.sciss.synth.proc.graph._
      val sig   : GE = ???
      val pitch : GE = ???
      // #snippet13reaction
      Out.ar(0, sig)
      val done = SetResetFF.kr(DetectSilence.ar(sig, amp = -60.dbAmp), 0)
      Reaction(done, pitch, "done")
      // #snippet13reaction
    }

    Action.apply[S] { universe =>
      // #snippet13value
      val Action.FloatVector(Seq(pitch)) = universe.value
      println(s"Action - last midi pitch was $pitch")
      // #snippet13value

      // #snippet13body
      val attr = universe.self.attr
      for {
        BooleanObj.Var(ply) <- attr.$[BooleanObj]("play" )
        IntObj    .Var(cnt) <- attr.$[IntObj    ]("count")
      } {
        val i = cnt.value - 1
        cnt() = i
        ply() = false
        if (i > 0) {
          println(s"Counter is $i - restarting.")
          ply() = true
        } else {
          println("Counter reached zero.")
        }
      }
      // #snippet13body
    }
  }
}