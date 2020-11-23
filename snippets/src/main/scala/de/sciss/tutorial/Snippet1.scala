package de.sciss.tutorial

// #snippet1
import de.sciss.lucre.synth.{InMemory, RT, Server, Synth}
import de.sciss.proc.AuralSystem
import de.sciss.synth.SynthGraph

object Snippet1 extends App {
  val cursor  = InMemory()
  val aural   = AuralSystem()

  val bubbles = SynthGraph {
    import de.sciss.synth._
    import ugen._
    val o = LFSaw.kr(Seq(8, 7.23)).mulAdd(3, 80)
    val f = LFSaw.kr(0.4).mulAdd(24, o)
    val s = SinOsc.ar(f.midiCps) * 0.04
    val c = CombN.ar(s, 0.2, 0.2, 4)
    val l = Line.kr(1, 0, 10, doneAction = freeSelf)
    Out.ar(0, c * l)
  }

  cursor.step { implicit tx =>
    aural.addClient(new AuralSystem.Client {
      def auralStarted(s: Server)(implicit tx: RT): Unit = {
        val syn = Synth.play(bubbles)(s)
        syn.onEnd(sys.exit())
      }

      def auralStopped()(implicit tx: RT): Unit = ()
    })

    aural.start()
  }
}
// #snippet1