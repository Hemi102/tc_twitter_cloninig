package com.example.tc_twitter_cloninig;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


import com.rahman.dialog.Activity.SmartDialog;
import com.rahman.dialog.ListenerCallBack.SmartDialogClickListener;
import com.rahman.dialog.Utilities.SmartDialogBuilder;
import com.shashank.sony.fancytoastlib.FancyToast;

public class signupPage extends AppCompatActivity implements View.OnClickListener{
    private EditText userName,userEmail,password;
    private Button signupBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);

        userName=findViewById(R.id.userName);
        userEmail=findViewById(R.id.userLoginEmail);
        password=findViewById(R.id.loginPassword);
        signupBtn=findViewById(R.id.userLogin);
        signupBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.userLogin:
                if(userName.getText().toString().equals("")||
                        userEmail.getText().toString().equals("")||
                        password.getText().toString().equals("")
                )

                {
                    FancyToast.makeText(this,"Fill complete form",
                            Toast.LENGTH_SHORT,FancyToast.ERROR,true).show();
                }
                else
                {
                    try
                    {
                        userEmail.setError(null);
                        password.setError(null);

                        final ParseUser parseUser=new ParseUser();
                        parseUser.setUsername(userName.getText().toString());
                        parseUser.setEmail(userEmail.getText().toString());
                        parseUser.setPassword(password.getText().toString());
                        final ProgressDialog progressDialog=new ProgressDialog(signupPage.this);
                        progressDialog.setMessage("Loading...");
                        progressDialog.show();
                        parseUser.signUpInBackground(new SignUpCallback() {
                            @Override
                            public void done(ParseException e) {


                                    if (e == null) {
                                        ParseUser.logOut();
                                        alertDisplayer("Account Created Successfully!", "Please verify your email before Login", false);
                                    } else {
                                        ParseUser.logOut();
                                        alertDisplayer("Error Account Creation failed", "Account could not be created" + " :" + e.getMessage(), true);
                                    }

                                progressDialog.dismiss();
                            }
                        });
                    }
                    catch (Exception e)
                    {e.printStackTrace();}
                }

                break;
        }
    }

    private void alertDisplayer(String title,String message,final boolean error)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(signupPage.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        if(!error)
                        {
                            Intent intent=new Intent(signupPage.this,loginPage.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                });
        AlertDialog ok=builder.create();
        ok.show();
    }

}
