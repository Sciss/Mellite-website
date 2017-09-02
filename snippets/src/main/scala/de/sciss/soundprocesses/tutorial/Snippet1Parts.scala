package de.sciss.soundprocesses.tutorial

import de.sciss.lucre.synth.{InMemory, Server, Synth, Txn}
import de.sciss.synth.SynthGraph
import de.sciss.synth.proc.AuralSystem

trait Snippet1Parts {
  // #snippet1systems
  val cursor  = InMemory()
  val aural   = AuralSystem()
  // #snippet1systems

  // #snippet1graph
  val bubbles = SynthGraph {
    import de.sciss.synth._
    import ugen._
    val o = LFSaw.kr(Seq(8, 7.23)).madd(3, 80)
    val f = LFSaw.kr(0.4).madd(24, o)
    val s = SinOsc.ar(f.midicps) * 0.04
    val c = CombN.ar(s, 0.2, 0.2, 4)
    val l = Line.kr(1, 0, 10, doneAction = freeSelf)
    Out.ar(0, c * l)
  }
  // #snippet1graph

  // #snippet1txn
  cursor.step { implicit tx =>
    aural.addClient(new AuralSystem.Client {
      def auralStarted(s: Server)(implicit tx: Txn): Unit = {
        val syn = Synth.play(bubbles)(s)
        syn.onEnd(sys.exit())
      }

      def auralStopped()(implicit tx: Txn): Unit = ()
    })

    aural.start()
  }
  // #snippet1txn

  cursor.step { implicit tx =>
    // #snippet1config
    val config = Server.Config()
    config.outputBusChannels = 4
    aural.start(config)
    // #snippet1config
  }

  // #snippet1explicit
  cursor.step { tx =>
    aural.start()(tx)
  }
  // #snippet1explicit

  new AuralSystem.Client {
    // #snippet1started
    def auralStarted(s: Server)(implicit tx: Txn): Unit = {
      val syn = Synth.play(bubbles)(s)
      syn.onEnd(sys.exit())
    }
    // #snippet1started

    def auralStopped()(implicit tx: Txn): Unit = ()
  }
}