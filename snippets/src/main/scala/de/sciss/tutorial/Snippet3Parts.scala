package de.sciss.tutorial

import de.sciss.lucre.expr.DoubleObj
import de.sciss.lucre.stm
import de.sciss.lucre.synth.InMemory
import de.sciss.synth._
import de.sciss.synth.proc.{Proc, Transport, Universe}

trait Snippet3Parts {
  type S = InMemory
  implicit val cursor: stm.Cursor[S] = InMemory()

  val bubbles = SynthGraph {
    // #snippet3stringcontrol
    import de.sciss.synth.proc.graph.Ops._
    val f0  = "freq".kr
    // #snippet3stringcontrol
    f0.poll
  }

  cursor.step { implicit tx =>
    val p = Proc()
    // #snippet3attrput
    p.attr.put("freq", DoubleObj.newConst(8.0))
    // #snippet3attrput
    // #snippet3transport
    val u = Universe.dummy[S]
    val t = Transport(u)
    t.addObject(p)
    t.play()
    // #snippet3transport
  }

  // #snippet3txhandle
  val pH = cursor.step { implicit tx =>
    val p = Proc()
    // ...
    tx.newHandle(p)
  }

  // ...

  cursor.step { implicit tx =>
    val p = pH()
    p.attr.put("freq", DoubleObj.newConst(0.1))
  }

  // #snippet3txhandle
}