package de.sciss.soundprocesses.tutorial

// #snippet6
import de.sciss.lucre.expr.IntObj
import de.sciss.lucre.synth.InMemory
import de.sciss.synth.SynthGraph
import de.sciss.synth.proc.{Grapheme, Proc, TimeRef, Transport}

object Snippet6 extends InMemorySoundApp {
  def run(t: Transport[S])(implicit tx: InMemory.Txn): Unit = {
    val piano = SynthGraph {
      import de.sciss.synth._
      import de.sciss.synth.proc.graph.Ops._
      import de.sciss.synth.ugen._

      val pitch     = "pitch".ar
      val strike    = (pitch sig_!= Delay2.ar(pitch)) * 0.05
      pitch.poll(strike, "note")
      val hammerEnv = Decay2.ar(strike, 0.008, 0.04)    // excitation envelope
      val sig       = Pan2.ar(
        // array of 3 strings
        Mix.tabulate(3) { i =>
          // detune strings, calculate delay time :
          val detune = Array(-0.05, 0, 0.04)(i)
          val delayTime = 1 / (pitch + detune).midicps
          // each string gets own exciter :
          val hammer = LFNoise2.ar(3000) * hammerEnv   // 3000 Hz was chosen by ear
          CombL.ar(hammer, // used as a string resonator
            delayTime,     // max delay time
            delayTime,     // actual delay time
            6              // decay time of string
          )
        },
        (pitch - 36) / 27 - 1    // pan position: lo notes left, hi notes right
      )
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
      val time = (beat * 0.511 * TimeRef.SampleRate).toLong
      g.add(time, IntObj.newConst(pch))
    }
    p.attr.put("pitch", g)

    t.addObject(p)
    t.play()
  }
}
// #snippet6