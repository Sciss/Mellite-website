package de

/**
 
=Welcome to the Mellite API documentation.=

The interfaces are grouped into the following packages:

 - [[de.sciss.synth]] is the '''ScalaCollider''' base package, with things like computation [[de.sciss.synth.Rate Rate]], [[de.sciss.synth.AddAction AddAction]],
   and [[de.sciss.synth.DoneAction DoneAction]].
 - [[de.sciss.synth.ugen]] contains the '''ScalaCollider UGen''' documentation
 - [[de.sciss.lucre.synth]] is the base package for the ''transactional'' layer for ScalaCollider, with things
   like [[de.sciss.lucre.synth.Bus]], [[de.sciss.lucre.synth.Buffer Buffer]], [[de.sciss.lucre.synth.Node Node]], and [[de.sciss.lucre.synth.Server Server]].
 - [[de.sciss.synth.proc]] is the '''SoundProcesses''' base package, with things
   like [[de.sciss.synth.proc.Proc Proc]], [[de.sciss.synth.proc.Timeline Timeline]], [[de.sciss.synth.proc.Folder Folder]], [[de.sciss.synth.proc.AuralSystem AuralSystem]], 
   [[de.sciss.synth.proc.Scheduler Scheduler]], [[de.sciss.synth.proc.Transport Transport]], and [[de.sciss.synth.proc.Workspace Workspace]]
 - [[de.sciss.synth.proc.graph]] are UGen abstractions added by SoundProcesses, such as [[de.sciss.synth.proc.graph.ScanIn ScanIn]] and [[de.sciss.synth.proc.graph.ScanOut ScanOut]].
 - [[de.sciss.mellite.gui]] is the base package for the '''Mellite''' graphical user interface.
 - [[de.sciss.fscape]] is the '''FScape''' base package
 - [[de.sciss.fscape.graph]] contains the '''FScape UGen''' documentation
 - [[de.sciss.fscape.lucre]] is the base package for the ''transactional'' layer for FScape, with things
   like [[de.sciss.fscape.lucre.FScape FScape]] and additional [[de.sciss.fscape.lucre.graph UGens]].

The '''Lucre''' transactional object model:

 - [[de.sciss.lucre.stm]] is the base package for transactions, with things
   like [[de.sciss.lucre.stm.Obj Obj]], [[de.sciss.lucre.stm.Txn Txn]], [[de.sciss.lucre.stm.Sys Sys]], and [[de.sciss.lucre.stm.Cursor Cursor]]
 - [[de.sciss.lucre.artifact]] is the base package for file system '''artifacts''', with
   classes [[de.sciss.lucre.artifact.Artifact Artifact]] and [[de.sciss.lucre.artifact.ArtifactLocation ArtifactLocation]]
 - [[de.sciss.lucre.expr]] is the base package for expression types such as [[de.sciss.lucre.expr.IntObj IntObj]], [[de.sciss.lucre.expr.DoubleObj DoubleObj]], etc.

Other useful packages:

 - [[de.sciss.synth.io]] for reading and writing '''audio files'''
 - [[de.sciss.osc]] for general '''Open Sound Control''' interfaces
 
  */
package object sciss {
}
