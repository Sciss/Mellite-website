# SP3 - Linking and Interacting

So far we have only looked at a singular sound producing `Proc`. This tutorial will address the question of how multiple processes
can be connected together, and how temporal developments and interactions may happen beyond the rather low-level scheduler shown
in the previous tutorial. We start off with (determined) temporal arrangments that do not contain interactions.

## Graphemes and Timelines

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

### A Grapheme for Frequency Values

@@snip [Snippet6]($sp_tut$/Snippet6.scala) { #snippet6 }


@@@ warning

This tutorial is in the making and incomplete

@@@


