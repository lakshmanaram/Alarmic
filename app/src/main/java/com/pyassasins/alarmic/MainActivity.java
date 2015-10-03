package com.pyassasins.alarmic;




import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.content.ContentResolver;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bowyer.app.fabtransitionlayout.BottomSheetLayout;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = "HOME";
                break;
            case 2:
                mTitle = "My Alarms";
                break;
            case 3:
                mTitle = "Contacts";
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        BottomSheetLayout mBottomSheetLayout;
        static int id=0;
        ArrayList<Cont> contactslist = new ArrayList<>();                                           //contacts

        ContactsHandler contactsHandler;
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {



            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            switch (id){
                case 1:
                    rootView=inflater.inflate(R.layout.activity_home,container,false);

                    final HomeSqlHandler handler=new HomeSqlHandler(getActivity());
                    ListView list=(ListView)rootView. findViewById(R.id.homelist);
                    Alarmy[] alarms= handler.getAlarmInfo();
                    List<Alarmy> al=new ArrayList<>();
                    for(Alarmy i : alarms){
                        if(i.getHide()==0){
                            al.add(i);
                        }
                    }
                    Alarmy[] h=new Alarmy[al.size()];
                    h=al.toArray(h);

                    HomeListAdapter adapter=new HomeListAdapter(getActivity(),h);
                    list.setAdapter(adapter);
                    ListView listView = (ListView)rootView. findViewById(R.id.list_menu);
                    String[] menu = {"Text","Picture","Video"};
                    ListMenuAdapter lmadapter = new ListMenuAdapter(getActivity(),menu);
                    listView.setAdapter(lmadapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if(position==0){
                                Intent intent = new Intent(getActivity(),SetTextAlarm.class);
                                startActivity(intent);
                                //todo: toast to say that the alarm has been set.
                                mBottomSheetLayout.slideInFab();
                            }
                            else if(position==1){

                            }
                            else if (position==2){

                            }

                        }
                    });
                    FloatingActionButton fab=(FloatingActionButton)rootView. findViewById(R.id.floating);
                    mBottomSheetLayout = (BottomSheetLayout)rootView. findViewById(R.id.bottom_sheet);
                    mBottomSheetLayout.setFab(fab);
                    fab.setOnClickListener(
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    mBottomSheetLayout.expandFab();
                                    //TODO: Clock thingy
                                }
                            }
                    );
                    break;
                case 2:
                    //myalarms
                    break;
                case 3:
                    rootView=inflater.inflate(R.layout.activity_contacts,container,false);
                    contactsHandler = new ContactsHandler(getActivity());
                    if(contactsHandler.getContactslist().isEmpty()){                                                                        //only first time.
                        ContentResolver cr =getActivity(). getContentResolver();
                        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                                null, null, null, null);
                        if (cur.getCount() > 0) {
                            while (cur.moveToNext()) {
                                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                                if (Integer.parseInt(cur.getString(
                                        cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                                    Cursor pCur = cr.query(
                                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                            null,
                                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                                            new String[]{id}, null);
                                    while (pCur.moveToNext()) {
                                        String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                        Cont temp = new Cont(name,phoneNo,0);
                                        contactslist.add(temp);
//                        Toast.makeText(this, "Name: " + name + ", Phone No: " + phoneNo, Toast.LENGTH_SHORT).show();
                                    }
                                    pCur.close();
                                }
                            }
                        }
                        cur.close();
                        contactsHandler.addcontacts(contactslist);
                    }

                    ArrayList<Cont> cl = contactsHandler.getContactslist();
                    ListView contactslist = (ListView)rootView. findViewById(R.id.contactlist);
                    ContactsListAdapter cladapter = new ContactsListAdapter(getActivity(), cl);
                    contactslist.setAdapter(cladapter);
                    break;
            }

            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
            id = getArguments().getInt(ARG_SECTION_NUMBER);
        }
    }

}
