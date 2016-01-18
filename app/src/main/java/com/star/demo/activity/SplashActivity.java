package com.star.demo.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.star.demo.R;
import com.star.demo.util.BitmapUtil;
import com.star.demo.util.cache.ACache;
import com.star.demo.util.constant.Constant;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Sym on 2016/1/18.
 */
public class SplashActivity extends AppCompatActivity {

    @Bind(R.id.splash_iv)
    ImageView splashIv;

    private ACache aCache;
    private Bitmap splashBitmap;
    private int screenWidth, screenHeight;
    private Handler handler = new Handler(){};
    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        this.init();
    }

    private void init() {
        aCache = ACache.get(this);
        screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        screenHeight = getWindowManager().getDefaultDisplay().getHeight();
        //Log.e(TAG, "screen width: " + screenWidth);
        //Log.e(TAG, "screen height: " + screenHeight);
        //Log.e(TAG, "imageview width: " + splashIv.getWidth());
        //Log.e(TAG, "imageview height: " + splashIv.getHeight());
        //Log.e(TAG, "status bar height: " + getStatusBarHeight());
        splashBitmap = BitmapUtil.resizeBitmap(screenWidth, screenHeight - getStatusBarHeight(),
                BitmapFactory.decodeResource(getResources(), R.mipmap.splash));
        splashIv.setImageBitmap(splashBitmap);
        this.doJump();
    }

    private void doJump() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String gesturePassword = aCache.getAsString(Constant.GESTURE_PASSWORD);
                if(gesturePassword == null || "".equals(gesturePassword)) {
                    Intent intent = new Intent(SplashActivity.this, CreateGestureActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashActivity.this, GestureLoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2000);
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
