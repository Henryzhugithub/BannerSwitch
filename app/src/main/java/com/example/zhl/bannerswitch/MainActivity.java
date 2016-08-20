package com.example.zhl.bannerswitch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private View adVfLayout;
    private AdViewFlipper viewFlipper;
    private LinearLayout adPointLayout;
    private List<String> imgsList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adVfLayout = (View) findViewById(R.id.adVfLayout);
        viewFlipper = (AdViewFlipper) findViewById(R.id.adVf);
        adPointLayout = (LinearLayout) findViewById(R.id.adPoint);
        for (int i=0;i<4;i++){
            String s = new String();
            imgsList.add(s);
        }
        initSwitcher();
    }


    private void initSwitcher(){
        //初始化图片滚动
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setClickable(true);
        viewFlipper.setAutoStart(true);
        if (imgsList==null||imgsList.size()==0){
            adVfLayout.setVisibility(View.GONE);
        }else {
            adVfLayout.setVisibility(View.VISIBLE);
            viewFlipper.setVisibility(View.VISIBLE);
            viewFlipper.setList(imgsList,adPointLayout);
            viewFlipper.setBanner(true);
        }

    }

    public boolean onClick(View v){
        int vid = v.getId();
        if (vid==R.id.adVf){
            int position = viewFlipper.getDisplayedChild();
            Toast.makeText(MainActivity.this, position+"", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}
