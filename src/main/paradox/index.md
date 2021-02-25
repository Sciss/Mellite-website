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
available under the [GNU Affero General Public License](http://www.gnu.org/licenses/agpl-3.0.txt). If you like the software and want
to encourage its continued development and documentation, please support me via [Liberapay](https://liberapay.com/sciss/donate).

## Download and Run

A binary version, ready to run, can be downloaded from [archive.org](https://archive.org/details/Mellite) or
[GitHub releases](https://github.com/Sciss/Mellite/releases/latest).

@@@ note

Before running the program, please read carefully the license terms, and especially the disclaimer also found
at the end of this page—Mellite is an experimental program, and while I do my best to prevent erroneous operation,
I cannot guarantee this program to be error-free, thus you use it fully at your own risk.

@@@

We now provide downloads bundled with a
so-called Java Development Kit (JDK) required to run the application:

- `mellite-full_{version}_win_x64.zip` (for Windows)
- `mellite-full_{version}_mac_x64.zip` (for macOS 10.10 or newer)
- `mellite-full_{version}_linux_x64.zip` (for Linux 64-bit Intel/AMD)
- `mellite-full_{version}_x64.deb` (alternative for Linux if you use Debian or Ubuntu)

Just download and unpack the zip archive and run (open) the file `bin/mellite.bat` (Windows) or `bin/mellite` (Linux and macOS).
If you use the deb package (Debian, Ubuntu), you install it via `sudo dpkg -i mellite-full_{version}_x64.deb`, then you should
be able to find `mellite` on the system and add it to your desktop manager or execute it right away.

On __Mac__, you need to allow the system to launch Mellite the first time you download it. Extract the zip
archive, and go into the `bin` directory. Right-click (or Ctrl-click) on the `mellite` program, then choose `Open`.
The computer will now warn you that the program is not verified, you have to confirm that you really want to open
it. A terminal window should then pop up and soon after the Mellite main window should be opened.

Currently, you may see some messages beginning with _WARNING: An illegal reflective access operation has occurred_ when starting
the application. This is perfectly normal and there is nothing dangerous or broken. I am sorry about these confusing messages, they
come from the look-and-feel library and will be mitigated in future versions.

### Plain Platform Neutral Download

If you do not wish to use the bundled JDK, or if you are using a different platform such as Raspberry Pi or an older
macOS, you can download the "universal" package:

- `mellite_{version}_all.zip`

This is platform neutral, and you must have Java separately installed on your system.
You can verify this by opening a terminal and running `java -version`. On Windows you open the terminal by executing `cmd.exe`
from the task bar, on macOS you find it in `Applications/Utilities`. If `java -version` fails or tells you that your version is older
than 1.8 or 8, then you need to upgrade your Java. The easiest way to do so is visit [adoptopenjdk.net](https://adoptopenjdk.net/)
and download the OpenJDK 11 HotSpot JVM for your system. Please note that the universal zip may have issues finding JavaFX, which means
that the application's built-in API browser may not work.

Legacy macOS systems [may need to look](https://github.com/AdoptOpenJDK/openjdk-support/issues/212) for an archived
AdoptJDK version 8u242 or older.

### Building from Source

If you want to build from the source code, go to [git.iem.at/sciss/Mellite](https://git.iem.at/sciss/Mellite) or
[github.com/Sciss/Mellite](https://github.com/Sciss/Mellite/).
The file `README.md` should give you details on the build process.

## SuperCollider

Mellite uses [SuperCollider](http://supercollider.github.io/) for real-time sound synthesis. If you haven't already done so,
you must separately install SuperCollider from its website. Versions 3.11.x and 3.10.x are recommended, although older versions
should work, too.

## Help and Getting Started

For documentation, see the video and text tutorials on the left navigation bar.
The API docs can be found [here](latest/api/de/sciss/), however the website is very slow. The recommended way to 
browse the API is from within the application itself, using the menu item _Help &gt; API Documentation_.

The best way to ask questions, no matter if newbie or expert, is to use the [Gitter Channel](https://gitter.im/Sciss/Mellite).
You need a GitLab, GitHub or Twitter account to sign in.

## Disclaimer and License Information

@@@ note { title="Disclaimer of Warranty" }

There is no warranty for the program (Mellite), to the extent permitted by
applicable law. Except when otherwise stated in writing the copyright
holders and/or other parties provide the program "as is" without warranty
of any kind, either expressed or implied, including, but not limited to,
the implied warranties of merchantability and fitness for a particular
purpose. The entire risk as to the quality and performance of the program
is with you. Should the program prove defective, you assume the cost of
all necessary servicing, repair or correction.

@@@

@@@ note { title="Limitation of Liability" }

In no event unless required by applicable law or agreed to in writing
will any copyright holder, or any other party who modifies and/or conveys
the program (Mellite) as permitted above, be liable to you for damages, including any
general, special, incidental or consequential damages arising out of the
use or inability to use the program (including but not limited to loss of
data, data being rendered inaccurate, losses sustained by you or third
parties, or a failure of the program to operate with any other programs),
even if such holder or other party has been advised of the possibility of
such damages.

@@@

Mellite contains many libraries covered by Open Source licenses. A comprehensive overview is available inside the application
by selecting the _Help_ > _About Mellite_ menu item and clicking on _License Details_. Libraries covered by the GNU AGPL and GPL
licenses require me to provide you with their source code. You can simply select a library and click on _Download Library Sources_
which takes you to a website hosting the library source code. When using the full package, the bundled OpenJDK is covered
by the GNU GPL with Classpath Exception. See [adoptopenjdk.net/about.html](https://adoptopenjdk.net/about.html) for details.

If you have trouble obtaining the source code of Mellite or its incorporated libraries, contact me via e-mail at _contact @ sciss.de_.
