package com.example.tc_twitter_cloninig;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import javax.xml.parsers.FactoryConfigurationError;

public class loginPage extends AppCompatActivity {
    private EditText userEmail,userPassword;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        userEmail=findViewById(R.id.userLoginEmail);
        userPassword=findViewById(R.id.loginPassword);
        loginBtn=findViewById(R.id.userLogin);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                (userPassword).setError(null);
                final ProgressDialog progressDialog=new ProgressDialog(loginPage.this);
                progressDialog.setMessage("Loading...");
                progressDialog.show();
                ParseUser.logInInBackground(userEmail.getText().toString(),
                        userPassword.getText().toString(),
                        new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e) {
                                if(user!=null && e==null)
                                {


                                        alertDisplayer("Login Sucessful", "Welcome, " + user.getUsername()+ "!", false);


                                }
                                else
                                {
                                    FancyToast.makeText(loginPage.this,
                                            e.getMessage()+"",
                                            Toast.LENGTH_SHORT,
                                            FancyToast.ERROR,true).show();
                                }
                                progressDialog.dismiss();
                            }
                        });

            }
        });
    }
    private void alertDisplayer(String title,String name,final boolean error)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(name)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        if(!error)
                        {
                            Intent intent=new Intent(loginPage.this,welcomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        }
                    }
                });
        AlertDialog ok=builder.create();
        ok.show();
    }
}
