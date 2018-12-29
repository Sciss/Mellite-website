# Mellite Website

This repository contains the sources of the [Mellite website](https://www.sciss.de/mellite/) (formerly [GH pages](http://sciss.github.io/Mellite/)).
It's root folder contains an sbt build file for creating the static site, including the Scala API docs.

__To build the API docs locally, run `sbt unidoc`. You can view the results via `open target/scala-2.12/unidoc/index.html`.__

You can run the site via a local web server as `sbt previewSite` which is a functionality of the [sbt-site](https://github.com/sbt/sbt-site) plugin.

## sciss.de

To publish here, prepare the files using `sbt clean packageSite`. The directory `target/site/` contains the stuff
that must be uploaded, i.e. `scp -r target/site/* <credentials>@ssh.strato.de:mellite/`

__TO-DO:__ `rsync` is a smarter option. Use it like the following:

    rsync -rltDvc --delete-after --exclude=.git target/site/ www.sciss.de@ssh.strato.de:mellite/

(add `--dry-run` to check first).

It seems that `packageSite` does not pick up changes, e.g. to `index.md`; better run `previewSite` once.

## GitHub pages

The (obsolete) GitHub pages publication works through 
`sbt ghpagesPushSite` which is provided by the [sbt-ghpages](https://github.com/sbt/sbt-ghpages) plugin.

## Snippets

There is a sub-directory `snippets` which contains another sbt build file. This is the base directory for the code used in the online
tutorials. You may want to import _that sub-directory_ into IntelliJ IDEA. (If you import the root directory, you'll get the build
for the website instead, not the tutorial snippets).

