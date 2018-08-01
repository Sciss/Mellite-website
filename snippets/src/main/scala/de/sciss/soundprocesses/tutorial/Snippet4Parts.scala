package de.sciss.soundprocesses.tutorial

import de.sciss.lucre.expr.DoubleObj
import de.sciss.lucre.stm
import de.sciss.lucre.stm.WorkspaceHandle
import de.sciss.lucre.synth.InMemory
import de.sciss.synth._
import de.sciss.synth.proc.{AuralSystem, Proc, Scheduler, TimeRef, Transport}
import de.sciss.synth.ugen._

object Snippet4Parts extends App {
  type S = InMemory
  implicit val cursor: stm.Cursor[S] = InMemory()
  import WorkspaceHandle.Implicits._

  val aural = AuralSystem()

  val bubbles = SynthGraph {
    import de.sciss.synth.proc.graph.Ops._
    // #snippet4pitchctl
    val p0  = "pitch".kr(80.0)
    val o   = LFSaw.kr(Seq(1.0, 7.23/8)).mulAdd(3, p0)
    // #snippet4pitchctl
    val f   = LFSaw.kr(0.4).mulAdd(4, o)
    val s   = SinOsc.ar(f.midiCps) * 0.04
    val c   = CombN.ar(s, 0.2, 0.2, 2)
    Out.ar(0, c)
  }

  // #snippet4modclass
  class PitchMod(sch: Scheduler[S], pchH: stm.Source[S#Tx, DoubleObj.Var[S]]) {
    def iterate()(implicit tx: S#Tx): Unit = {
      val pch = pchH()
      pch() = math.random() * 40 + 60
      sch.schedule(sch.time + TimeRef.SampleRate.toLong) { implicit tx =>
        iterate()
      }
    }
  }
  // #snippet4modclass

  cursor.step { implicit tx =>
    val p = Proc[S]
    p.graph() = bubbles
    // #snippet4pitchvar
    val pch = DoubleObj.newVar[S](0.0)
    p.attr.put("pitch", pch)
    // #snippet4pitchvar

    val t   = Transport[S](aural)
    // #snippet4createmod
    val sch = t.scheduler
    val mod = new PitchMod(sch, tx.newHandle(pch))
    mod.iterate()
    // #snippet4createmod
    t.addObject(p)
    t.play()

    aural.start()
  }
}