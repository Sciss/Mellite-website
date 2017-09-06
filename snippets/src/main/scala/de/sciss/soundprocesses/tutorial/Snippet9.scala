package de.sciss.soundprocesses.tutorial

// #snippet9
import de.sciss.lucre.expr.IntObj
import de.sciss.span.Span
import de.sciss.synth._
import de.sciss.synth.proc._
import de.sciss.synth.proc.graph.Ops._
import de.sciss.synth.proc.graph._
import de.sciss.synth.ugen._

object Snippet9 extends InMemorySoundApp {
  def run(t: Transport[S])(implicit tx: S#Tx): Unit = {
    implicit class Seconds(d: Double) {
      def seconds: Long = (d * TimeRef.SampleRate).toLong
    }

    val gOsc = SynthGraph {
      val p0 = "pitch".kr(80f)
      val freq = SinOsc.kr(4).madd(1, p0).max(
        Decay.ar(LFPulse.ar(0.1, 0, 0.05) * Impulse.ar(8) * 500, 2)
      )
      val width = LFNoise1.kr(0.157).madd(0.4, 0.5)
      val pulse = Pulse.ar(freq, width) * 0.04
      val res   = RLPF.ar(pulse, LFNoise1.kr(0.2).madd(2000, 2400), 0.2)
      val fd    = FadeOut.ar
      val sig   = Pan2.ar(res * fd, LFNoise1.kr(0.1))
      ScanOut(sig)
    }

    val tlProc  = Timeline[S]
    val tlOut   = Timeline[S]
    for (i <- 0 to 15) {
      val pOsc = Proc[S]
      pOsc.graph() = gOsc
      val pitch = i.linlin(0, 15, 60, 120).toInt
      pOsc.attr.put("pitch", IntObj.newConst(pitch))
      val dur = math.random().linlin(0, 1, 10, 20)
      val off = math.random().linlin(0, 1,  0, 20) + i.linlin(0, 15, 0, 60)
      pOsc.attr.put("fade-out", FadeSpec.Obj.newConst(FadeSpec((dur/2).seconds)))
      val oOsc = pOsc.outputs.add("out")
      val span = Span(off.seconds, (off + dur).seconds)
      tlProc.add(span, pOsc)
      tlOut .add(span, oOsc)
    }

    val gVerb = SynthGraph {
      val in  = ScanIn()
      val y   = in * 0.6
      val sig = in + Mix.fill(2)(CombL.ar(y, 0.06, LFNoise1.kr(Rand(0, 0.3)).madd(0.025, 0.035), 1))
      Out.ar(0, sig)
    }
    val pVerb = Proc[S]
    pVerb.graph() = gVerb
    pVerb.attr.put("in", tlOut)

    t.addObject(tlProc)
    t.addObject(pVerb)
    t.seek(tlProc.firstEvent.getOrElse(0L))
    t.play()
  }
}
// #snippet9