package de.sciss.soundprocesses.tutorial

// #inmemorysoundapp
import de.sciss.lucre.synth.InMemory
import de.sciss.synth.proc.{Transport, Universe}

trait InMemorySoundApp {
  type S = InMemory
  implicit val cursor: S = InMemory()

  cursor.step { implicit tx =>
    val u = Universe.dummy[S]
    u.auralSystem.whenStarted { _ =>
      cursor.step { implicit tx =>
        val t = Transport[S](u)
        run(t)
      }
    }
    u.auralSystem.start()
  }

  def main(args: Array[String]): Unit = ()

  def run(t: Transport[S])(implicit tx: S#Tx): Unit
}
// #inmemorysoundapp
