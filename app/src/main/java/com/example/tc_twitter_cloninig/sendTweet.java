package com.example.tc_twitter_cloninig;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class sendTweet extends AppCompatActivity {
    private EditText userTweet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_tweet);
        userTweet=findViewById(R.id.userTweet);


    }

    public void userTweetBtn(View v)
    {

        if(userTweet.getText().toString()=="")
        {
            FancyToast.makeText(sendTweet.this,"Please input tweet",
                    Toast.LENGTH_SHORT,FancyToast.ERROR,true).show();

        }
        else
        {
            ParseObject parseObject=new ParseObject("userTweets");
            parseObject.put("tweets",userTweet.getText().toString());
            parseObject.put("user", ParseUser.getCurrentUser().getUsername());
            final ProgressDialog progressDialog=new ProgressDialog(sendTweet.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            parseObject.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if(e==null)
                    {
                        FancyToast.makeText(sendTweet.this,"Success",
                                Toast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();
                    }
                    else
                    {
                        FancyToast.makeText(sendTweet.this,"Error",
                                Toast.LENGTH_SHORT,FancyToast.ERROR,true).show();
                    }
                    progressDialog.dismiss();
                }
            });
        }
    }
}
