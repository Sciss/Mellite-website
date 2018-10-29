package de.sciss.soundprocesses.tutorial

// #snippet13
import de.sciss.lucre.expr.{BooleanObj, IntObj}
import de.sciss.lucre.stm.Folder
import de.sciss.synth.SynthGraph
import de.sciss.synth.proc._

object Snippet13 extends InMemorySoundApp {
  def run(t: Transport[S])(implicit tx: S#Tx): Unit = {
    import de.sciss.synth.proc.MacroImplicits._

    val f     = Folder[S]
    val p     = Proc[S]()
    val g     = SynthGraph {
      import de.sciss.synth._
      import de.sciss.synth.ugen._
      import de.sciss.synth.proc.graph._

      val pitchLo   = 50
      val pitchHi   = 75
      val maxPeriod = pitchLo.midiCps.reciprocal
      val numVoices = 4
      val trigAll   = Impulse.kr(3)
      val trigCnt   = PulseCount.kr(trigAll) - 1
      val trig4     = Gate.kr(trigAll, trigCnt < 4)
      val pitch     = TIRand.kr(lo = pitchLo, hi = pitchHi, trig = trig4)
      val out = Mix.tabulate(numVoices) { i =>
        val trigVc  = Trig.kr(trigCnt sig_== i, dur = 0.01f)
        val pluck   = PinkNoise.ar(Decay.kr(trigVc, 0.05))
        val period  = Latch.kr(pitch, trigVc).midiCps.reciprocal
        val string  = CombL.ar(pluck, delayTime = period, maxDelayTime = maxPeriod, decayTime = 8)
        Pan2.ar(string, i.linLin(0, numVoices - 1, -0.75, 0.75))
      }
      val sig = LeakDC.ar(out)
      Out.ar(0, sig)
      val done = SetResetFF.kr(DetectSilence.ar(sig, amp = -60.dbAmp), 0)
      Reaction(done, pitch, "done")
    }
    p.graph() = g
    f.addLast(p)

    val play  = BooleanObj.newVar[S](true)
    val count = IntObj.newVar[S](4)
    val ens   = Ensemble[S](f, 0L, play)

    val act   = Action.apply[S] { universe =>
      println(s"Action - last midi pitch was ${universe.value}")
      val attr = universe.self.attr
      for {
        BooleanObj.Var(ply) <- attr.$[BooleanObj]("play" )
        IntObj    .Var(cnt) <- attr.$[IntObj    ]("count")
      } {
        val i = cnt.value - 1
        cnt() = i
        ply() = false
        if (i > 0) {
          println(s"Counter is $i - restarting.")
          ply() = true
        } else {
          println("Counter reached zero.")
        }
      }
    }

    act.attr.put("play" , play )
    act.attr.put("count", count)

    p.attr.put("done", act)

    t.addObject(ens)
    t.play()
  }
}
// #snippet13