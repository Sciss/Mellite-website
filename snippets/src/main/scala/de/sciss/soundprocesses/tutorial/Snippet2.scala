package de.sciss.soundprocesses.tutorial

// #snippet2
import de.sciss.lucre.stm
import de.sciss.lucre.synth.InMemory
import de.sciss.synth._
import de.sciss.synth.proc.{AuralSystem, Proc, Transport, WorkspaceHandle}
import de.sciss.synth.ugen._

object Snippet2 extends App {
  type S = InMemory
  implicit val cursor: stm.Cursor[S] = InMemory()
  import WorkspaceHandle.Implicits._

  val aural = AuralSystem()

  val bubbles = SynthGraph {
    val o   = LFSaw.kr(Seq(8.0, 7.23)).madd(3, 80)
    val f   = LFSaw.kr(0.4).madd(24, o)
    val s   = SinOsc.ar(f.midicps) * 0.04
    val c   = CombN.ar(s, 0.2, 0.2, 4)
    Out.ar(0, c)
  }

  cursor.step { implicit tx =>
    val p = Proc[S]
    p.graph() = bubbles

    val t = Transport[S](aural)
    t.addObject(p)
    t.play()

    aural.start()
  }

  Thread.sleep(10000)
  sys.exit()
}
// #snippet2
