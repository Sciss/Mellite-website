package de.sciss.soundprocesses.tutorial

import de.sciss.lucre.stm
import de.sciss.lucre.synth.InMemory
import de.sciss.synth._
import de.sciss.synth.proc.{AuralSystem, Proc, Transport, Universe}
import de.sciss.synth.ugen._

trait Snippet2Parts {
  // #snippet2implicits
  type S = InMemory
  implicit val cursor: stm.Cursor[S] = InMemory()
  // #snippet2implicits

  val aural = AuralSystem()

  val bubbles = SynthGraph {
    val o   = LFSaw.kr(Seq(8.0, 7.23)).mulAdd(3, 80)
    val f   = LFSaw.kr(0.4).mulAdd(24, o)
    val s   = SinOsc.ar(f.midiCps) * 0.04
    val c   = CombN.ar(s, 0.2, 0.2, 4)
    Out.ar(0, c)
  }

  cursor.step { implicit tx =>
    // #snippet2proc
    val p = Proc()
    p.graph() = bubbles
    // #snippet2proc

    // #snippet2transport
    val u = Universe.dummy[S]
    val t = Transport(u)
    t.addObject(p)
    t.play()
    // #snippet2transport

    u.auralSystem.start()
  }

  Thread.sleep(10000)

  sys.exit()
}