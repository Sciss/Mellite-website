package de.sciss.tutorial

import de.sciss.fscape.Graph

trait FScapeRecipes {
  import de.sciss.fscape.graph._

  def plain(): Unit = {
    // #fsc-recipe1-plain
    val size  = 4
    val in    = 0 until size
    val pairs = for (a <- in; b <- in if a < b) yield (a, b)
    // #fsc-recipe1-plain
  }

  Graph {
    // #fsc-recipe1
    val size  = 4
    def in    = ArithmSeq(length = size)
    val a     = RepeatWindow(in, 1   , size)
    val b     = RepeatWindow(in, size, size)
    val guard = a < b
    RunningSum(guard).last.poll("num-pairs")
    val aF    = FilterSeq(a, guard)
    val bF    = FilterSeq(b, guard)
    aF.poll(DC(1), "A")
    bF.poll(DC(1), "B")
    // #fsc-recipe1

    // (0,1),  (0,2),  (0,3),  (1,2),  (1,3),  (2,3)
  }
}
