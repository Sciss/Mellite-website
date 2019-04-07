# Mellite

@@@ index

- [Tutorials](tutorials.md)
- [Links](links.md)

@@@

Mellite is an environment for creating experimental computer-based music and sound art.
This system has been developed since 2012 by its author, Hanns Holger Rutz, and is made
available under the GNU Affero General Public License.

![Mellite Screenshot](.../screenshot.png)

## Download

A binary version, ready to run, can be downloaded on [archive.org](https://archive.org/details/Mellite) or
[GitHub releases](https://github.com/Sciss/Mellite/releases/latest).
Mellite is cross-platform, it is provided through a universal zip archive that can run on any platform
including Mac and Windows, and a `.deb` package suitable for Linux Debian and Ubuntu.

If you want to build from the source code, go to [git.iem.at/sciss/Mellite](https://git.iem.at/sciss/Mellite).

See below for additional requirements (Java, SuperCollider).

On __Mac__, you need to allow the system to launch Mellite the first time you download it. Extract the zip
archive, and go into the `bin` directory. Right-click (or Ctrl-click) on the `mellite` program, then choose `Open`.
The computer will now warn you that the program is not verified, you have to confirm that you really want to open
it. A terminal window should then pop up and soon after the Mellite main window should be opened. If this is not
the case, you might be using a too old Java version (or not have Java installed at all). You can verify this by
opening the terminal application (in the `/Applications/Utilities` directory) and executing `java -version` from the
text prompt. You need version 8 or newer. See below for more information.

----

**Legal disclaimer:**
Mellite contains libraries also released under the GNU AGPL and GPL.
You are entitled to the source code of all these libraries. The licenses of
all libraries are [available here](https://git.iem.at/sciss/Mellite/tree/master/licenses). To
obtain the source code, clone the source code repository of Mellite (see above), and in the file
`build.sbt` add the qualification `withSources()` to any library before running `sbt update`.
For example, to receive the source code of the PDFlitz library used by Mellite, change
`"de.sciss" %% "pdflitz" % pdflitzVersion` to `"de.sciss" %% "pdflitz" % pdflitzVersion withSources()`.
The sources will be downloaded to `~/.ivy2/cache/de.sciss/pdflitz_2.12/srcs`.
If you have trouble obtaining the source code of incorporated libraries, I can send it to you via e-mail
in compliance with the GNU AGPL.

----

In order to run Mellite, you also need to have installed on your computer:

- [Java](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html). The recommended
  version is 8, currently _Java SE Development Kit 8u201_.
  If using OpenJDK, you may also have to install OpenJFX, since the API browser uses JavaFX. In some Linux systems
  (Debian, Ubuntu), you can use `sudo apt install openjdk-8-jdk` and `sudo apt install openjfx` to do so.
  On Raspbian, use Oracle JDK instead because of stability issues.
- [SuperCollider](https://supercollider.github.io/download) (version 3.9.x is recommended, but 3.7.x should work, too)

We have now tested Mellite with JDK 11; it does not provide an installable package for JavaFX, which means the API
browser does not work, and we still recommend JDK 8. However, you should be fine to run Mellite under JDK 11, if you
do not need the API browser.

## Resources

The API docs can be found [here](latest/api/de/sciss/), however the website is very slow. The recommended way to 
browse the API is from within the application itself, using the menu item Help &gt; API Documentation.

The best way to ask questions, no matter if newbie or expert, is to use the [Gitter Channel](https://gitter.im/Sciss/Mellite).
You need a GitLab, GitHub or Twitter account to sign in.
