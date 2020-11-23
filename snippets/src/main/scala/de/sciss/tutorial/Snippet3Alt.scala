package de.sciss.tutorial

import de.sciss.lucre.synth.InMemory
import de.sciss.lucre.{Cursor, DoubleObj}
import de.sciss.proc.Proc

trait Snippet3Alt {
  type T = InMemory.Txn
  implicit val cursor: Cursor[T] = InMemory()

  // #snippet3noprochandle
  val p = cursor.step { implicit tx =>
    val p0 = Proc[T]()
    // ...
    p0 // this is the functions return value and thus becomes the outer `p`
  }

  // ...

  cursor.step { implicit tx =>
    p.attr.put("freq", DoubleObj.newConst(0.1))
  }
  // #snippet3noprochandle
}
