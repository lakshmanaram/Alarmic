package com.pyassasins.alarmic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lakshmanaram on 2/10/15.
 */
public class ContactsHandler {

    Context context;
    ContactsHelper contactsHelper;
    ArrayList<Cont> contactslist = new ArrayList<>();

    public ContactsHandler(Context context){
        this.context = context;
        contactsHelper = new ContactsHelper(context);
    }

    public ArrayList<Cont> getAuthenticatedcontacts(int auth){

        SQLiteDatabase sqLiteDatabase = contactsHelper.getReadableDatabase();
        if(!contactslist.isEmpty())
            contactslist.clear();
        String[] columns={ContactsHelper.CONTACTNAME,ContactsHelper.CONTACTPHONE,ContactsHelper.AUTHENTICATED};
        Cursor cv = sqLiteDatabase.query(ContactsHelper.TABLENAME, columns,ContactsHelper.AUTHENTICATED + " =?",new String[]{String.valueOf(auth)}, null, null, null);
        int index1 = cv.getColumnIndex(ContactsHelper.CONTACTNAME);
        int index2 = cv.getColumnIndex(ContactsHelper.CONTACTPHONE);
        int index3 = cv.getColumnIndex(ContactsHelper.AUTHENTICATED);
        while(cv.moveToNext()) {
            String name = cv.getString(index1);
            String phone = cv.getString(index2);
            int authenticated = cv.getInt(index3);
            if(name!=null||phone!=null) {
                Cont temp = new Cont(name,phone,authenticated);
                contactslist.add(temp);
            }
        }
        cv.close();
        return contactslist;
    }

    public boolean updateauthentication(String phone, int auth){                                            //0 for non-existing user, 1 for authenticated user, -1 for blocked user
        SQLiteDatabase db = contactsHelper.getWritableDatabase();
        //UPDATE attendance SET authentication = auth WHERE PHONENO = phone;
        ContentValues cv = new ContentValues();
        cv.put(ContactsHelper.AUTHENTICATED, auth);
        int a = db.update(ContactsHelper.TABLENAME,cv,"("+ContactsHelper.CONTACTPHONE+ "=?)",new String[]{phone});
        if (a>0)
            return true;
        else
            return false;
    }

    public ArrayList<Cont> getContactslist(){
        SQLiteDatabase sqLiteDatabase = contactsHelper.getReadableDatabase();
        if(!contactslist.isEmpty())
            contactslist.clear();
        String[] columns={ContactsHelper.CONTACTNAME,ContactsHelper.CONTACTPHONE,ContactsHelper.AUTHENTICATED};
        Cursor cv = sqLiteDatabase.query(ContactsHelper.TABLENAME, columns,null,null, null, null, null);
        int index1 = cv.getColumnIndex(ContactsHelper.CONTACTNAME);
        int index2 = cv.getColumnIndex(ContactsHelper.CONTACTPHONE);
        int index3 = cv.getColumnIndex(ContactsHelper.AUTHENTICATED);
        while(cv.moveToNext()) {
            String name = cv.getString(index1);
            String phone = cv.getString(index2);
            int authenticated = cv.getInt(index3);
            if(name!=null||phone!=null) {
                Cont temp = new Cont(name,phone,authenticated);
                contactslist.add(temp);
            }
        }
        cv.close();
        return contactslist;
    }

    public void addcontacts(ArrayList<Cont> contacts){               //to add attendance
        SQLiteDatabase sqLiteDatabase = contactsHelper.getWritableDatabase();
        for(int i=0;i<contacts.size();i++)
        {
            Cont temp = contacts.get(i);
            ContentValues cv = new ContentValues();
            cv.put(ContactsHelper.CONTACTNAME,temp.getName());
            cv.put(ContactsHelper.CONTACTPHONE,temp.getPhoneno());
            cv.put(ContactsHelper.AUTHENTICATED,temp.getAuthenticated());
            updateauthentication("14323141341",1);
            sqLiteDatabase.insert(ContactsHelper.TABLENAME,ContactsHelper.CONTACTNAME,cv);
        }
    }

    private class ContactsHelper extends SQLiteOpenHelper{

        public static final int VERSION=1;
        public static final String DBNAME="conts.db";
        public static final String TABLENAME="CONTACTS";
        public static final String CONTACTNAME="name";
        public static final String CONTACTPHONE="phone";
        public static final String AUTHENTICATED="auth";
        public ContactsHelper(Context context) {
            super(context, DBNAME, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String query="CREATE TABLE "+TABLENAME+"(" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    CONTACTNAME+" TEXT," +
                    CONTACTPHONE+" TEXT," +
                    AUTHENTICATED+" INTEGER" +
                    ");";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
            onCreate(db);
        }

    }

}
