package de.sciss.soundprocesses.tutorial

// #snippet2
import de.sciss.lucre.stm
import de.sciss.lucre.synth.InMemory
import de.sciss.synth._
import de.sciss.synth.proc.{Proc, Transport, Universe}
import de.sciss.synth.ugen._

object Snippet2 extends App {
  type S = InMemory
  implicit val cursor: stm.Cursor[S] = InMemory()

  val bubbles = SynthGraph {
    val o   = LFSaw.kr(Seq(8.0, 7.23)).mulAdd(3, 80)
    val f   = LFSaw.kr(0.4).mulAdd(24, o)
    val s   = SinOsc.ar(f.midiCps) * 0.04
    val c   = CombN.ar(s, 0.2, 0.2, 4)
    Out.ar(0, c)
  }

  cursor.step { implicit tx =>
    val p = Proc()
    p.graph() = bubbles

    val u = Universe.dummy[S]
    val t = Transport(u)
    t.addObject(p)
    t.play()

    u.auralSystem.start()
  }

  Thread.sleep(10000)
  sys.exit()
}
// #snippet2
