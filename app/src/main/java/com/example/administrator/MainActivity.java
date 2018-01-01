package com.example.administrator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String mNames[] = {
            "洗衣机","IT","第二",
            "电冰箱","水果","第二第一",
            "电脑","苹果","viewgroup",
            "电磁炉","第二","第二"};
    private ReSouView reSouView;
    private EditText name;
    private Dao dao;
    private ListView lv;
    private ArrayAdapter<String> adapter;
    private List<String> sel;
    private Button btn;
    List<String> a=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reSouView = findViewById(R.id.reSouView);
        //找控件
        name = findViewById(R.id.et_sou);
        //热搜
        initChildViews();
        lv = (ListView) findViewById(R.id.lv);
        btn = (Button) findViewById(R.id.btn);
        dao = new Dao(MainActivity.this);
        sel = dao.sel();
        adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, sel);

        lv.setAdapter(adapter);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int d, long l) {
                AlertDialog.Builder ab=new AlertDialog.Builder(MainActivity.this);
                ab.setTitle("是否删除");
                Log.d("aaa",sel.get(d).toString());
                ab.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int delyi = dao.delyi(sel.get(d).toString());

                        if (delyi==1){
                            zhanshi();
                        }
                    }
                });

                ab.setNegativeButton("取消",null);
                ab.show();
                return false;
            }
        });


        if (sel.size()>0){
            btn.setVisibility(View.VISIBLE);
        }else if(sel.size()==0)
        {
            btn.setVisibility(View.INVISIBLE);
        }


    }

    private void zhanshi() {
        List<String> sel4 = dao.sel();
        ArrayAdapter<String> ada = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, sel4);
        lv.setAdapter(ada);
    }

    private void initChildViews() {
        // TODO Auto-generated method stub
        reSouView = findViewById(R.id.reSouView);
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 5;
        lp.rightMargin = 5;
        lp.topMargin = 5;
        lp.bottomMargin = 5;
        for(int i = 0; i < mNames.length; i ++){
            TextView view = new TextView(this);
            view.setText(mNames[i]);
            view.setTextColor(Color.WHITE);
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.textshape));
            reSouView.addView(view,lp);
        }
    }

    public void add(View view) {
        String n = name.getText().toString();
        int i = dao.insertJson(n);

        btn.setVisibility(View.VISIBLE);

        List<String> sel3 = dao.sel();
        a.add(0,n);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, a);

        lv.setAdapter(adapter3);

    }

    public void delall(View view) {
        dao.del();
        List<String> sel2 = dao.sel();
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, sel2);

        lv.setAdapter(adapter2);

        Toast.makeText(MainActivity.this,"清除成功",Toast.LENGTH_LONG).show();

        btn.setVisibility(View.INVISIBLE);

    }
}
