package de.sciss.tutorial

// #snippet11
import de.sciss.lucre.expr.StringObj
import de.sciss.lucre.stm.Obj
import de.sciss.span.Span
import de.sciss.synth.proc.SoundProcesses.executionContext
import de.sciss.synth.proc.{ActionRaw, Code, Compiler, TimeRef, Timeline, Transport}

object Snippet11 extends InMemorySoundApp {
  implicit val compiler: Code.Compiler = Compiler()

  def run(t: Transport[S])(implicit tx: S#Tx): Unit = {
    val code = Code.ActionRaw(
      """println("Any kind of tentacles will do:")
        |for (fooObj <- universe.self.attr.$[StringObj]("foo")) {
        |  val v = fooObj.value
        |  println(v)
        |  if (v.contains("second")) {
        |    println("Quitting...")
        |    sys.exit()
        |  }
        |}
        |""".stripMargin)

    println("Compiling...")
    val fut = ActionRaw.compile[S](code)
    fut.foreach { actH =>
      println("Compilation done.")
      cursor.step { implicit tx =>
        runWithAction(t, actH())
      }
    }
  }

  def runWithAction(t: Transport[S], act1: ActionRaw[S])(implicit tx: S#Tx): Unit = {
    val tl = Timeline[S]()
    act1.attr.put("foo", StringObj.newConst("first occurrence"))
    tl.add(Span.From((2 * TimeRef.SampleRate).toLong), act1)
    val act2: ActionRaw[S] = Obj.copy(act1)
    act2.attr.put("foo", StringObj.newConst("second occurrence"))
    tl.add(Span.From((8 * TimeRef.SampleRate).toLong), act2)
    t.addObject(tl)
    t.play()
  }
}
// #snippet11