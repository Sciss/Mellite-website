# Mellite Video Tutorials

Video tutorials for version 2.10 and newer are available from a [Peertube channel](https://peertube.social/video-channels/da59306f-d732-42d5-864f-3b70b868e9fb/videos) ([Vimeo backup](https://vimeo.com/album/4473871)).

## Tutorial 1 - Getting Started

[Video Link](https://peertube.social/videos/watch/d5c98baf-fa70-4211-a31d-95c9181a3dfb) (14 min)

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

[Video Link](https://peertube.social/videos/watch/8a664318-87b4-4e8a-83bb-a847eacbbca2) (19 min)

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

[Video Link](https://peertube.social/videos/watch/306e11ce-d01f-42ac-bb07-62d5d406b85b) (16 min)

This tutorial introduces the freesound.org sound file retrieval object:

 - creating a retrieval object
 - specifying query terms and search filter
 - reviewing the search results and previewing sounds
 - authorizing the application to download sounds
 - downloading sounds and using them within Mellite

## Tutorial 4 - Wolkenpumpe

[Video Link](https://peertube.social/videos/watch/1a179f03-402c-4844-b2e9-653664fe17ca) (33 min)

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

[Video Link](https://peertube.social/videos/watch/2bb93731-d6a5-4d97-8dd5-1f6417d1be6f) (16 min)

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

[Video Link](https://peertube.social/videos/watch/de98dae5-c3d4-464c-9c79-6bfed1367ae3) (21 min)

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

[Video Link](https://peertube.social/videos/watch/46e65cde-b459-487a-9884-0f00725e1980) (18 min)

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

[Video Link](https://peertube.social/videos/watch/6ef9d4cf-7ae0-47c0-b011-f0559e464fea) (16 min)

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

[Video Link](https://peertube.social/videos/watch/39933fdf-2cc8-4594-87aa-8f0ac6e8ed45) (15 min)

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

[Video Link](https://peertube.social/videos/watch/e10b8a0c-7d26-4bcc-a611-1175faa6287c) (21 min)

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

---

If you are interested in the confluent versioning workspace, there is [a quite old video](https://vimeo.com/86202332).
I'm planning to cover this topic in updated tutorials.
