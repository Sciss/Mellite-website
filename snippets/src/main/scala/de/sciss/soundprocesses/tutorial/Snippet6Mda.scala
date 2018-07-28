package de.sciss.soundprocesses.tutorial

import de.sciss.lucre.expr.IntObj
import de.sciss.synth.SynthGraph
import de.sciss.synth.proc.{Grapheme, Proc, TimeRef, Transport}

// Variant of `Snippet6` that uses the `MdaPiano` UGen (requires that sc3-plugins is installed)
object Snippet6Mda extends InMemorySoundApp {
  def run(t: Transport[S])(implicit tx: S#Tx): Unit = {
    val piano = SynthGraph {
      import de.sciss.synth._
      import de.sciss.synth.proc.graph.Ops._
      import de.sciss.synth.ugen._

      val pitch = "pitch".ar
      val gate  = HPZ1.ar(pitch).signum.abs + Impulse.ar(0)
      pitch.poll(gate, "note")
      val sig   = MdaPiano.ar(freq = pitch.midiCps, gate = gate)
      Out.ar(0, sig)
    }

    val p = Proc[S]
    p.graph() = piano
    val pitches = Seq(
      ( 0,78), ( 1,81), ( 2,79), ( 3,78), ( 4,73), ( 5,71), ( 6,73), ( 7,74), ( 8,69),
      (11,66), (14,66), (17,66), (20,66),
      (24,78), (25,81), (26,79), (27,78), (28,73), (29,71), (30,73), (31,74), (32,69),
      (35,73), (38,78), (41,64),
      (50,69), (51,71), (52,72), (53,76), (54,74), (55,71), (56,74), (57,72), (58,71), (59,74),
      (64,74), (65,76), (66,77), (67,79), (68,81), (69,72), (70,74), (71,76), (72,74), (73,71), (74,74) // ...
    )

    val g = Grapheme[S]
    pitches.foreach { case (beat, pch) =>
      val time = (beat * 0.5 * TimeRef.SampleRate).toLong
      g.add(time, IntObj.newConst(pch))
    }
    p.attr.put("pitch", g)

    t.addObject(p)
    t.play()
  }
}