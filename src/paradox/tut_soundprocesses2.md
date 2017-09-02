# SP2 - Understanding Objects

The introductory tutorial stopped short of going into the _customary_ way SoundProcesses is used, and instead showed the
transactional variant of ScalaCollider to play a synth. Most of the time, instead of creating instances of `Synth` yourself, you
will instead manipulate other objects that automatically spawn synths when the server is running and some other conditions are met.
In this second tutorial I will introduce this approach to creating sounding objects.

## The Core Object: `Proc`

The SoundProcesses way of referring to a synth is an object of type [`Proc`](latest/api/de/sciss/synth/proc/Proc.html).
The function of `Proc` is threefold:

- it's method `graph` points to the `SynthGraph`, a variable that can be updated
- it's method `outputs` points to an interface for creating outlets that can be patched into other objects
- it implements the [`Obj`](latest/api/de/sciss/lucre/stm/Obj.html) trait or "protocol", a uniform
  way all sorts of objects are modelled in SoundProcesses

Before I explain what this means, let us look at another example snippet that shows the usage of `Proc`:

@@snip [Snippet2]($sp_tut$/Snippet2.scala) { #snippet2 }

If you run this snippet, you should see SuperCollider booting, then hear the familiar 'analog bubbles' sound again. Also a significant
portion of the code is identical to the first version we've seen in the previous tutorial. The main difference here is that instead of
waiting for aural system to have booted and then create a `Synth` instance, an instance of `Proc` is created and added to a `Transport`.
I will discuss the changes step by step, beginning with the initialisation:

@@snip [Snippet2 Implicits]($sp_tut$/Snippet2Parts.scala) { #snippet2implicits }

The first line contains a so-called _type-alias_. In Scala, a type-alias does not create a new type, but simply introduces a new name for
an existing type. In SoundProcesses, many objects require as type parameter the type of system&mdash;in-memory, durable, confluent&mdash;and
we can avoid having to insert the particular system type we choose, here `InMemory`, in many places. So in the following lines, whenever
you see `[S]`, this is just aliased to `[InMemory]`. However, should we eventually decide to change the system type, for example to
`Durable`, the only change that needs to be made to the program is to rewrite the type-alias as `type S = Durable`.

The next line adds an `implicit` modifier to the cursor, and we also annotate the value type explicitly with `stm.Cursor[S]` (note that `stm`
was imported in the very beginning through `import de.sciss.lucre.stm`). The reason for marking this value implicit is that when the
`Transport` object is created further down, it requires an implicit parameter of type `Cursor[S]`, and we therefore make it visible here.
The type annotation `: stm.Cursor[S]` is not necessary, strictly speaking, because Scala will otherwise infer the type automatically. But 
it is good practice to always specify the types of implicit values, because it avoids surprises and ambiguities in the compiler's search for implicit
values. We could also have choosen to use the more precise type `: InMemory` here, but we never need to refer to this value as this particular
system, so the more general type is sufficient and therefore better indicates the use case.

The third line imports an implicit method that provides a [`WorkspaceHandle`](latest/api/de/sciss/synth/proc/WorkspaceHandle.html).
This is a "dummy" workspace that we can use in the absence of a real workspace which we have not dealt with yet. A workspace handle allows
objects to register callbacks for when the workspace closes. For example, the transport registers itself with a workspace in order to ensure
that it stops and frees resources if the corresponding workspace closes. Other than that, it is not of any importance here.

### Instantiating and Configuring `Proc`

Next, let's see the creation of the `Proc` instance:

@@snip [Snippet2 Proc]($sp_tut$/Snippet2Parts.scala) { #snippet2proc }



@@@ warning

__TODO:__ The tutorial is in the making and will continue here

@@@
