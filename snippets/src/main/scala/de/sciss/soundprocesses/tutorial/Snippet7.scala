package de.sciss.soundprocesses.tutorial

// #snippet7
import de.sciss.lucre.expr.DoubleVector
import de.sciss.span.Span
import de.sciss.synth.SynthGraph
import de.sciss.synth.proc.{Proc, TimeRef, Timeline, Transport}

object Snippet7 extends InMemorySoundApp {
  def run(t: Transport[S])(implicit tx: S#Tx): Unit = {
    val piano = SynthGraph {
      import de.sciss.synth._
      import de.sciss.synth.proc.graph.Ops._
      import de.sciss.synth.ugen._

      val pitch     = "pitch".ar
      val strike    = Impulse.ar(0) * 0.1 / NumChannels(pitch).sqrt
      val hammerEnv = Decay2.ar(strike, 0.008, 0.04)
      val sig       = Pan2.ar(
        Mix.tabulate(3) { i =>
          val detune    = Array(-0.05, 0, 0.04)(i)
          val delayTime = 1 / (pitch + detune).midiCps
          val hammer    = LFNoise2.ar(3000) * hammerEnv
          CombL.ar(hammer, delayTime, delayTime, 6)
        },
        (pitch - 36) / 27 - 1
      )
      Out.ar(0, Mix(sig))
    }

    val tl = Timeline[S]
    for (i <- 0 until 30) {
      val p       = Proc[S]
      p.graph()   = piano
      import de.sciss.numbers.Implicits._
      val pitches = Vector.fill(i/5 + 1)(math.random().linLin(0, 1, 49, 76) + i)
      p.attr.put("pitch", DoubleVector.newConst(pitches))
      val timeSec = i.linExp(0, 29, 7, 21)
      val time    = (timeSec * TimeRef.SampleRate).toLong
      val dur     = (      8 * TimeRef.SampleRate).toLong
      tl.add(Span(time, time + dur), p)
    }

    t.addObject(tl)
    t.seek(tl.firstEvent.getOrElse(0L))
    t.play()
  }
}
// #snippet7