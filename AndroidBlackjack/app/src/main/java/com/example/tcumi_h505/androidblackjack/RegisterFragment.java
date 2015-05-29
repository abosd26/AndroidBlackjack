package com.example.tcumi_h505.androidblackjack;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class RegisterFragment extends Fragment implements View.OnClickListener{

    public EditText i1, i2, i3, i4;
    public String e;
    public RegisterFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);
        i1 = (EditText)rootView.findViewById(R.id.inputaccount);
        i2 = (EditText)rootView.findViewById(R.id.inputpassword);
        i3 = (EditText)rootView.findViewById(R.id.confirmpassword);
        i4 = (EditText)rootView.findViewById(R.id.inputname);
        Button register = (Button)rootView.findViewById(R.id.register2);
        register.setOnClickListener(this);
        Button clear = (Button)rootView.findViewById(R.id.clear);
        clear.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.register2){
            e = "";
            MyDBHelper dbHelper = new MyDBHelper(this.getActivity());
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.clear();

            e = isEmpty();
            if(!e.equals("\n")){
                Toast.makeText(this.getActivity().getApplicationContext(), e, Toast.LENGTH_SHORT).show();
            }
            else if(isSame()==false){
                Toast.makeText(v.getContext(), "密碼不一致!", Toast.LENGTH_SHORT).show();
            }
            else if(ifExist(i1.getText().toString())){
                Toast.makeText(v.getContext(), "此帳號已存在!!", Toast.LENGTH_SHORT).show();
            }
            else {
                values.put("Name", i4.getText().toString());
                values.put("Email", i1.getText().toString());
                values.put("Password", i2.getText().toString());
                db.insert("SystemUser", null, values);
                Toast.makeText(this.getActivity(), "\n註冊成功\n", Toast.LENGTH_SHORT).show();
                values.clear();
                values.put("Title", "");
                values.put("DeckCount", "");
                values.put("Amount", "");
                values.put("Gamblable", "");
                db.insert("GameSetting", null, values);
            }
            db.close();
            dbHelper.close();

        }
        else if(v.getId() == R.id.clear){
            i1.setText("");
            i2.setText("");
            i3.setText("");
            i4.setText("");
        }
    }
    public boolean ifExist(String account) {
        MyDBHelper dbHelper = new MyDBHelper(this.getActivity());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor =
                db.query("SystemUser", // a. table
                        new String[] {"ID", "Email"}, // b. column names
                        "Email = ?",                          // selections
                        new String[] {account},  // selections args
                        null, // e. group by
                        null, // f. having
                        "ID desc", // g. order by
                        null); // h. limit

        if (cursor != null && cursor.getCount() > 0) {
            db.close();
            dbHelper.close();
            return true;
        }
        db.close();
        dbHelper.close();
        return false;
    }
    public String isEmpty(){
        String error = "\n";
        if(i1.getText().toString().equals("")){
            error += "請輸入帳號\n";
        }
        if(i2.getText().toString().equals("")){
            error += "請輸入密碼\n";
        }
        if(i3.getText().toString().equals("")){
            error += "請確認密碼\n";
        }
        if(i4.getText().toString().equals("")){
            error += "請輸入姓名\n";
        }
        return error;
    }
    public boolean isSame(){
        if(i3.getText().toString().equals(i2.getText().toString())){
            return true;
        }
        return false;
    }
}
