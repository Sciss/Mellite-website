package de.sciss.tutorial

import de.sciss.lucre.Folder
import de.sciss.proc._
import de.sciss.span.Span
import de.sciss.synth._
import de.sciss.synth.proc.graph._
import de.sciss.synth.ugen._

trait Snippet9Parts extends InMemorySoundApp {
  def run(t: Transport[T])(implicit tx: T): Unit = {
    // #snippet9sec
    implicit class Seconds(d: Double) {
      def seconds: Long = (d * TimeRef.SampleRate).toLong
    }
    // #snippet9sec

    // #snippet9tl
    val tlProc  = Timeline[T]()
    val fOut    = Folder[T]()
    for (i <- 0 to 15) {
      val pOsc  = Proc[T]()
      // ...
      // #snippet9tl
      val dur   = math.random().linLin(0, 1, 10, 20)
      val off   = math.random().linLin(0, 1,  0, 20) + i.linLin(0, 15, 0, 60)
      val oOsc  = pOsc.outputs.add("out")
      val span  = Span(off.seconds, (off + dur).seconds)
      // #snippet9add
      tlProc.add(span, pOsc)
      fOut  .addLast  (oOsc)
    }
    // #snippet9add

    val gVerb = SynthGraph {
      val in  = ScanInFix(2)
      val y   = in * 0.6
      val sig = in + Mix.fill(2)(CombL.ar(y, 0.06, LFNoise1.kr(Rand(0, 0.3)).mulAdd(0.025, 0.035), 1))
      Out.ar(0, sig)
    }
    // #snippet9in
    val pVerb = Proc[T]()
    pVerb.attr.put("in", fOut)
    // #snippet9in
    pVerb.graph() = gVerb

    t.addObject(tlProc)
    t.addObject(pVerb)
    t.seek(tlProc.firstEvent.getOrElse(0L))
    t.play()
  }
}