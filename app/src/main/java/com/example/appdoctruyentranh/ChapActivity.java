package com.example.appdoctruyentranh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.appdoctruyentranh.adapter.ChapTruyenAdapter;
import com.example.appdoctruyentranh.adapter.TruyenTranhAdapter;
import com.example.appdoctruyentranh.api.ApiChapTruyen;
import com.example.appdoctruyentranh.interfaces.LayChapVe;
import com.example.appdoctruyentranh.object.ChapTruyen;
import com.example.appdoctruyentranh.object.TruyenTranh;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChapActivity extends AppCompatActivity implements LayChapVe {
        TextView txvTenTruyens;
        ImageView imgAnhTruyens;
        TruyenTranh truyenTranh;
        ListView lsvDanhSachChap;
        ArrayList<ChapTruyen> arrChap;
        ChapTruyenAdapter chapTruyenAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chap);
        init();
        anhXa();
        setUp();
        setClick();
        new ApiChapTruyen(this,truyenTranh.getId()).execute();
    }
    private void init(){
        Bundle b = getIntent().getBundleExtra("data");
        truyenTranh =(TruyenTranh) b.getSerializable("truyen");
        arrChap = new ArrayList<>();



    }
    private void anhXa(){
        txvTenTruyens = findViewById(R.id.txvTenTruyens);
        imgAnhTruyens = findViewById(R.id.imgAnhTruyens);
        lsvDanhSachChap = findViewById(R.id.lsvDanhSachChap);
    }
    private void setUp(){
        txvTenTruyens.setText(truyenTranh.getTenTruyen());
        Glide.with(this).load(truyenTranh.getLinkAnh()).into(imgAnhTruyens);

    }
    private void setClick(){
        lsvDanhSachChap.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle b = new Bundle();
                b.putString("idChap",arrChap.get(i).getId());
                Intent intent = new Intent(ChapActivity.this,DocTruyenActivity.class);
                intent.putExtra("data",b);
                startActivity(intent);

            }
        });
    }

    @Override
    public void batDau() {
        Toast.makeText(this,"lay chap ve",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ketThuc(String data) {
        try {

            JSONArray array = new JSONArray(data);
            for (int i = 0;i<array.length();i++){
                JSONObject o = array.getJSONObject(i);
                ChapTruyen chapTruyen = new ChapTruyen(array.getJSONObject(i));

                arrChap.add(chapTruyen);
            }
            chapTruyenAdapter = new ChapTruyenAdapter(this,0,arrChap);
            lsvDanhSachChap.setAdapter(chapTruyenAdapter);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void biLoi() {

    }
}