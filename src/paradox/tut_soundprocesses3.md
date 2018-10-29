# SP3 - Arranging and Linking

So far we have only looked at a singular sound producing `Proc`. This tutorial will address the question of how multiple processes
can be connected together, and how temporal developments may happen beyond the rather low-level scheduler shown
in the previous tutorial. We start off with temporal arrangements, and then proceed to the exchange between processes.

## Graphemes

There are two objects in SoundProcesses that associate other objects with temporal positions: [`Grapheme`](latest/api/de/sciss/synth/proc/Grapheme.html)
and [`Timeline`](latest/api/de/sciss/synth/proc/Timeline.html). If you look up the API docs, you'll see that `Grapheme` derives from a type
`BiPin[S, A]` where the element type `A` is fixed to `Obj`, and likewise `Timeline` derives from `BiGroup[S, A]` with a fixed element type `A` of `Obj`.
A current limitation of the `Obj`  type system is that type parameters can not be represented, and therefore we need particular sub-types to
fix type parameters, in this case the most generic type `Obj`. The difference between the two is as follows:

- a `Grapheme` is similar to a breakpoint function, such that at any point in time, if the function has been defined in the past,
  there exists exactly one value. In a `Grapheme`, elements are associated with a _point in time_, specified as a `LongObj`. As outlined
  in the previous example, temporal values are given as sample frames with respect to `TimeRef.SampleRate`. You can think of a grapheme
  as a sorted list, so it allows efficient queries by time.
- a `Timeline` is similar to a timeline in a multi-track editor, such that its elements are associated with _time spans_, and at any
  time there may be zero, one, or multiple overlapping elements. The time spans are specified as `SpanLikeObj`, which is an
  `Expr[S, SpanLike]`, and the primitive type [`SpanLike`](latest/api/de/sciss/span/SpanLike.html) can be either a bounded `Span(start, stop)`, or an open
  interval such as `Span.From(start)` or `Span.Until(stop)`. There are also special cases `Span.Void` (empty) and `Span.All` (infinite duration).
  A timeline allows efficient queries by points in time or time intervals.

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

This starts up the system like we did before, with a small change&mdash;we wait until the server is booted before doing anything else, so
we can be sure that when we create a transport and play it, it will sound immediately. The `def main` line ensures that the program can
be executed, it does not do anything special, but it so happens that the body of the object containing this trait will also be initialised,
so all the other statements will be automatically executed when the program is started. The last line `def run` is __abstract__; it does
not end with a `=` and a right-hand side defining the body of the method. Traits can contain abstract methods, and when we use the trait,
we have to define that method. This is a standard object-oriented mechanism, so you have probably encountered it in other languages.
But otherwise, the following example should make clear how this works:

@@snip [Snippet5]($sp_tut$/Snippet5.scala) { #snippet5 }

If we hadn't defined `def run`, the compiler would refuse to compile this. You see that implementing or "mixing in" a trait is done by
writing `extends TraitName`. Before, we had always implemented the `App` trait, now we are implementing our batteries-included trait. Scala
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
notorious problem of UGen initialisation. What should the output of a UGen be when the synth is started? Until version 3.8 of SuperCollider, many UGens
have a rather unintuitive behaviour, for example `Delay1` will not start by outputting zero, as if the delay line was "empty",
but instead it will repeat the first input sample. As a result, `strike` would not produce an initial trigger at time zero.
To compensate for that, we simply add an `Impulse.ar(0)` which is a trick to create a single sample impulse at time zero.
The final multiplication `* 0.05` does not alter the trigger behaviour, but it scales the amplitude of the excitation signal
`hammerEnv`.

The other important bit is how the grapheme is created and registered:

@@snip [Snippet6 Grapheme]($sp_tut$/Snippet6Parts.scala) { #snippet6attr }

Similar to creating a new "blank" proc with `Proc[S]()` (aka `Proc.apply[S]()`), a new empty grapheme is created with
`Grapheme[S]()` (aka `Grapheme.apply[S]()`). We stored a combination of time values and pitches in the `pitches` sequence.
In Scala, you can create ad-hoc records or _tuples_ by putting together comma separated values in parentheses. So `(a, b)`
is a tuple of arity two, its class is actually `Tuple2` with the type parameters corresponding to the types of the first
and second tuple element, respectively. So `(0, 78)`, containing two integer numbers, is of type `Tuple2[Int, Int]`.
To transfer those values into the grapheme, we iterate over the sequence, this is done with the `foreach` method which
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

When synthesis parameters refer to the proc's attribute map, we can use both scalar values in the form
of, for example, a `DoubleObj`, but we can also use more complex objects as control signals, such as a `Grapheme` which
then acts as a breakpoint function, where time zero is aligned with the starting point of the synth.

@@@

You may also have noticed, when playing this snippet, that the first or so note seems a bit faster than the following ones.
Currently, SoundProcesses does not incorporate any mechanism for automatic latency control of OSC bundles to the server.
Messages are sent out when a transaction completes. Normally, on reasonably fast computers, this is not a big issue, but
it leaves room for improvement in future versions. In my own work, I very rarely have the need for extremely precise timing
of client-server communication, and when I need very precise timing, I make sure that temporal signals are produced through
UGens on the server. But this needs not extend to other people's expectations, so hopefully we can improve client-server
timing precision in the future (although this endeavour is quite complicated, since latency and precision are mutual
compromises, and SuperCollider itself has no provision for sample-accurate scheduling).

## Timelines

A timeline allows to place multiple objects to coexist or follow each other in time, similar to regions in a multi-track
editor. The synthetic piano example may not be the best suited here, as each note's duration is
determined by a recursive decay, but for the sake of simplicity, we will just reuse that synth graph, except that we
eliminate the `strike` trigger and just excite once per synth. Here is `Snippet7` which places various piano strikes as
individual procs on a timeline:

@@snip [Snippet7]($sp_tut$/Snippet7.scala) { #snippet7 }

If you play that, you will hear a ritardando, and the piano starts from single pitches and goes into chords, the pitches
being random but with a slight upward tendency. So the timeline is created using `Timeline[S]()`, and we add elements to
it using the `add` method that takes a `SpanLikeObj` for time region and the object to place. How are the pitches
generated and how does the number of voices increase over time? Here is the relevant code:

@@snip [Snippet7 Pitches]($sp_tut$/Snippet7Parts.scala) { #snippet7vec }

The import adds as extension method `linLin` on numbers, something we know from SuperCollider but normally not supported
by numbers in Scala. For those who are curious, you can easily extend classes with new methods in Scala, using so-called
implicit classes (the import above brings such an implicit class into scope):

```scala
implicit class MyIntOps(n: Int) {
  def isPrime: Boolean = n > 1 && !((2 until n-1).exists(n % _ == 0))
}
```

This would "extend" the `Int` type with the method `isPrime`, so we can write `1.isPrime`, `2.isPrime`, `(0 to 10).filter(_.isPrime)`
and so on. Note that Scala does not share SuperCollider's brilliant concept of automatically allowing the two styles
`isPrime(x)` and `x.isPrime`. The former would be an ordinary method defined on some utility object and is not looked for
in the type of `x`.

So what does `Vector.fill` do? `Vector`, like `List` is an immutable collection type in Scala, one that has efficient
random access and a fast `size` method, among other things. We use it here, because there is an `Obj` type in SoundProcesses
that we can use, `DoubleVector` which is roughly an `Expr[S, Vector[Double]]`. We can create a `Vector` passing directly
all its elements, like `Vector(2, 3, 5, 8)`, but we can also use a generator function with `Vector.fill(n)(f)` or
`Vector.tabulate(n)(f)`. The `fill` constructor takes the number of elements in the first argument list, and a parameterless
function in the second argument list, which is invoked for each of the elements.
For example, `Vector.fill(4)(3)` would create the sequence `Vector(3, 3, 3, 3)`.
Since we use a random number generator, and it is evaluated over and over again,
we create vectors of random pitches. In contrast, `Vector.tabulate` passes the element index counter into the function.
For example, `Vector.tabulate(4)(i => i * 3)` would create the sequence `Vector(0, 3, 6, 9)`. In SuperCollider, the closest
equivalent to `fill` and `tabulate` would be `Array.fill(n, f)`.

Now we can explain why the number of voices increases over time. It's the size of the vectors we create, so `(i/5 + 1)` where
`i` runs from 0 (inclusive) until 30 (exclusive). In Scala, `for (i <- range) do-something` is a for-loop that iterates
over a range of numbers with `i` becoming the iteration variable. A range literal can be `start until stop` for an exclusive end,
or `start to stop` for an inclusive end. If you evaluate the number of voices, it goes from `(0/5 + 1) == 1` to `(29/5 + 1) == 6`.
If we now look at the relevant part of the synth graph:

@@snip [Snippet7 Pitch Control]($sp_tut$/Snippet7Parts.scala) { #snippet7graph }

We see that we did not specify a default value for the `pitch` control. Because SoundProcesses expands the UGen graph _late_,
it can look up, in each case, what number of channels the control would have, so in our example, we actually get different
synth-defs in the end, because the pitch control changes from monophonic (a vector of size 1) up to 6 channels.
The late expansion is also the reason why we cannot directly query the number of channels of a graph element or UGen inside
the synth-graph definition. Here the `NumChannels` graph element (or pseudo-UGen) comes to the rescue. It simply expands to
a constant denoting the number of channels of its input argument. We use it here to scale down the amplitude by a factor
determined by the square-root of the number of channels, thereby compensating for the increased volume due to the increased
number of voices. Also note that we use a `Mix` inside the final `Out` UGen to sum the different voices together before
sending them to the audio interface.

Next, let's see the temporal positions of the thirty procs:

@@snip [Snippet7 Spans]($sp_tut$/Snippet7Parts.scala) { #snippet7span }

We create an exponential series from 7 to 21 seconds, and convert it to sample frames. `linexp` was also imported through
`numbers.Implicits` and does exactly what its SuperCollider counterpart does. We give a liberal span length or duration
of eight seconds; by that time, the `Decay` of the sound envelope should have faded to an extremely small number.
Because the first proc does not start at the beginning of the timeline, but after seven seconds, we use a `seek` command
on the transport before we invoke `play`:

@@snip [Snippet7 Seek]($sp_tut$/Snippet7Parts.scala) { #snippet7seek }

We could also have written `t.seek((7 * TimeRef.SampleRate).toLong)`, but since we're smarty-pants, we queried the
position of the first element on the timeline instead. `firstEvent` returns a type `Option[Long]`. Scala uses the type
`Option` for values that may either be defined/present or undefined/absent. If the timeline was empty or only contained
elements with unbounded intervals, the result of `firstEvent` would be `None`. The defined case of an `Option` is
`Some`. In our case, the result would be `Some(98784000L)` (in Scala, 64-bit long integer literals are written with
a trailing `l` or `L` character). We can turn an optional value into a defined value by
using the method `getOrElse(defaultValue)`. If the value is `Some(x)` then `x` is returned, if it is `None`, then
the given `defaultValue` is returned. In SuperCollider, this behaviour would be represented by a value being either `nil`
or not-`nil`, and the equivalent of `x.getOrElse(defaultValue)` would be `x ?? { defaultValue }`.

## Linking Processes

The last example has shown that multiple processes on a timeline may overlap, but so far they did not directly interact
with each other. You may remember that `Proc` had a method `output`, and that I mentioned that this gives us a way to
patch processes together. We are going to look at that in this section.

A common case would be the filtering of one process by another, or the routing of processes to some output buses.
`Snippet8` shows this, decomposing one of SuperCollider's and ScalaCollider's standard example, the aptly named
["what was I thinking?"](https://github.com/Sciss/ScalaCollider/blob/master/ExampleCmd.sc). In that example, a pulse
oscillator is fed through a resonant low-pass filter, and finally augmented by reverberation. If want to take these
three things apart, we can create three individual `Proc`s, and in order to link them, we use the special graph
elements `scan.In` and `scan.Out`. Additionally, we need to create output objects for the first two procs and place
these in the attribute map of the subsequent procs:

@@snip [Snippet8]($sp_tut$/Snippet8.scala) { #snippet8 }

This is what we need to do:

- replace `Out.ar(bus, x)` UGens by `ScanOut(x)`. This means the signal `x` is sent to an internal bus associated
  with the standard output of the proc.
- outputs are in a dictionary `outputs` of a proc. The are organised by string keys, with the default key being `"out"`.
  If we wanted a different key or several outputs, we could specify the key as `ScanOut("key", x)`.
- we have to create a corresponding entry in the `outputs` dictionary using, `p.outputs.add("key")`. This method
  returns an object of type [`Output`](latest/api/de/sciss/synth/proc/Output.html).
- to use this output object as the input to another (sink) proc, it must be placed in the sink's attribute map.
  the sink proc's graph function may grab the signal through a `ScanIn()` graph element. The default key in the
  attribute map is `"in"`. If we want to use another key, we would write `ScanIn("key")`.

Of course, this example is a bit silly, because if we just play these three procs together, there isn't really
a need to spread the sound producing function across three graphs. It makes more sense if several processes
are combined onto one bus, or if the bus signals change over time. This is done in the following `Snippet9`:

@@snip [Snippet9]($sp_tut$/Snippet9.scala) { #snippet9 }

The synth-graph is the same as before, combining again pulse oscillator and resonator within the same function.
I added a `"pitch"` input control, and the signal is faded out using a `FadeOut.ar` element. This is a special
graph element from SoundProcesses that takes into account the proc's span on a timeline, fading out according
to an entry at key `"fade-out"` in the attribute map. That entry is of type `FadeSpec.Obj` where a `FadeSpec`
gives fade duration and curvature. Also, we are sending out a stereo signal now, using a `Pan2` with randomly
changing position.

The for-loop in the middle is interesting which assembles procs on a timeline. Each proc is placed further
along the timeline, with a random variation, and the fundamental pitch increases over time.
Note that to
avoid the annoying multiplications with the sampling rate, we add an extension method `.seconds` like this:

@@snip [Snippet9 Seconds]($sp_tut$/Snippet9Parts.scala) { #snippet9sec }

So `2.seconds` produces the corresponding sample frame `28224000L`. There are two ways in which we can connect
the different oscillator procs to the reverberation: Either we add their outputs to a single collection,
`Folder[S]`, or we add them indeed to another `Timeline[S]`. Here we go for the former, which is slightly
less efficient but otherwise simpler to understand. So, we add the procs themselves with the desired spans
to the timeline, and we add the outputs to a separate object of type `Folder` which is then placed at the
`"in"` key of the attribute map of the reverberation process:

@@snip [Snippet9 Timeline and Folder]($sp_tut$/Snippet9Parts.scala) { #snippet9tl }
@@snip [Snippet9 Adding Procs]($sp_tut$/Snippet9Parts.scala) { #snippet9add }
@@snip [Snippet9 Attr In]($sp_tut$/Snippet9Parts.scala) { #snippet9in }

The `Folder` type has methods `addHead` and `addLast` to add an element either at the beginning or the 
end of the sequence. In our case, the order of the outputs inside the folder does not matter.

Both the timeline and the reverberation proc must then be registered with the transport. Alternatively
(`Snippet9Var`),
we also could have added the reverberation to the timeline with the special position `Span.All`:

@@snip [Snippet9 Variant]($sp_tut$/Snippet9Var.scala) { #snippet9var }

In Mellite, processes inside a timeline with `Span.All` are called "global" processes. In the timeline
editor they appear in the left table of the editor window.
