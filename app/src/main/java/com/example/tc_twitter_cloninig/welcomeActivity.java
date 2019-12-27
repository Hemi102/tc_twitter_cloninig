package com.example.tc_twitter_cloninig;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class welcomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView userList;
    private ArrayList<String> userArray;
    private ArrayAdapter arrayAdapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        userList =findViewById(R.id.userList);
        userArray=new ArrayList();
        arrayAdapter=new ArrayAdapter(welcomeActivity.this,android.R.layout.simple_list_item_checked,userArray);
        userList.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        userList.setOnItemClickListener(this);

        try
        {
            final ParseQuery<ParseUser> parseQuery=ParseUser.getQuery();
            parseQuery.whereNotEqualTo("email",ParseUser.getCurrentUser().getEmail());
            progressDialog=new ProgressDialog(welcomeActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            parseQuery.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {
                    if(e==null)
                    {
                        if(objects.size()>0)
                        {
                            for(ParseUser user:objects)
                            {
                                userArray.add(user.getUsername());

                            }
                            userList.setAdapter(arrayAdapter);
                            userList.setVisibility(View.VISIBLE);
                        }
                    }
                    else
                    {
                        FancyToast.makeText(welcomeActivity.this,e.getMessage()+"",
                                Toast.LENGTH_SHORT,FancyToast.ERROR,true).show();

                    }
                    if(ParseUser.getCurrentUser().getList("followingOf")!=null)
                    {
                        for(String users: userArray)
                        {
                            if(ParseUser.getCurrentUser().getList("followingOf").contains(users))
                            {
                                userList.setItemChecked(userArray.indexOf(users),true);
                            }
                        }
                    }
                    progressDialog.dismiss();
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
            final ProgressDialog progressDialog=new ProgressDialog(welcomeActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            ParseUser.getCurrentUser().logOutInBackground(new LogOutCallback() {
                @Override
                public void done(ParseException e) {
                    if(e==null)
                    {
                        Intent intent=new Intent(welcomeActivity.this,homePage.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        FancyToast.makeText(welcomeActivity.this,
                                "Error", Toast.LENGTH_SHORT,
                                FancyToast.ERROR,true).show();
                    }
                    progressDialog.dismiss();
                }
            });


        }
        if(item.getItemId()==R.id.sendTweet)
        {
            Intent intent=new Intent(welcomeActivity.this,sendTweet.class);
            startActivity(intent);


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        CheckedTextView checkedTextView = (CheckedTextView) view;
        if (checkedTextView.isChecked()) {


            ParseUser.getCurrentUser().add("followingOf",userArray.get(position));

            FancyToast.makeText(welcomeActivity.this,
                    "Following " + userArray.get(position), Toast.LENGTH_SHORT,
                    FancyToast.INFO, true).show();


        }
        else
        {
            ParseUser.getCurrentUser().getList("followingOf").remove(userArray.get(position));
            List updatedList=ParseUser.getCurrentUser().getList("followingOf");
            ParseUser.getCurrentUser().remove("followingOf");
            ParseUser.getCurrentUser().put("followingOf",updatedList);

            FancyToast.makeText(welcomeActivity.this,
                    "Unfollowing " + userArray.get(position), Toast.LENGTH_SHORT,
                    FancyToast.INFO, true).show();
        }
        ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null)
                {
                    FancyToast.makeText(welcomeActivity.this,
                            "Save " , Toast.LENGTH_SHORT,
                            FancyToast.SUCCESS, true).show();
                }
            }
        });
    }
}
