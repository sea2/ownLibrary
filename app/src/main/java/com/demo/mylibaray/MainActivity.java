package com.demo.mylibaray;

import android.app.Activity;
import android.os.Bundle;

import com.sea.library.view.RoundAngleImageView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RoundAngleImageView tv_info = findViewById(R.id.iv_info);
        tv_info.setImageResource(R.drawable.img_game_bar_default);
    }
}
