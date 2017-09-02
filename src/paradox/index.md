# Mellite

@@@ index

- [Tutorials](tutorials.md)
- [Links](links.md)

@@@

Mellite is an environment for creating experimental computer-based music and sound art.
This system has been developed since 2012 by its author, Hanns Holger Rutz, and is made
available under the GNU GPL open source license.

![Mellite Screenshot](.../screenshot.png)

## Download

A binary version, ready to run, can be download [here](https://github.com/Sciss/Mellite/releases/latest).
Mellite is cross-platform, it is provided through a universal zip archive that can run on any platform
including Mac and Windows, and a `.deb` package suitable for Linux Debian and Ubuntu.

If you want to build from the source code, go to [github.com/Sciss/Mellite](http://github.com/Sciss/Mellite).

----

**Legal disclaimer:**
Mellite contains libraries also released under the GNU General Public Licence.
You are entitled to the source code of all these libraries. The licenses of
all libraries are [available here](https://github.com/Sciss/Mellite/tree/master/licenses). To
obtain the source code, clone the source code repository of Mellite (see above), and in the file
`build.sbt` add the qualification `withSources()` to any library before running `sbt update`.
For example, to receive the source code of the PDFlitz library used by Mellite, change
`"de.sciss" %% "pdflitz" % pdflitzVersion` to `"de.sciss" %% "pdflitz" % pdflitzVersion withSources()`.
The sources will be downloaded to `~/.ivy2/cache/de.sciss/pdflitz_2.12/srcs`.
If you have trouble obtaining the source code of incorporated libraries, I can send it to you via e-mail
in compliance with the GNU GPL.

----

In order to run Mellite, you also need to have installed on your computer:

- [Java](https://www.java.com/download/) (version 8 or newer)
- [SuperCollider](https://supercollider.github.io/download) (version 3.8.0 is recommended, but 3.7.x should work, too)

## Resources

The API docs can be found [here](latest/api/de/sciss/).

The best way to ask questions, no matter if newbie or expert, is to use the [Gitter Channel](https://gitter.im/Sciss/Mellite). You need a GitHub or Twitter account to sign in.
