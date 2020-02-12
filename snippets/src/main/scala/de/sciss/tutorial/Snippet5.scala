package de.sciss.tutorial

// #snippet5
import de.sciss.synth.proc.Transport

object Snippet5 extends InMemorySoundApp {
  def run(t: Transport[S])(implicit tx: S#Tx): Unit = {
    println("Ok, we've booted.")
    sys.exit()  // well, this is just an example, so quit
  }
}
// #snippet5
