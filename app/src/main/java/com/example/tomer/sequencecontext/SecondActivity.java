package com.example.tomer.sequencecontext;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

public class SecondActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnCreateContextMenuListener {
    Intent t;
    TextView tv1;
    double a1;
    double d_q;
    boolean s;
    String[] seq = new String[20];
    ListView lv;
    double sum,n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        tv1 = (TextView)findViewById(R.id.tv1);
        lv = (ListView)findViewById(R.id.lv);
        if(getIntent()!=null){
            t = getIntent();
            s = t.getExtras().getBoolean("s");
            a1=t.getExtras().getDouble("first",9.99997);
            d_q=t.getExtras().getDouble("d/q",9.99997);
            seq[0]=Double.toString(a1);
            if (s){
                for(int i=1;i<20;i++){
                    seq[i] = Double.toString(Double.parseDouble(seq[i-1])+d_q);
                }
            }
            else{
                for(int i=1;i<20;i++){
                    seq[i] = Double.toString(Double.parseDouble(seq[i-1])*d_q);
                }
            }
        }
        lv.setOnItemClickListener(this);
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        ArrayAdapter<String> adp = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, seq);
        lv.setAdapter(adp);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        sum = 0;
        n = i+1;
        adapterView.setOnCreateContextMenuListener(this);
        openContextMenu(adapterView);
        adapterView.setOnCreateContextMenuListener(null);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Element stats");
        menu.add("a1");
        menu.add("n");
        menu.add("d/q");
        menu.add("Sn");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        String st = "";
        st = item.getTitle().toString();
        if(st=="a1")
            tv1.setText(Double.toString(a1));
        if(st=="n")
            tv1.setText(Double.toString(n));
        if(st=="d/q")
            tv1.setText(Double.toString(d_q));
        if (st=="Sn") {
            if(s){
                sum = ((n*((2*a1)+d_q*(n-1)))/2);
            }
            else {
                if(d_q==1)
                    sum = a1*n;
                else
                    sum = ((a1 * ((Math.pow(d_q, n)) - 1)) / (d_q - 1));
            }
            tv1.setText(Double.toString(sum));
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String st = item.getTitle().toString();
        if(st.equals("Credits"))
            Toast.makeText(this, "This app was created by Tomer Ben Ari", Toast.LENGTH_LONG).show();
        return super.onOptionsItemSelected(item);
    }

    public void back(View view) {
        Intent b = new Intent(this, MainActivity.class);
        startActivity(b);
    }
}
