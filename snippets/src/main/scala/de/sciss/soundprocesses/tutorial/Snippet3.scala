package de.sciss.soundprocesses.tutorial

// #snippet3
import de.sciss.lucre.expr.DoubleObj
import de.sciss.lucre.stm
import de.sciss.lucre.stm.WorkspaceHandle
import de.sciss.lucre.synth.InMemory
import de.sciss.synth._
import de.sciss.synth.proc.{AuralSystem, Proc, Transport}
import de.sciss.synth.ugen._

object Snippet3 extends App {
  type S = InMemory
  implicit val cursor: stm.Cursor[S] = InMemory()
  import WorkspaceHandle.Implicits._

  val aural = AuralSystem()

  val bubbles = SynthGraph {
    import de.sciss.synth.proc.graph.Ops._
    val f0  = "freq".kr
    val o   = LFSaw.kr(Seq(f0, f0 * 8/7.23)).mulAdd(3, 80)
    val f   = LFSaw.kr(0.4).mulAdd(24, o)
    val s   = SinOsc.ar(f.midiCps) * 0.04
    val c   = CombN.ar(s, 0.2, 0.2, 4)
    Out.ar(0, c)
  }

  val pH = cursor.step { implicit tx =>
    val p = Proc[S]
    p.graph() = bubbles
    p.attr.put("freq", DoubleObj.newConst(8.0))

    val t = Transport[S](aural)
    t.addObject(p)
    t.play()

    aural.start()
    tx.newHandle(p)
  }

  Thread.sleep(8000)

  cursor.step { implicit tx =>
    val p = pH()
    p.attr.put("freq", DoubleObj.newConst(0.1))
  }

  Thread.sleep(6000)

  sys.exit()
}
// #snippet3