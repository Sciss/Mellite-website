package de.sciss.tutorial

// #snippet4
import de.sciss.lucre.expr.DoubleObj
import de.sciss.lucre.stm
import de.sciss.lucre.synth.InMemory
import de.sciss.synth._
import de.sciss.synth.proc.{Proc, Scheduler, TimeRef, Transport, Universe}
import de.sciss.synth.ugen._

object Snippet4 extends App {
  type S = InMemory
  implicit val cursor: stm.Cursor[S] = InMemory()

  val bubbles = SynthGraph {
    import de.sciss.synth.proc.graph.Ops._
    val p0  = "pitch".kr(80.0)
    val o   = LFSaw.kr(Seq(1.0, 7.23/8)).mulAdd(3, p0)
    val f   = LFSaw.kr(0.4).mulAdd(4, o)
    val s   = SinOsc.ar(f.midiCps) * 0.04
    val c   = CombN.ar(s, 0.2, 0.2, 2)
    Out.ar(0, c)
  }

  class PitchMod(sch: Scheduler[S], pchH: stm.Source[S#Tx, DoubleObj.Var[S]]) {
    def iterate()(implicit tx: S#Tx): Unit = {
      val pch = pchH()
      pch() = math.random() * 40 + 60
      sch.schedule(sch.time + TimeRef.SampleRate.toLong) { implicit tx =>
        iterate()
      }
    }
  }

  cursor.step { implicit tx =>
    val p = Proc()
    p.graph() = bubbles
    val pch = DoubleObj.newVar(0.0)
    p.attr.put("pitch", pch)

    val u   = Universe.dummy[S]
    val t   = Transport(u)
    val sch = t.scheduler
    val mod = new PitchMod(sch, tx.newHandle(pch))
    mod.iterate()
    t.addObject(p)
    t.play()

    u.auralSystem.start()
  }
}
// #snippet4