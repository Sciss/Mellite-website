# Mellite

@@@ index

- [Tutorials](tutorials.md)
- [Links](links.md)

@@@

Mellite is an environment for creating experimental computer-based music and sound art.
It is a desktop application, allowing you to work with
real-time and offline sound synthesis processes, combining multiple perspectives
such as live improvisation, implementing sound installations, or working in DAW-like
timeline views. Mellite runs on all major operating systems and can be used both in
a purely graphical fashion, or by writing and connecting snippets in the Scala programming
language.

![Mellite Screenshot](.../screenshot.png)

Mellite has been developed since 2012 by its author, Hanns Holger Rutz, and is made
available under the GNU Affero General Public License. If you like the software and want
to encourage its continued development and documentation, please support me via [Liberapay](https://liberapay.com/sciss/donate).

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
text prompt. You need version 8 or newer. Often macOS comes with an outdated Java 1.6! See below for more information. 

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

- __Java__ development kit (JDK). The recommended
  version is JDK 8 (JDK 11 should also be fine).
  On Linux, if you install OpenJDK 8, you may also have to install OpenJFX. In some Linux systems
  (Debian, Ubuntu), you can use `sudo apt install openjdk-8-jdk` and `sudo apt install openjfx` to do so.
  On Raspbian, use Oracle JDK instead because of stability issues. For all other platforms, the recommended way
  is to grab an installer from [AdoptOpenJDK](https://adoptopenjdk.net/). It should automatically detect your
  platform (if not, choose 'Other platforms'). Stick to the HotSpot JVM and OpenJDK 8 (or 11).
- [SuperCollider](https://supercollider.github.io/download) (versions 3.10.x and 3.9.x are recommended, but 3.8.x should work, too)

We have now tested Mellite with JDK 11; it does not provide an installable package for JavaFX, which means the API
browser does not work, and we still recommend JDK 8. However, you should be fine to run Mellite under JDK 11, if you
do not need the API browser.

## Resources

For documentation, see the video and text tutorials on the left navigation bar.
The API docs can be found [here](latest/api/de/sciss/), however the website is very slow. The recommended way to 
browse the API is from within the application itself, using the menu item _Help &gt; API Documentation_.

The best way to ask questions, no matter if newbie or expert, is to use the [Gitter Channel](https://gitter.im/Sciss/Mellite).
You need a GitLab, GitHub or Twitter account to sign in.
