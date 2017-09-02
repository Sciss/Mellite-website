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

@@snip [HelloWorld]($sp_tut$/HelloWorld.scala) { #helloworld }

@@@ warning

__TODO:__ The tutorial is in the making and will continue here

@@@
