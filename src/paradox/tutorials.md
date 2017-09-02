# Tutorials

@@@ index

* [Getting Started with SoundProcesses](tut_soundprocesses1.md)

@@@

@@toc { depth=1 }

Video tutorials for version 2.10 are available from a [Vimeo album](https://vimeo.com/album/4473871).

## Tutorial 1 - Getting Started

[Video Link](https://vimeo.com/208302767) (14 min)

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

[Video Link](https://vimeo.com/208354987) (19 min)

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

[Video Link](https://vimeo.com/216298165) (16 min)

This tutorial introduces the freesound.org sound file retrieval object:

 - creating a retrieval object
 - specifying query terms and search filter
 - reviewing the search results and previewing sounds
 - authorizing the application to download sounds
 - downloading sounds and using them within Mellite

## Tutorial 4 - Wolkenpumpe

[Video Link](https://vimeo.com/222107666) (33 min)

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

## Tutorial 5...

More tutorials that cover the API and ways of actually programming the system will appear soon (hopefully)...

If you are interested in the confluent versioning workspace, there is [a quite old video](https://vimeo.com/86202332).
I'm planning to cover this topic in updated tutorials.
