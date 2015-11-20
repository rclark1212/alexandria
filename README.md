# Alexandria

A Udacity android nanodegree application which needs some debugging. Reads/scans ISBN codes to create a database of books.
This application was *not* written by me but instead downloaded for a lesson.

## Installation

First, clone this repo. Second, this project developed with Android Studio 1.3.1 and is set up to support API 15 or later android OS.

You should be able to build and run at this point.

## Usage

Rubric issues solved:
- handle lack of network connectivity
- added bar scanner capability (embedded - does not require download)

Other issues seen:
- added default value for preferences/settings
- eliminated goback button on detail view (against android guidelines)
- cleared info when scanning again
- fixed rotate in detail view when on phone

ToDo: on tablet, rotate or lock destroys detail view of book.
ToDo: selection from book list (second selection), causes about menu to come up (or other problems)
ToDo: network warning on UI thread in logcat on executing
ToDo: landscape book selection when no internet has overdraw
ToDo: phone. title bar does not change when going back from about this app using back key
ToDo: phone. When rotating, title bar does change (even though screen stays correct)
ToDo: back key is shown on detail view for phone
ToDo: no cancel/next in landscape mode when adding book
Check: see if with an account share does anything.
ToDo: search does not scale with character entry (i.e. search for something unique, backspace to null - elements don't show up until re-search)



## History

First iteration for phase 3 of nanodegree.

## Credits

Original author of code (Sascha Jaschke)
Used 3rd party library for barcode scanning - https://github.com/journeyapps/zxing-android-embedded - Apache 2.0 license
Previous projects for android nanodegree
