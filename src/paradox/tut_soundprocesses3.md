# SP3 - Linking and Interacting

So far we have only looked at a singular sound producing `Proc`. This tutorial will address the question of how multiple processes
can be connected together, and how temporal developments and interactions may happen beyond the rather low-level scheduler shown
in the previous tutorial. We start off with (determined) temporal arrangments that do not contain interactions.

## Graphemes

There are two objects in SoundProcesses that associate other objects with temporal positions: [`Grapheme`](latest/api/de/sciss/synth/proc/Grapheme.html)
and [`Timeline`](latest/api/de/sciss/synth/proc/Timeline.html). If you look up the API docs, you'll see that `Grapheme` derives from a type
`BiPin` where the stored element type is fixed to `Obj`, and likewise `Timeline` dervies from `BiGroup` with a fixed element type of `Obj`.
A current limitation of the `Obj`  type system is that type parameters can not represented, and therefore we need particular sub-types to
fix type parameters, in this case the most generic type `Obj`. The difference between the two is as follows:

- a `Grapheme` is similar to a breakpoint function, such that at any point in time, if the function has been defined in the past,
  there exists exactly one value. In a `Grapheme`, elements are associated with a _point in time_, specified as a `LongObj`. As outlined
  in the previous example, temporal value are given as sample frames with respect to `TimeRef.SampleRate`. You can think of a grapheme
  as a sorted list, so it allows efficent queries by time.
- a `Timeline` is similar to a timeline in a multi-track editor, such that its elements are associated with _time spans_, and at any
  time there may be zero, one, or multiple overlapping elements. The time spans are specified as `SpanLikeObj`, which is an
  `Expr[S, SpanLike]`, and the primitive type [`SpanLike`](latest/api/de/sciss/span/SpanLike.html) can be either a bounded `Span(start, stop)`, or an open
  interval such as `Span.From(start)` or `Span.Until(stop)`. There are also special cases `Span.Void` (empty) and `Span.All` (infinite duration).
  A timeline allows efficient query by point in time or time interval.

@@@ note

If you have used the timeline editor in Mellite, you have basically edited a `Timeline` object. On the other hand, editors for
`Grapheme` breakpoint functions are, as of this writing, not yet fully implemented in the Mellite GUI.

@@@

The last example of the previous tutorial showed how a scheduler can be used to update an attribute value and thereby modulate the sound
of a `Proc`. The grapheme allows us to do something similar, but decoupled from the real-time scheduling. We can place designated breakpoints
in a grapheme, and this grapheme can then be used instead of the scalar `DoubleObj` in the proc's attribute map.

### Putting the Startup Ceremony in a Trait

Before we kick off, here is an example trait we can "mix in" to our next snippets to avoid having to type the same thing over and over again:

@@snip [In-memory sound app]($sp_tut$/InMemorySoundApp.scala) { #inmemorysoundapp }

This starts up the system like we did before, with a small change - we wait until the server is booted before doing anything else, so
we can be sure that when we create a transport and play it, it will sound immediately. The `def main` line ensures that the program can
be executed, it doesn't do anything special, but it so happens that the body of the object containing this trait will also be initialised,
so all the other statements will be automatically executed when the program is started. The last line `def run` is __abstract__; it does
not end with a `=` and a right-hand side defining the body of the method. Traits can contain abstract methods, and when we use the trait,
we have to define that method. This is a standard object-oriented mechanism, so you have probably encountered it in other languages.
But otherwise, the following example should make clear how this works:

@@snip [Snippet5]($sp_tut$/Snippet5.scala) { #snippet5 }

If we hadn't defined `def run`, the compiler would refuse to compile this. You see that implementing or "mixing in" a trait is done by
writing `extends TraitName`. Before we had always implemented the `App` trait, now we implementing our battries-included trait. Scala
allows multiple trait mixin which is very powerful. It's syntax would be `object MyObject extends Trait1 with Trait2` or 
`class MyClass extends Trait1 with Trait2`. The object or class can then use any members defined in those traits&mdash;here for example
type `S` and value `cursor`&mdash;unless they are marked `private`.

### A Grapheme for Pitch Values

With this trait, we can now introduce the example `Snippet6` that shows how to use a grapheme to create
a sequence of pitch values:

@@snip [Snippet6]($sp_tut$/Snippet6.scala) { #snippet6 }

The snippet looks very long, but the main reason is that we used a more sophisticated synth graph, a synthetic piano taken from the
standard SuperCollider examples. What is important in the graph is again the creation of a control, and we use a trick to derive from
the changes in the control value a trigger for exciting the piano sound:

@@snip [Snippet6 Graph]($sp_tut$/Snippet6Parts.scala) { #snippet6graph }

If you are new to SuperCollider, the `.poll` statement means that the current value of `pitch` is printed to the console whenever
the trigger signal `strike` goes from non-positive to positive. Since `strike` is defined to trigger exactly when `pitch` changes,
the poll will print exactly the sequence of pitch values as they enter the real-time sound synthesis server.
The way the trigger is generated looks complicated. How do we detect that `pitch` changes? One way to do that is to use `HPZ1`,
a simple high-pass filter that differentiates the signal, subtracting from the current sample the value of the previous sample.
If the signal does not change, the output of `HPZ1` is zero, if it changes it will be non-zero. To ensure we have a consistent
signal of zero or one, `.signum.abs` converts any non-zero signal to one. There are other possibilities to express the same
idea, for example `pitch sig_!= Delay1.ar(pitch)` would have been one. What complicates the matter is that SuperCollider has a
notorious problem of UGen initialisation. What should the output of UGen be when the synth is started? Until now, many UGens
have a rather unintuitive behaviour, for example `Delay1` will not start by outputting zero, as if the delay line was "empty",
but instead it will repeat the first input sample. As a result, `strike` would not produce an initial trigger at time zero.
To compensate for that, we simply add an `Impulse.ar(0)` which is a trick to create a single sample impulse at time zero.
The final multiplication `* 0.05` does not alter the trigger behaviour, but it scales the amplitude of the excitation signal
`hammerEnv`.

The other important bit is how the grapheme is created and registered:

@@snip [Snippet6 Grapheme]($sp_tut$/Snippet6Parts.scala) { #snippet6attr }

Similar to creating a new "blank" proc with `Proc[S]` (aka `Proc.apply[S]`), a new empty grapheme is created with
`Grapheme[S]` (aka `Grapheme.apply[S]`). We stored a combination of time values and pitches in the `pitches` sequence.
In Scala, you can create ad-hoc records or _tuples_ by putting together comma separated values in parenthesis. So `(a, b)`
is a tuple of arity two, its class is actually `Tuple2` with the type parameters corresponding to the types of the first
and second tuple element, respectively. So `(0, 78)`, containing two integer numbers, is of type `Tuple2[Int, Int]`.
To tranfer those values into the grapheme, we iterate over the sequence, this is done with the `foreach` method which
corresponds with `do` in SuperCollider's collections, although we don't automatically get an extra index counter argument.
The `{ case (beat, pch) => ... }` way of writing a lambda is special in Scala in that we use _pattern matching_. A
function literal in Scala can be written with a list of `case` statements, each of which checks the function's input
argument, and when it matches, runs the corresponding body of the `case` branch. In many cases there is only one single
`case` statement, and it's used not to detect if an argument matches a pattern, but to decompose it and "extract"
elements of it. Here is the iteration written without pattern matching:

@@snip [Snippet6 No Pattern Matching]($sp_tut$/Snippet6Parts.scala) { #snippet6nopatmat }

You see that `Tuple2` has two methods `_1` and `_2` to access the two elements stored in the tuple. You will probably agree
that the first form, using the pattern matching, is more readable, and that's why we usually use that form. When you write
`case (beat, ch) =>`, Scala extracts the two tuple elements and assigns them to local values `beat` and `ch`. We will learn
more about pattern matching in the future. For now, what's important is how we create a proper time key for the grapheme,
and how key and value are stored. Our `pitches` sequence used logical "beat" indices as time pointers. The grapheme expects
sample frames with reference to `TimeRef.SampleRate`. If we decide that the tempo be two beats per second (or 120 bpm), then
we need to multiple the beat index with 0.5 to obtain the time point in seconds. To obtain a sample frame, we multiply with
the sample rate, and we must then convert the `Double` floating point number to a `Long` expected by the grapheme. The
MIDI note pitches we can then use directly, wrapping them inside `IntObj` instances. The system can feed both integer and
floating point numbers to the control input of a synth. Behind the scenes, they will always be converted to 32-bit floating
point numbers, because that's the only precision understood by the SuperCollider server.

@@@ note

So you can see that when synthesis parameters refer to the proc's attribute map, we can use both scalar values in the form
of, for example, a `DoubleObj`, but we can also use more complex objects as control signals, such as a `Grapheme` which
then acts as a breakpoint function, where time zero is aligned with the starting point of the synth.

@@@

You may also have noticed, when playing this snippet, that the first or so note seems a bit faster than the following ones.
Currently, SoundProcesses does not incorporate any mechanism for automatic latency control of OSC bundles to the server.
Messages are sent out when a transaction completes. Normally, on reasonably fast computers, this is not a big issue, but
it leaves room for improvement in future versions. In my own work, I very rarely have the need for extremely precise timing
of client-server communication, and when I need very precise timing, I make sure that temporal signals are produced through
UGens on the server. But this need not extend to other people's expectations, so hopefully we can improve client-server
timing precision in the future (although this endeavour is quite complicated, since latency and precision are mutual
compromises, and SuperCollider itself has no provision for sample-accurate scheduling).

## Timelines

@@@ warning

This tutorial is in the making and incomplete

@@@


