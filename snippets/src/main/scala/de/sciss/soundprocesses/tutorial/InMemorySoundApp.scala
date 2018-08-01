package de.sciss.soundprocesses.tutorial

// #inmemorysoundapp
import de.sciss.lucre.stm
import de.sciss.lucre.stm.WorkspaceHandle
import de.sciss.lucre.synth.InMemory
import de.sciss.synth.proc.{AuralSystem, Transport}

trait InMemorySoundApp {
  type S = InMemory
  implicit val cursor: stm.Cursor[S] = InMemory()
  implicit val ws: WorkspaceHandle[S] = WorkspaceHandle.Implicits.dummy
  val aural = AuralSystem()

  cursor.step { implicit tx =>
    aural.whenStarted { _ =>
      cursor.step { implicit tx =>
        val t = Transport[S](aural)
        run(t)
      }
    }
    aural.start()
  }

  def main(args: Array[String]): Unit = ()

  def run(t: Transport[S])(implicit tx: S#Tx): Unit
}
// #inmemorysoundapp
