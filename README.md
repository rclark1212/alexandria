# Alexandria

A Udacity android nanodegree application which needs some debugging. Reads/scans ISBN codes to create a database of books.
This application was *not* written by me but instead downloaded for a lesson.

## Installation

First, clone this repo. Second, this project developed with Android Studio 1.3.1 and is set up to support API 15 or later android OS.

You should be able to build and run at this point.

## Usage

Tested on N5, N9. 

Udacity rubric issues solved:
- handle lack of network connectivity
- added bar scanner capability (embedded - does not require download)
- extra error cases found

Other issues seen/resolved/changed:
- added default value for preferences/settings
- eliminated goback button on detail view (against android guidelines)
- cleared info when scanning next book
- fixed rotate in detail view when on phone
- "fix" UI style change between portrait/landscape on tablet (this is really a design decision but think this design works better and 
it also fixes bad back button behavior and losing detail view on rotate). Keep 2 pane in both views on tablet.
- "fix" the back stack behavior of back button. This might be intentional but it does not match google GMS app behavior. And it introduces
confusion (in my opinion) and some UI challenges. Remove the back stack behavior (don't ever go more than 1 deep).
- fix overdraw on detail view when there is no network (have a valid image web url but can't load it)
- fix title bar changing when rotating/suspend. TO BE CLEAR - when nav tray open will be app name. After selection, goes to subfunction name
- preserve 2nd pane visibility through rotate/suspend.
- fix landscape button layout on add book
- Fix unitialized loader in book list view (was resulting in no loader callback updates for when underlying data changed)
- Fix null pointer on empty database when running for first time
- Verified no hardcoded strings
- Fixed layouts to support RtoL languages (start,end)
- Updated about box text.
- Add missing accessibility to search edit box.
- Change nav drawer to top level view only (per google guidelines and photo, newstand examples). Implement
back button for nav drawer on detail view. See navigation below.
- Moved about dialog from nav drawer to options menu
- fix important/valid lint issues
- fix delete bug in phone view (call onBackPressed instead of pop stack)


Potential remaining Work:
- Jacoco is giving a network access warning on UI thread in logcat. This error comes from 3rd party library and looks harmless. Verified that
alexandria itself is not executing network code on UI thread.
- Action bar text is still kind of hacky. Most apps just leave the name of the nav menu function in action bar. Alexandria resets name to app
name when nav drawer is open. As this is sort of a UI designer choice, leaving it as is (but bugs me).
- Search could be improved. Search results only updated when clicking search button. You could make search dynamic on character entry.
- UI itself does the job but is not the most exciting or attractive UI I have seen. Can probably do a good deal more polish here.
- One could remove some unused code (CameraPreview). Left it in.
- Layouts could be further optimized. Did not do this.

## Navigation

This topic deserves its own section. The initial project pushed all screen transactions to the back stack.
I did not like this behavior as when you navigate for a while, it can take a large number of back clicks
to exit the app (and I get lost as to where I am). Looking at other GMS apps like photos, newstand (and 
google UI design documentation), they do not create a back stack on nav drawer selection and they also
recommend only showing nav drawer when on the top level of the app (turn nav drawer into a back button
when going into detail view).


This new scheme has been implemented for alexandria. As part of this change, moved the about box
from the nav drawer options (and put into settings).

## History

First iteration for phase 3 of nanodegree.

## Credits

Original author of code (Sascha Jaschke)
Used 3rd party library for barcode scanning - https://github.com/journeyapps/zxing-android-embedded - Apache 2.0 license
Previous projects for android nanodegree
