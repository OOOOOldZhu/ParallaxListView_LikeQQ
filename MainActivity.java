package com.itheima.parallax97;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ParallaxListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview = (ParallaxListView) findViewById(R.id.listview);

        //去掉listview滑动到头的时候边缘的蓝色阴影
        listview.setOverScrollMode(AbsListView.OVER_SCROLL_NEVER);

        //添加header
        View header = View.inflate(this, R.layout.header, null);
        ImageView image = (ImageView) header.findViewById(R.id.image);
        listview.addHeaderView(header);
        //设置ImageView
        listview.setParallaxImage(image);

        listview.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,
                Constant.NAMES));

    }
}
