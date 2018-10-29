package de.sciss.soundprocesses.tutorial

// #snippet8
import de.sciss.synth._
import de.sciss.synth.proc.graph._
import de.sciss.synth.proc.{Proc, Transport}
import de.sciss.synth.ugen._

object Snippet8 extends InMemorySoundApp {
  def run(t: Transport[S])(implicit tx: S#Tx): Unit = {
    val gOsc = SynthGraph {
      val freq = SinOsc.kr(4).mulAdd(1, 80).max(
        Decay.ar(LFPulse.ar(0.1, 0, 0.05) * Impulse.ar(8) * 500, 2)
      )
      val width = LFNoise1.kr(0.157).mulAdd(0.4, 0.5)
      val pulse = Pulse.ar(freq, width) * 0.04
      ScanOut(pulse)
    }
    val pOsc = Proc[S]()
    pOsc.graph() = gOsc
    val outOsc = pOsc.outputs.add("out")

    val gRes = SynthGraph {
      val in = ScanIn()
      val res = RLPF.ar(in, LFNoise1.kr(0.2).mulAdd(2000, 2400), 0.2)
      ScanOut(res)
    }
    val pRes = Proc[S]()
    pRes.graph() = gRes
    pRes.attr.put("in", outOsc)
    val outRes = pRes.outputs.add("out")

    val gVerb = SynthGraph {
      val in  = ScanIn()
      val y   = in * 0.6
      val sig = in + Seq(
        Mix.fill(2)(CombL.ar(y, 0.06, LFNoise1.kr(Rand(0, 0.3)).mulAdd(0.025, 0.035), 1)),
        Mix.fill(2)(CombL.ar(y, 0.06, LFNoise1.kr(Rand(0, 0.3)).mulAdd(0.025, 0.035), 1))
      )
      Out.ar(0, sig)
    }
    val pVerb = Proc[S]()
    pVerb.graph() = gVerb
    pVerb.attr.put("in", outRes)

    t.addObject(pOsc)
    t.addObject(pRes)
    t.addObject(pVerb)
    t.play()
  }
}
// #snippet8