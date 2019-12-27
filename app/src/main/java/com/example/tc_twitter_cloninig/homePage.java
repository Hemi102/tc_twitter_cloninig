package com.example.tc_twitter_cloninig;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.parse.ParseUser;

public class homePage extends AppCompatActivity implements View.OnClickListener{

    private Button loginBtn,signupBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        loginBtn=findViewById(R.id.userLogin);
        signupBtn=findViewById(R.id.signup);

        loginBtn.setOnClickListener(this);
        signupBtn.setOnClickListener(this);
        if(ParseUser.getCurrentUser()!=null)
        {
            Intent intent=new Intent(homePage.this,welcomeActivity.class);
            startActivity(intent);
            finish();
        }



    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.userLogin:
                transferToLoginActivity();
                break;
            case R.id.signup:
                transferToSignupActivity();
                break;
        }

    }

    private void transferToLoginActivity()
    {
        Intent intent=new Intent(this,loginPage.class);
        startActivity(intent);
    }
    private void transferToSignupActivity()
    {
        Intent intent=new Intent(this,signupPage.class);
        startActivity(intent);

    }

}
