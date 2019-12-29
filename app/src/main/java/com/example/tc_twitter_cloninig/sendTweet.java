package com.example.tc_twitter_cloninig;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.mikepenz.actionitembadge.library.ActionItemBadge;
import com.mikepenz.actionitembadge.library.ActionItemBadgeAdder;
import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class sendTweet extends AppCompatActivity {
    private EditText userTweet;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_tweet);
        userTweet=findViewById(R.id.userTweet);
        listView=findViewById(R.id.listView);


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

    public void userTweets(View v)
    {
        final ArrayList<HashMap<String ,String>>tweetList=new ArrayList<>();
        final SimpleAdapter adapter=new SimpleAdapter(sendTweet.this,tweetList,
                android.R.layout.simple_list_item_2,new String[]{"tweetUserName","tweetValue"},
                new int []{android.R.id.text1,android.R.id.text2});
        try {
            ParseQuery<ParseObject> parseQuery=ParseQuery.getQuery("userTweets");
            parseQuery.whereContainedIn("user",ParseUser.getCurrentUser().getList("followingOf"));
            parseQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if(objects.size()>0 && e==null)
                    {
                        for(ParseObject tweetObjects: objects)
                        {
                            HashMap<String,String> userTweets=new HashMap<>();
                            userTweets.put("tweetUserName",tweetObjects.getString("user"));
                            userTweets.put("tweetValue",tweetObjects.getString("tweets"));
                            tweetList.add(userTweets);


                        }
                        listView.setAdapter(adapter);
                        listView.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.logoutUser)
        {
            final ProgressDialog progressDialog=new ProgressDialog(sendTweet.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            ParseUser.getCurrentUser().logOutInBackground(new LogOutCallback() {
                @Override
                public void done(ParseException e) {
                    if(e==null)
                    {
                        Intent intent=new Intent(sendTweet.this,homePage.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        FancyToast.makeText(sendTweet.this,
                                "Error", Toast.LENGTH_SHORT,
                                FancyToast.ERROR,true).show();
                    }
                    progressDialog.dismiss();
                }
            });


        }

        return super.onOptionsItemSelected(item);
    }


}
