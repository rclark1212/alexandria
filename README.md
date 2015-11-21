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

- "fix" UI style change between portrait/landscape on tablet (this is really a design decision but think this design works better and 
it also fixes bad back button behavior and losing detail view on rotate).
- "fix" the back stack behavior of back button. This might be intentional but it does not match google GMS app behavior. And it introduces
confusion (in my opinion) and some UI challenges. Remove the back stack behavior (don't ever go more than 2 deep).
- fix overdraw on detail view when there is no network (have a valid image web url but can't load it)
- fix title bar changing when rotating/suspend. TO BE CLEAR - when nav tray open will be app name. After selection, goes to subfunction name
- preserve 2nd pane visibility through rotate/suspend.
- fix landscape button layout on add book


ToDo: delete button does not work in tablet
ToDo: network warning on UI thread in logcat on executing
ToDo: phone. When rotating, title bar does change (even though screen stays correct)
ToDo: no cancel/next in landscape mode when adding book
Check: see if with an account share does anything.
ToDo: search does not update with character entry (i.e. search for something unique, backspace to null - elements don't show up until re-search)



## History

First iteration for phase 3 of nanodegree.

## Credits

Original author of code (Sascha Jaschke)
Used 3rd party library for barcode scanning - https://github.com/journeyapps/zxing-android-embedded - Apache 2.0 license
Previous projects for android nanodegree
