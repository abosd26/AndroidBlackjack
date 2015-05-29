package com.example.tcumi_h505.androidblackjack;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    public int pageNo = 0;
    public static String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }
    public void switchFragment(){
        Fragment newFragment = null;
        if(pageNo == 0){
            newFragment = new PlaceholderFragment();
        }
        else if(pageNo == 1){
            newFragment = new SettingFragment();
        }
        else if(pageNo == 2){
            newFragment = new RegisterFragment();
        }
        if(newFragment == null){
            return;
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public String giveId(){
        return id;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener{

        public EditText t1, t2;
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            t1 = (EditText)rootView.findViewById(R.id.account);
            t2 = (EditText)rootView.findViewById(R.id.password);
            Button login = (Button)rootView.findViewById(R.id.login);
            Button register = (Button)rootView.findViewById(R.id.register);
            login.setOnClickListener(this);
            register.setOnClickListener(this);
            MyDBHelper dbHelper = new MyDBHelper(this.getActivity());
            dbHelper.getWritableDatabase();
            return rootView;
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.login){
                String e = isEmpty(t1.getText().toString(), t2.getText().toString());
                if(!e.equals("\n")){
                    Toast.makeText(v.getContext(), e, Toast.LENGTH_SHORT).show();
                }
                else if(!isValidate(t1.getText().toString(), t2.getText().toString())){
                    Toast.makeText(v.getContext(), "\n帳號／密碼錯誤!\n", Toast.LENGTH_SHORT).show();
                }
                else {
                    MainActivity parent = (MainActivity) this.getActivity();
                    parent.pageNo = 1;
                    parent.switchFragment();
                }
            }
            else if(v.getId() == R.id.register){
                MainActivity parent = (MainActivity)this.getActivity();
                parent.pageNo = 2;
                parent.switchFragment();
            }
        }
        public boolean isValidate(String account, String pw){
            MyDBHelper dbHelper = new MyDBHelper(this.getActivity());
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor =
                    db.query("SystemUser", // a. table
                            new String[] {"ID", "Email"}, // b. column names
                            "Email = ? and Password = ?",                      // selections
                            new String[] {account, pw},  // selections args
                            null, // e. group by
                            null, // f. having
                            "ID desc", // g. order by
                            null); // h. limit

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                id = cursor.getString(0);
                db.close();
                dbHelper.close();
                return true;
            }
            db.close();
            dbHelper.close();
            return false;
        }
        public String isEmpty(String a, String p){
            String e = "\n";
            if(a.equals("")){
                e += "請輸入帳號\n";
            }
            if(p.equals("")){
                e += "請輸入密碼\n";
            }
            return e;
        }
    }
}
