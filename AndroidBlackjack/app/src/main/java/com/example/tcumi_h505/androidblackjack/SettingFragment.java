package com.example.tcumi_h505.androidblackjack;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment implements View.OnClickListener{

    public String[] t = {"主題名稱: ", "牌組數量: ", "玩家初始金額: ", "是否可投注(請輸入1或0): "};
    public ListView listView;
    public MyAdapter myAdapter;
    public Button b1;
    public String id;
    public String s[] = new String[5];
    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_setting, container, false);
        MainActivity parent = (MainActivity)this.getActivity();
        this.id = parent.giveId();

        listView = (ListView)rootView.findViewById(R.id.listView);
        myAdapter = new MyAdapter(this.getActivity(), t, id);
        listView.setAdapter(myAdapter);
        Button b1 = (Button)rootView.findViewById(R.id.save);
        b1.setOnClickListener(this);

        return rootView;
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.save){
            MyDBHelper dbHelper = new MyDBHelper(this.getActivity());
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            s[0] = myAdapter.getET(0);
            for(int i = 0; i < 4; i++){
                s[i] = myAdapter.getET(i);
            }
            String e = isEmpty();
            if(!e.equals("\n")){
                Toast.makeText(this.getActivity(), e, Toast.LENGTH_SHORT).show();
            }
            else if(!s[3].equals("1") && !s[3].equals("0")){
                Toast.makeText(this.getActivity(), "\n是否可投注：請輸入0或1\n", Toast.LENGTH_SHORT).show();
            }
            else if(!isNumber()){
                Toast.makeText(this.getActivity(), "\n牌組數量/玩家初始金額請輸入大於0的數字\n", Toast.LENGTH_SHORT).show();
            }
            else{
                ContentValues values = new ContentValues();
                values.clear();

                values.put("Title", s[0]);
                values.put("DeckCount", s[1]);
                values.put("Amount", s[2]);
                values.put("Gamblable", s[3]);
                db.update("GameSetting", values, "ID = ?", new String[]{id});
                Toast.makeText(this.getActivity(), "\n保存成功\n", Toast.LENGTH_SHORT).show();
            }
            db.close();
            dbHelper.close();
        }
    }

    public String isEmpty(){
        String error = "\n";
        if(s[0].equals("")){
            error += "請輸入主題名稱\n";
        }
        if(s[1].equals("")){
            error += "請輸入牌組數量\n";
        }
        if(s[2].equals("")){
            error += "請輸入玩家初始金額\n";
        }
        if(s[3].equals("")){
            error += "請輸入是否可投注\n";
        }
        return error;
    }
    public boolean isNumber(){
        try{
            int a = Integer.parseInt(s[1]);
            int b = Integer.parseInt(s[2]);
            if(a > 0 && b > 0){
                return true;
            }
            return false;
        }
        catch(Exception e){
            return false;
        }
    }
}
