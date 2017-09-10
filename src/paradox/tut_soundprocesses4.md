# SP4 - Acting and Reacting

With the exception of the direct use of the scheduler, the structures so far have been created "once", even if they lead to developments that unroll in time.
In this tutorial, we want to look at ways in which one can introduce code fragments that will be executed at specific points in time or when a specific
trigger in a synth-graph happens.

## Actions

In a way, a synth-graph can be seen as a "code fragment"; however what is actually created and stored is a graph of elements that translate&mdash;although
perhaps with some expansions and indirections, as we have seen in the "pseudo-UGens"&mdash;into corresponding signal processing blocks on the SuperCollider
server. But often times we need to be able to insert some imperative actions, we want to be able to generate new structures as a reaction to something that
happens as a piece is performing. In a dynamically typed language, this is rather straight forward: just schedule the execution of a function. For Scala,
this requires a bit more infrastructure, because we want to be able to persist those functions with the workspace, and we want to persist them as compiled
objects in order to minimise the latency that occurs when they are executed. Luckily, most of this infrastructure is hidden from you. On the other hand,
it is useful for you to understand how this is actually made possible, and therefore the first snippet, `Snippet10`, shows the "old workaround" of creating
actions:

@@snip [Snippet10]($sp_tut$/Snippet10.scala) { #snippet10 }

Before explaining, the output from running this snippet is as follows:

> _(after two seconds)_<br>
> Any kind of tentacles will do:<br>
> first occurrence<br>
> _(after another six seconds)_<br>
> Any kind of tentacles will do:<br>
> second occurrence<br>
> Quitting...

(and then you'll see a stack trace from an exception related to the shutting down of the system&mdash;you can ignore that.)

So we can state that placing an action on a timeline will execute it at the starting point of its time span. The duration of the span does not
have any particular meaning, so I just used the open interval `Span.Form` here. The action itself seems to have been created through three
steps: The instantiation of an `Action.Body`, the call `Action.registerPredef`, and the call `Action.predef`. You can think of `Action.Body`
as the equivalent of synth-graph for real-time sound synthesis; it defines the program that is executed. The body has a single method
`apply` with this signature

```scala
def apply[S <: Sys[S]](universe: Action.Universe[S])(implicit tx: S#Tx): Unit
```

In the snippet, we used `T` instead of `S` as type parameter to stress the fact that this is a "fresh" type parameter that does not need
to be identical to the system we used when creating the action. This pecularity helps us understand, what it actually means to create an
action: It is a piece of code that will usually be persisted in the database of workspace to be retrieved and executed at a later point in
time. If you have worked with Mellite, you know that objects can be copied, even from workspace to workspace, even from an in-memory
workspace to a durable workspace and vice versa. The body of the action stays the same, but it may be invoked with different systems,
and that is the reason its `apply` method has a system type parameter.

### Predefined Actions

The argument passed to `apply`, of type `Action.Universe` can be explained in a similar manner: The purpose of an action usually is to
operate on other objects in the workspace, other compositional objects. How do we access them? Look at the snippet: It might have been
tempting to assume that the action body could refer to the _enclosing scope_ and, for example, do something with the transport parameter
of the `run` method, such as stopping or restarting the transport. If we consider the snippet a "normal" program, there would be nothing
wrong with it. But imagine that the action is placed in a workspace&mdash;something that will be shown later&mdash;then the snippet itself
has the function of _generating_ or _manipulating_ that workspace, but it stands "outside" of that workspace. The workspace would have no
knowledge of ephemeral objects such as the transport in here.

The way the action was created in `Snippet10` is via a the regstration of _predefined_ action.

@@@ note

When we create and register a predefined action, we are telling SoundProcesses: Look, this action is provided by us on the classpath that is used
when running the code. An action created via `Action.predef("key")` can be persisted, but it only stores the key in the workspace, and
when executed, a body prior registered via `Action.registerPredef` is looked up in a global dictionary. That body is never written to the
workspace.

@@@

When you start working with workspaces, you will see that this is not very elegant. Ideally we want to store actions fully self-contained
just as we store synth-graphs. If only they were more similar! But a synth-graph is much easier to store, because the graph elements form
a consistent self-contained system, and we don't need to store any actual Scala program.

### Actions from Compiled Source-Code

To overcome this, the "old" solution&mdash;the solution until recently&mdash;was to create actions by _compiling source code at runtime_.

@@@ warning

This tutorial is in the making and still incomplete.

@@@

@@snip [Snippet11]($sp_tut$/Snippet11.scala) { #snippet11 }

![I herd you like compilers](.../tut_sp_meme_compiler_in_runtime.jpg)

### Macro-generated Actions

@@snip [Snippet12]($sp_tut$/Snippet12.scala) { #snippet12 }

![I herd you like compilers](.../tut_sp_meme_compiler_in_compiler.jpg)

### An Action's Universe

## Reacting to Real-time Sound


