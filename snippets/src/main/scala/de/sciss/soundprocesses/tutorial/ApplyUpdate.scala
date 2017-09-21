package de.sciss.soundprocesses.tutorial

trait ApplyUpdate {
  // #cell
  trait Cell[A] {  // fictitious type
    def apply(): A
    def update(v: A): Unit
  }
  // #cell

  // #cell-use
  val cell: Cell[Int] = ???   // imagine we had such cell
  val oldValue = cell()       // aka cell.apply()
  val newValue = oldValue + 1
  cell() = newValue           // aka cell.update(newValue)
  // #cell-use

  // #array
  val xs = Array(3, 5, 8, 0)
  xs(3) = xs(1) + xs(2)  // aka xs.update(3, xs.apply(1) + xs.apply(2))
  assert(xs(3) == 13)
  // #array

  // #var
  trait Var[Tx, A] {
    def apply()(implicit tx: Tx): A
    def update(v: A)(implicit tx: Tx): Unit
  }
  // #var
}