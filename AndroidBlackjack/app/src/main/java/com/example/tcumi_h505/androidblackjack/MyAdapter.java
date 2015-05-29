package com.example.tcumi_h505.androidblackjack;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by tcumi_H505 on 2015/5/26.
 */
public class MyAdapter extends BaseAdapter{
    private Context context;
    private String[] str;
    private static LayoutInflater inflater = null;
    public String ss[] = new String[10];
    public String id;

    public MyAdapter(Context c, String[] s, String id){
        context = c;
        str = s;
        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        for(int i = 0; i < 10; i++){
            ss[i] = "";
        }
        this.id = id;
    }
    @Override
    public int getCount() {
        return str.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;

        view = inflater.inflate(R.layout.setting_list, null);
        TextView tv = (TextView)view.findViewById(R.id.listtitle);
        final EditText e = (EditText)view.findViewById(R.id.listcontent);

        MyDBHelper dbHelper = new MyDBHelper(view.getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor =
                db.query("GameSetting", // a. table
                        new String[] {"Title", "DeckCount", "Amount", "Gamblable"}, // b. column names
                        "ID = ?",                          // selections
                        new String[] {id},  // selections args
                        null, // e. group by
                        null, // f. having
                        "ID desc", // g. order by
                        null); // h. limit

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            e.setText(cursor.getString(position));
            ss[position] = cursor.getString(position);
        }

        db.close();
        dbHelper.close();

        e.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ss[position] = s.toString();
            }
        });

        tv.setText(str[position]);


        return view;
    }
    public String getET(int position){
        return ss[position];
    }
}
