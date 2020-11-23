package de.sciss.tutorial

// #inmemorysoundapp
import de.sciss.lucre.synth.InMemory
import de.sciss.proc.{Transport, Universe}

trait InMemorySoundApp {
  type T = InMemory.Txn
  implicit val cursor = InMemory()

  cursor.step { implicit tx =>
    val u = Universe.dummy[T]
    u.auralSystem.whenStarted { _ =>
      cursor.step { implicit tx =>
        val t = Transport[T](u)
        run(t)
      }
    }
    u.auralSystem.start()
  }

  def main(args: Array[String]): Unit = ()

  def run(t: Transport[T])(implicit tx: T): Unit
}
// #inmemorysoundapp
