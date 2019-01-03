package com.example.lamine.bluetooth_20;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME = 5000; //This is 4 seconds
    Typeface tf;
    //TextView text;
    ImageView img1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //text = (TextView)findViewById(R.id.text1);
        img1 = (ImageView)findViewById(R.id.imageView);

        //tf = Typeface.createFromAsset(getAssets(),"AlexBrush-Regular.ttf");
        //text.setTypeface(tf);
        Animation anima = AnimationUtils.loadAnimation(this,R.anim.mytransition);
        //text.startAnimation(anima);
        img1.startAnimation(anima);

        final Intent i = new Intent(this,Main2Activity.class);
        Thread timer = new Thread(){
            public void run (){
                try{
                    sleep(5000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();
                }
            }
        };
                timer.start();
    }
}
