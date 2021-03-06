package it.jaschke.alexandria;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import it.jaschke.alexandria.api.Callback;


public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks, Callback {

    public static final String ARG_2ND_VISIBLE = "panevisible";
    private int b2paneVisible = 1;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment navigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence title;
    public static boolean IS_TABLET = false;
    private BroadcastReceiver messageReciever;

    public static final String MESSAGE_EVENT = "MESSAGE_EVENT";
    public static final String MESSAGE_KEY = "MESSAGE_EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IS_TABLET = isTablet();
        if(IS_TABLET){
            setContentView(R.layout.activity_main_tablet);
        }else {
            setContentView(R.layout.activity_main);
        }

        messageReciever = new MessageReciever();
        IntentFilter filter = new IntentFilter(MESSAGE_EVENT);
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReciever,filter);

        navigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        navigationDrawerFragment.setUp(R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        //FIX - preserve title between rotates/suspend. Note that when tray is open, it will go to
        //alexandria. Sort of tacky but will leave this feature.
        //title = getTitle();
        //FIX - restore the hidden/shown state of second pane
        if (savedInstanceState != null) {
            int vis = savedInstanceState.getInt(ARG_2ND_VISIBLE);
            if (vis != 0)
                setRightViewVisible(true);
            else
                setRightViewVisible(false);
        }
    }


    @Override
    public void onNavigationDrawerItemSelected(int position) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment nextFragment;

        //FIX - change behavior of navigation for the phone case...
        //TODO - describe behavior

        switch (position){
            default:
            case 0:
                nextFragment = new ListOfBooks();
                //FIX - when adding or scanning a book, show the detail view...
                setRightViewVisible(true);
                break;
            case 1:
                nextFragment = new AddBook();
                //FIX - when adding or scanning a book, hide the detail view...
                setRightViewVisible(false);
                break;
            /*
            case 2:
                nextFragment = new About();
                //FIX - when adding or scanning a book, hide the detail view...
                setRightViewVisible(false);
                break; */

        }

        //FIX - change behavior. Take out the back stack store. Makes a confusing UI and is different
        //than other android app behavior... See top of routine for reasoning...
        fragmentManager.beginTransaction()
                .replace(R.id.container, nextFragment)
                //.addToBackStack((String) title)
                .commit();
    }

    private void setRightViewVisible(boolean bVisible) {
        View vw = findViewById(R.id.right_container);

        if (vw != null) {
            if (bVisible) {
                vw.setVisibility(View.VISIBLE);
                b2paneVisible = 1;
            } else {
                vw.setVisibility(View.INVISIBLE);
                b2paneVisible = 0;
            }
        }
    }

    public void setTitle(int titleId) {
        title = getString(titleId);
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(title);
    }

    //FIX - add an activity result for scanner...
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            // handle scan result
            EditText ean = (EditText) findViewById(R.id.ean);
            if(ean != null) {
                ean.setText(scanResult.getContents());
            }
            //Toast.makeText(getApplicationContext(),scanResult.getContents(),Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!navigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if (id == R.id.action_about) {
            //make about a dialog
            AlertDialog.Builder about = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            //lint will warn on passing null but is actually what I want to do for centering dialog on both phone and tablet views
            View aboutview = inflater.inflate(R.layout.fragment_about, null);   //ignore lint warning here
            about.setView(aboutview);
            about.create().show();
        } else if (id == android.R.id.home) {
            //handle the up carat
            if (navigationDrawerFragment.isDrawerHomeCarat()) {
                onBackPressed();
                return true;        //handled the selection (and keeps nav bar from showing sliding out again)
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(messageReciever);
        super.onDestroy();
    }

    @Override
    public void onItemSelected(String ean) {
        Bundle args = new Bundle();
        args.putString(BookDetail.EAN_KEY, ean);

        BookDetail fragment = new BookDetail();
        fragment.setArguments(args);

        int id = R.id.container;
        if(findViewById(R.id.right_container) != null){
            id = R.id.right_container;
        }

        //FIX - remove the back stack saving for tabet...
        if (IS_TABLET) {
            getSupportFragmentManager().beginTransaction()
                    .replace(id, fragment)
                    .commit();

            //and make sure to make the second fragment visible if on tablet...
            setRightViewVisible(true);
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(id, fragment)
                    .addToBackStack("BookList")      //note - no need to translate this. is internal only.
                    .commit();

            //Hide the nav bar (just like photos, newstand)
            navigationDrawerFragment.enableDrawer(false);
        }

    }

    private class MessageReciever extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getStringExtra(MESSAGE_KEY)!=null){
                //This toast simply executes the book not found message.
                Toast.makeText(MainActivity.this, intent.getStringExtra(MESSAGE_KEY), Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean isTablet() {
        return (getApplicationContext().getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    @Override
    public void onBackPressed() {
        //FIX - can remove below lines without a back stack...
        //if(getSupportFragmentManager().getBackStackEntryCount()<2){
        //    finish();
        //}
        super.onBackPressed();

        //restore nav fragment view - maybe leave disabled and onresume in fragments, enable?
        if (getFragmentManager().getBackStackEntryCount() == 0) {        //back at root
            navigationDrawerFragment.enableDrawer(true);
            //make sure drawer stays closed
            navigationDrawerFragment.closeDrawer();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Save whether second view is visible or not...
        outState.putInt(ARG_2ND_VISIBLE, b2paneVisible);
        super.onSaveInstanceState(outState);
    }

}