# SP4 - Acting and Reacting

@@@ warning

The use of the old `Action` object is deprecated, and it is now called `ActionRaw`. A new `Action` object
has been introduced that has a well-defined API and better compatibility across versions. Currently, only
this tutorial for the old, deprecated actions is available.

@@@

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
it is useful to understand how this is actually made possible, and therefore the first snippet, `Snippet10`, shows the "old workaround" of creating
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
to be identical to the system we used when creating the action. This peculiarity helps us understand, what it actually means to create an
action: It is a piece of code that will usually be persisted in the database of a workspace to be retrieved and executed at a later point in
time. If you have worked with Mellite, you know that objects can be copied, even from workspace to workspace, even from an in-memory
workspace to a durable workspace and vice versa. The body of the action stays the same, but it may be invoked with different systems,
and that is the reason its `apply` method has a system type parameter.

The argument passed to `apply`, of type `Action.Universe` can be explained in a similar manner: The purpose of an action usually is to
operate on other objects in the workspace, other compositional objects. How do we access them? Look at the snippet: It might have been
tempting to assume that the action body could refer to the _enclosing scope_ and, for example, do something with the transport parameter
of the `run` method, such as stopping or restarting the transport. If we consider the snippet a "normal" program, there would be nothing
wrong with it. But imagine that the action is placed in a workspace&mdash;something that will be shown later&mdash;then the snippet itself
has the function of _generating_ or _manipulating_ that workspace, but it stands "outside" of that workspace. The workspace would have no
knowledge of ephemeral objects such as the transport in here.

### Predefined Actions

The way the action was created in `Snippet10` is via a the registration of _predefined_ action. Its body is not persisted in a workspace,
and therefore must be explicitly registered before it can be used.

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

![I herd you like compilers](.../tut_sp_meme_compiler_in_runtime.jpg)

To overcome this, the "old" solution&mdash;the solution until recently&mdash;was to create actions by _compiling source code at runtime_.
SoundProcesses has a light-weight type to represent source code, called `Code` with different sub-types for the kind of programs (including
the type of `import` statements implicitly provided and the kind of output produced by the program). The source code itself can be given
as a `String`, for example using Scala's triple-quotes `"""` so that line breaks can be used without escaping them. The code object then can be
passed to `Action.compile`, as shown in the following `Snippet11`:

@@snip [Snippet11]($sp_tut$/Snippet11.scala) { #snippet11 }

It's clear that this approach is inconvenient, because it adds a number of problems:

- The source code being a `String` means that it is not checked at the time the outer program is compiled. As a workaround, you would
  probably write the code as in the first ('predef') example, and then escape it as a string later.
- The compilation can be slow, especially in the first run, when the compiler has not been initialised yet. Therefore, the API decision was
  to return a `Future[A]` from `Action.compile`. A [`Future` in Scala](http://docs.scala-lang.org/overviews/core/futures.html) denotes an asynchronous process that eventually, in the future, leads to
  a computed value of type `A` (or a failure). So `Action.compile` returns immediately, but the compiled `Action` is not available immediately.
  Therefore, what the snippet does, is to use `fut.foreach` to execute a function when the future is completed. This function must open a
  new transaction, because the old one from which `Action.compile` was called, has been completed at this point. To create a new transaction, we use
  `cursor.step { implicit tx => ... }`. The value of the future is of type `stm.Source[S#Tx, Action[S]]`, a reference to the action. It must
  be resolved in the new transaction, using the `apply` method, i.e. `actH.apply()` or just `actH()`. Only then can we continue with our
  program building process, invoking `runWithAction` as a separate step.

These problems led to the current design alternative of using macros to generate actions.

### Macro-generated Actions

![I herd you like compilers](.../tut_sp_meme_compiler_in_compiler.jpg)

Macros are meta-programs or 'program synthesisers'. In Scala, the most common form is a `def`-macro, i.e. a macro in the shape of a method
that, when called, synthesises part of the program around the call-site. For the user, a `def`-macro may look very much like a regular
method invocation, but with the ability to do some "magic" to the code. In our case, we added a `def`-macro as method `Action.apply`. It
generates a "full" action that can be persisted along with its body, without the two problems of the `Action.compile` approach shown in the
previous section. The macro essentially _compiles at compile-time_ the function passed as argument to `Action.apply` using another specially created compiler,
storing the serialised program in the returned object, and as a nice side-effect, it also stores the source code for the action body in
the attribute map of the action object, making it possible to open the source code editor later in the Mellite GUI.

`Snippet12` shows how macro-based action generation looks like:

@@snip [Snippet12]($sp_tut$/Snippet12.scala) { #snippet12 }

Note that `import MacroImplicits._` brings `Action.apply` into scope as an extension method that otherwise does not exist.
Another interesting aspect of this is, as the macro compiles, using a fresh compiler, _the source code extracted from the call_,
there is no risk of accidentally catching symbols from the environment. You can try this out yourself. If you attempted to
compile a program containing:

```scala
val act1 = Action.apply[S] { universe =>
  println("Transport: " + t)
}
```

The compiler would report:

>     /tmp/temp4836962927583971584.scala:12: error: not found: value t
>     println("Transport: " + t)
>                             ^

On the other hand, you need to be sure to place all necessary import statements inside the `apply` block. SoundProcesses by default gives you the most
common imports, such as `de.sciss.synth.proc._`, but not everything that is at the top of your source file. Imagine you wanted to print the current
time:

```scala
import java.util.Date
val act1 = Action.apply[S] { universe =>
  println("Current time: " + new Date)
}
```

This this would fail to compile:

>     /tmp/temp3097917770584164351.scala:12: error: not found: type Date
>     println("Current time: " + new Date)
>                                    ^

In other words, the compiler that compiles the body of the action does not see anything that's outside that body. Your editor, e.g. IntelliJ, may not
indicate this problem, as it does not have any idea that we are calling a special macro. To make the above work, put the import inside the body:

```scala
val act1 = Action.apply[S] { universe =>
  import java.util.Date
  println("Current time: " + new Date)
}
```

### An Action's Universe

The action's body is invoked with an argument of type [`Action.Universe`](latest/api/de/sciss/synth/proc/Action$$Universe.html).
This is the interface to the "outer world" of the body,
a way to find and access other objects in SoundProcesses. It extends the general [`Universe`](latest/api/de/sciss/synth/proc/Universe.html),
which contains pointers to the cursor and workspace, and adds additional methods. Methods include:

- `cursor: Cursor[S]`, if we have the need to issue new transactions (the action's body is called inside a transaction, so in most cases we don't need the cursor)
- `self: Action[S]`, the action object whose body is executed. A common pattern is to use that action's attribute map to find other objects. This is what the
  example snippets are doing and is explained further down.
- `workspace: Workspace[S]`, if we need to access objects by traversing the workspace's root folder, for example
- `invoker: Option[Obj[S]]`, an object that functions as a "parent" to the action in its invocation; this is used by `Proc`, as we will see later
- `value: Any`, a general interface for passing all sorts of data to the action. Again, we will see a use case later

The last snippets made use of `self` to get to the attribute map, looking for a string object at key `"foo"`. Here is again the relevant portion:

@@snip [Snippet12 Self]($sp_tut$/Snippet12Parts.scala) { #snippet12self }

The method `$` on the attribute map is a convenient way to query an entry with an _expected value type_. Since values in attribute map can be all sorts of
objects, the `get` method would only return an `Option[Obj[S]]`, which means that to do anything meaningful with the return value, we would need an additional
pattern match. The dollar method avoids this by taking a type parameter, specifying the expected type of the value. Here, we pass the type without its own
system type parameter, so we write `attr.$[StringObj]("foo")` instead of `attr.$[StringObj[S]]("foo")`, saving at least some boiler plate. This call looks
for an entry at key `"foo"`, and only if the entry is found _and the value type is string object_. The method's return type thus becomes `Option[StringObj[S]]`
instead of the generic `Option[Obj[S]]`. Furthermore, we use a [for-comprehension](https://stackoverflow.com/questions/3754089/scala-for-comprehension#3754568)
to match the result and extract the string object inside the option. In Scala, `for (x <- anOption) { }` is the same as `anOption.foreach { x => ... }`.
The assignment `val v = fooObj.value` then evaluates the found expression to a primitive string value which is then printed to the console.

Without the use of the `$` method, the body could have been written as follows:

@@snip [Snippet12 Without Dollar]($sp_tut$/Snippet12Parts.scala) { #snippet12nodollar }

The dollar method along with for-comprehensions is useful, as we can extract all attributes the action wishes to use in a sequentially written manner:

@@snip [Snippet12 Multiple Attributes]($sp_tut$/Snippet12Parts.scala) { #snippet12multiple }

## Reacting to Real-time Sound

Rather than scheduling actions at precise moments, for example on a timeline, one often wants to use them to react to some input to the system, such as
a real-time sound signal. For the purpose of this tutorial, we don't use a microphone signal or anything like that, but something that can be directly
reproduced on any computer. We use a synth that plays a sequence of four random pitches, and when the sound has decayed to -60 dB, an action is invoked.
That action then decreases a counter, and as long as the new counter value is greater than zero, it restarts the same sound process. The code is in
`Snippet13`:

@@snip [Snippet13]($sp_tut$/Snippet13.scala) { #snippet13 }

Let's break that up into several bits. First the overall structure:

@@snip [Snippet13 Scaffold]($sp_tut$/Snippet13Parts.scala) { #snippet13scaffold }

It introduces two new objects, `Folder` and `Ensemble`. The folder is simply a linear list of other objects, and it is in fact the 
thing you see first in Mellite when you create a workspace: Every workspace starts with a root folder. You can add objects to a 
folder using `addHead` (to the beginning of the list) and `addLast` (to the end of the list). We add a `Proc` to the folder. 
An `Ensemble`, in turn, combines a folder with a time offset (here zero) and a boolean playing state. It is the ensemble `ens` that
we finally add to the transport. We can then use the `BooleanObj.Var` that we passed to the ensemble constructor to toggle the
playing of that ensemble. To play an ensemble means to play all objects inside its folder, so here the single `Proc`.

We want to access the counter variable of type `IntObj.Var` and the playing state variable of type `BooleanObj.Var` from within
the action. The easiest way to do that is put them in its attribute map.
Inside the action's body we look for those objects again using `universe.self.attr`. We cannot use the more specific variable
type like `attr.$[BooleanObj.Var]("play")`, but only the main type `attr.$[BooleanObj]("play")`&mdash;this is a limitation in
the "type system" of SoundProcesses&mdash;but we can use an additional pattern match by writing `BooleanObj.Var(x)` on the
left-hand side inside the for-comprehension:

@@snip [Snippet13 Body]($sp_tut$/Snippet13Parts.scala) { #snippet13body }

Thus, `ply` is now of type `BooleanObj.Var` and we can update it writing `ply() = ...`, and `cnt` is now of type `IntObj.Var` and
we can update it writing `cnt() = ...`.

Similar to plugging the objects we need inside the action to the action's attribute map, we also need to make an association from
the action to the proc that triggers the action.
We put the action in the proc's attribute map, and inside the graph function, we make use of the special graph element `Reaction`:

@@snip [Snippet13 Reaction]($sp_tut$/Snippet13Parts.scala) { #snippet13reaction }

The `DetectSilence` UGen goes from zero to one when the input signal `sig` falls below a given threshold for a given period
(100ms default). In order to avoid it triggering multiple times, we wrap it in a `SetResetFF` with no reset signal. `Reaction` then
takes that `done` trigger and invokes an action looked up in the proc's attribute map at key `"done"`, setting its
universe's `value` field to the value of the graph element `pitch`. If you observe the console printing of the snippet, it looks like this:

> Action - last midi pitch was FloatVector(Vector(54.0))<br>
> Counter is 3 - restarting.<br>
> Action - last midi pitch was FloatVector(Vector(72.0))<br>
> Counter is 2 - restarting.<br>
> Action - last midi pitch was FloatVector(Vector(70.0))<br>
> Counter is 1 - restarting.<br>
> Action - last midi pitch was FloatVector(Vector(57.0))<br>
> Counter reached zero.

The value passed to the action has the perhaps strange appearing type `FloatVector`. Like `SendReply`, `Reaction` may transmit
multiple values to the client, so that is the reason we have a vector (sequence) of floats instead of a single float. The extra
wrapping `FloatVector` makes it easier to extract or pattern match the untyped (`Any`) `value` method of the `Action.Universe`. So
if we wanted to use the single pitch value as a number in the action body, we could have written:

@@snip [Snippet13 Value Extractor]($sp_tut$/Snippet13Parts.scala) { #snippet13value }

And the output would have been:

> Action - last midi pitch was 67.0<br>
> Counter is 3 - restarting.<br>
> Action - last midi pitch was 71.0<br>
> Counter is 2 - restarting.<br>
> Action - last midi pitch was 57.0<br>
> Counter is 1 - restarting.<br>
> Action - last midi pitch was 66.0<br>
> Counter reached zero.

@@@ warning

A word of caution: Using pattern extraction in the way `val Pattern(x) = ...` can result in runtime errors if the pattern matching
fails, for example if we made a mistake and wrongly assumed the `value` to be of type `FloatVector`. But if we take that risk,
we can write very succinct code.

@@@
