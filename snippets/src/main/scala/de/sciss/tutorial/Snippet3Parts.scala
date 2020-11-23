package de.sciss.tutorial

import de.sciss.lucre.synth.InMemory
import de.sciss.lucre.{Cursor, DoubleObj}
import de.sciss.proc.{Proc, Transport, Universe}
import de.sciss.synth._

trait Snippet3Parts {
  type T = InMemory.Txn
  implicit val cursor: Cursor[T] = InMemory()

  val bubbles = SynthGraph {
    // #snippet3stringcontrol
    import de.sciss.synth.proc.graph.Ops._
    val f0  = "freq".kr
    // #snippet3stringcontrol
    f0.poll
  }

  cursor.step { implicit tx =>
    val p = Proc[T]()
    // #snippet3attrput
    p.attr.put("freq", DoubleObj.newConst(8.0))
    // #snippet3attrput
    // #snippet3transport
    val u = Universe.dummy[T]
    val t = Transport(u)
    t.addObject(p)
    t.play()
    // #snippet3transport
  }

  // #snippet3txhandle
  val pH = cursor.step { implicit tx =>
    val p = Proc[T]()
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