package com.cloth.clothes.splash;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cloth.clothes.R;
import com.cloth.clothes.home.HomeActivity;
import com.cloth.clothes.login.LoginActivity;
import com.cloth.clothes.role.RoleActivity;
import com.cloth.kernel.service.LcRouterWrapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

@Route(path = SplashActivity.PATH)
public class SplashActivity extends AppCompatActivity {
    public static final String  PATH = "/main/splash";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        LoginActivity.jump();


        finish();
    }
}
