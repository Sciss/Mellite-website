# Mellite Video Tutorials

Video tutorials for version 2.10 and newer are available from [diode.zone (Peertube)](https://diode.zone/video-channels/mellite/videos)
or the [Vimeo backup](https://vimeo.com/album/4473871).

## Tutorial 1 - Getting Started

[Video Link](https://diode.zone/videos/watch/72b62385-a120-4de3-97f6-5203ca9efd01) (14 min)

This is a basic introduction from scratch:

 - installing the downloaded application
 - checking dependencies
 - setting up basic preferences
 - creating a new workspace
 - creating objects in the workspace
 - defining a sound process
 - listening to sound
 - attribute map and sound synthesis controls
 - copying objects between workspaces

## Tutorial 2 - Timeline and Audio Files

[Video Link](https://diode.zone/videos/watch/ddf44d04-40fa-41dd-bb5c-9a8c968d22ac) (19 min)

This tutorial introduces the timeline object and shows how to arrange sound file regions:

 - importing an audio file
 - artifact base locations
 - using the audio file player
 - creating a timeline object
 - dragging audio file regions
 - "global processes" in a timeline
 - routing from an audio region to a global process
 - predefined controls for gain, fade, mute
 - creating a programmed filtering process on the timeline
 - process outputs
 - using the mute state to control filter bypass
 - bouncing to disk

## Tutorial 3 - Freesound.org

[Video Link](https://diode.zone/videos/watch/5f22fa2f-2af1-4c80-899f-3191d162aec7) (16 min)

This tutorial introduces the freesound.org sound file retrieval object:

 - creating a retrieval object
 - specifying query terms and search filter
 - reviewing the search results and previewing sounds
 - authorizing the application to download sounds
 - downloading sounds and using them within Mellite

## Tutorial 4 - Wolkenpumpe

[Video Link](https://diode.zone/videos/watch/6b6bb10e-1fb2-4fa3-be7e-0e04d7f0f10e) (33 min)

This tutorial introduces the Wolkenpumpe live interface object:

 - creating a new Wolkenpumpe object
 - components and signal flow: generators > filters > collectors
 - populating with default sound processes; generator channels and audio file players
 - opening the Live view, transport
 - inserting generators and collectors
 - changing parameters with rotary controls
 - inserting filters
 - modulating signals with other signals
 - deleting connections and modules
 - pinning object positions
 - parameter keyboard control, multi-channel parameters
 - inspecting the resulting timeline
 - defining your own sound processes
 - `ScanIn`, `ScanOut` and output objects for creating patchable processes
 - `Param` UGen for parameters, parameter specs
 - clearing the timeline
 - modulator signal range 0 to 1 vs. audio signal range -1 to 1
 - defining a custom filter
 - copying an empty template setup

## Tutorial 5 - Patterns

[Video Link](https://diode.zone/videos/watch/5ead4638-929c-4339-9364-1982f000d793) (16 min)

This tutorial introduces the Pattern abstraction:

 - creating a new Pattern object
 - writing and evaluating a pattern expression in the editor
 - naming differences and operational differences between SuperCollider and Scala
 - looking up the API docs
 - type parameters of patterns
 - constant patterns and a pattern of given elements, looping patterns
 - collection-like operations such as `++`, `drop`, `take`, `sorted`
 - feeding a pattern into the attribute map of a proc to control a sound parameter
 - future plans

## Tutorial 6 - Widgets

[Video Link](https://diode.zone/videos/watch/f1a0110a-b4ef-474d-860a-c8e2dabc9a7c) (21 min)

This tutorial introduces Widget programs:

- creating a Widget object
- creating a static text `Label`
- creating a `Slider` with label and custom value ranges
- composing expressions and assigning them to a label text
- API documentation
- widget graph programs vs. proc (UGen graph) programs
- linking widget model to an attribute map
- bidirectional mapping (persisting models)
- `DoubleField` as alternative to `Slider`
- `Runner` control to play a proc from the widget program
- `Button` component, triggers (`Trig`) and actions (`Act`)

## Tutorial 7 - Patterns II

[Video Link](https://diode.zone/videos/watch/cc39dd19-f71e-4fc4-99b7-765b55f4c0df) (18 min)

This tutorial continues with patterns, introducing a way to play them as main objects,
specifying auxiliary sound producing functions.

- recall how patterns can provide a parameter input to a signal proc object
- reverse dependency of pattern and proc: proc becomes input to the pattern
- using `Bind` to create fully specified event patterns
- `"play"` pointing to an object to play, arbitrary keys to feed patterns into that object
- timing: delta, legato, sustain keys
- embedding a pattern in a timeline
- a pattern to play the soundfiles within a folder in a random order
- creating a pattern from the pattern object's attribute map
- auto-run an object when launching Mellite

## Tutorial 8 -- CLI and Grapheme Editor

[Video Link](https://diode.zone/videos/watch/99140d27-3e82-4869-b768-dcf211e6e7f6) (16 min)

This tutorial is more casual and introduces a new command line interface for creating objects,
as well as the grapheme (break-point function) editor.

- opening the CLI with <kbd>control</kbd>-<kbd>1</kbd> in the folder window
- showing usage help for an object, and listing available objects
- creating a zig-zag break-point function
- writing a proc whose frequency parameter is patched to the grapheme
- using the CLI to create entries in an attribute map
- editing individual `EnvSegment` break-points (magnitude and curvature)
- a look at a recording session: a grapheme to control reverb-gain
- using `FadeInOut.ar` as another straight forward way to control filters

## Tutorial 9 -- Built-in API docs, and new code editor

[Video Link](https://diode.zone/videos/watch/0a5bff86-8600-49ee-ae4c-4f33c51a59fd) (15 min)

This tutorial introduces the new Dotterweide editor used for the embedded
Scala snippets.

- Cache and API documentation
- new Dotterweide embedded Scala editor
- `play { }` idiom in a Proc graph
- Error diagnostics and code completion
- API look-up
- look-up from FScape
- Show usage of a symbol, go to definition
- Editor keyboard shortcuts

## Tutorial 10 -- Widgets and receiving OSC

[Video Link](https://diode.zone/videos/watch/30e0a119-07b1-4c26-93dc-0f53ea88c420) (21 min)

This tutorial introduces the Widget object, using as example the communication from
Processing to Mellite via Open Sound Control messages.

- Creating a new Widget program
- Label and FlowPanel
- OscUdpNode to create an OSC socket
- the Bang component
- Connecting a trigger to an action
- Processing and its oscP5 library
- Sending mouse coordinate from Processing to Mellite
- Matching on particular OSC messages
- Displaying an OSC argument
- Runner control for a Proc object
- References through the attribute map
- Sequential actions
- Nested attribute references
- Arithmetic composition of expressions

## Tutorial 11 -- Negatum genetic programming

[Video Link](https://diode.zone/videos/watch/e3980e15-bdd1-48e1-9f40-77e4f7914d03) (39 min)

This tutorial introduces the Negatum object, a process for generating sound synthesis
structure through automated genetic programming.

- Introduction to Genetic Algorithms and Genetic Programming
- Creating a `Negatum` object, selecting an input (target) sound
- Walk through the algorithm's parameters (generation, evaluation, breeding)
- Starting and stopping iterations
- Examining individual sound results; auto-generated sound code
- Copying and optimising selected sound programs
- Drawing a UGen graph diagram of a sound program
- Experimenting with the results: Adding multi-channel expansion and named controls
- Future outlook
- Examining the `Mix` set of tree leaves

## Tutorial 12 -- Measuring impulse responses

[Video Link](https://diode.zone/videos/watch/889507c8-cf76-405f-baf9-c63220accb82) (28 min)

This uses a workspace available in the Mellite downloads, which provides a widget for the entire measurement process
(behind the scenes FScape and Proc are combined, but this is not shown in detail). The video starts with a general
introduction how IR measurement with sine sweeps works.

- What is the impulse response of an unknown (black box) linear system
- Finite impulse response filters (frequency filters, reverberation, ...)
- Principles of digitally measuring an impulse response
- Sine sweeps
- Deconvolution and a trick to use "normal" convolution
- Walk through the parameters of the IR measuring tool
- Applying the measured IR to a "dry" sound

## Tutorial 13 -- Stream object, Oscilloscope

[Video Link](https://diode.zone/videos/watch/7c973721-c189-48b4-901e-1130a84bbc61) (21 min)

This tutorial shows the new Stream object - streams are "expanded" patterns that persist their internal state.
Towards the end, the new Oscilloscope view is demonstrated.

- Recap: a Pattern to produce Brownian movement; playing a Bind Pattern.
- State of a Pattern
- The Brownian movement as a Stream instead
- A memorised counter Stream
- An Action to take and print the next stream value
- Options and default values for Action attributes
- State is preserved even quitting Mellite
- Resetting a Stream
- Swapping the Stream back to a Pattern
- the Oscilloscope view

## Tutorial 14 -- Raspberry Pi GPIO support, and system (shell) processes

[Video Link](https://diode.zone/videos/watch/03ea9aa7-8321-4e4d-9d9c-bc5955fd2064) (37 min)

This tutorial shows the basic GPIO support - accessing the digital input and output pins on a Raspberry Pi, the example being a button box and using a relay to route a sound signal to different speakers. Towards the end, showing how to start and parse system or shell processes, and passing key/value properties to Mellite and using it within a control program.

- Wiring push buttons to the Raspberry Pi
- Using the `GPIO.DigitalIn` object in a widget program
- Specifying pull-up or pull-down resistors, inverting a signal
- Attaching a mechanical relay to route an input sound signal to two alternative outputs
- Using a Logic-Level Converter to lift the 3.3V GPIO output to 5V
- Using the `GPIO.DigitalOut` object in a widget program
- Combining input and output in a control program
- Running shell commands with `Sys.Process`
- Obtaining properties passed to Mellite with `Sys.Property`

---

If you are interested in the confluent versioning workspace, there is [a quite old video](https://vimeo.com/86202332) (see 17'15" into the video).
I'm planning to cover this topic in updated tutorials.
