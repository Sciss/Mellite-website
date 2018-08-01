package de.sciss.soundprocesses.tutorial

import de.sciss.lucre.expr.DoubleObj
import de.sciss.lucre.stm
import de.sciss.lucre.stm.WorkspaceHandle
import de.sciss.lucre.synth.InMemory
import de.sciss.synth._
import de.sciss.synth.proc.{AuralSystem, Proc, Transport}

trait Snippet3Parts {
  type S = InMemory
  implicit val cursor: stm.Cursor[S] = InMemory()
  import WorkspaceHandle.Implicits._

  val aural = AuralSystem()

  val bubbles = SynthGraph {
    // #snippet3stringcontrol
    import de.sciss.synth.proc.graph.Ops._
    val f0  = "freq".kr
    // #snippet3stringcontrol
    f0.poll
  }

  cursor.step { implicit tx =>
    val p = Proc[S]
    // #snippet3attrput
    p.attr.put("freq", DoubleObj.newConst(8.0))
    // #snippet3attrput
    // #snippet3transport
    val t = Transport[S](aural)
    t.addObject(p)
    t.play()
    // #snippet3transport
  }

  // #snippet3txhandle
  val pH = cursor.step { implicit tx =>
    val p = Proc[S]
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