package de.sciss.tutorial

import de.sciss.lucre.expr.DoubleVector
import de.sciss.span.Span
import de.sciss.synth.SynthGraph
import de.sciss.synth.proc.{Proc, TimeRef, Timeline, Transport}

trait Snippet7Parts extends InMemorySoundApp {
  def run(t: Transport[S])(implicit tx: S#Tx): Unit = {
    val piano = SynthGraph {
      import de.sciss.synth._
      import de.sciss.synth.proc.graph.Ops._
      import de.sciss.synth.ugen._

      // #snippet7graph
      val pitch     = "pitch".ar
      val strike    = Impulse.ar(0) * 0.1 / NumChannels(pitch).sqrt
      // #snippet7graph
      strike
    }

    val tl = Timeline[S]()
    for (i <- 0 until 30) {
      val p       = Proc[S]()
      p.graph()   = piano
      // #snippet7vec
      import de.sciss.numbers.Implicits._
      val pitches = Vector.fill(i/5 + 1)(math.random().linLin(0, 1, 49, 76) + i)
      p.attr.put("pitch", DoubleVector.newConst(pitches))
      // #snippet7vec
      // #snippet7span
      val timeSec = i.linExp(0, 29, 7, 21)
      val time    = (timeSec * TimeRef.SampleRate).toLong
      val dur     = (      8 * TimeRef.SampleRate).toLong
      tl.add(Span(time, time + dur), p)
      // #snippet7span
    }

    // #snippet7seek
    t.addObject(tl)
    t.seek(tl.firstEvent.getOrElse(0L))
    t.play()
    // #snippet7seek
  }
}