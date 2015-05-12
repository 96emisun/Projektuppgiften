package emisun.todo;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;


public class Activity_display_task extends ActionBarActivity {

    ArrayList<String> headlines;
    ArrayList<String> texts;
    ArrayList<String> times;

    String headline;
    String text;
    String filename;

    int nbrItems;

    MainActivity activity;
    Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_task);

        activity = new MainActivity();
        model = new Model(getApplicationContext(), null);
        filename = MainActivity.filename;

        TextView txvHeadline = (TextView) findViewById(R.id.txvHeadline);
        TextView txvText = (TextView) findViewById(R.id.txvText);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            headline = extras.getString("headline");
            text = extras.getString("text");

            txvHeadline.setText(headline);
            txvText.setText(text);

            headlines = extras.getStringArrayList("headlines");
            texts = extras.getStringArrayList("texts");
            times = extras.getStringArrayList("times");

            nbrItems = extras.getInt("nbrItems");
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_actions, menu);
        return true;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_delete){
            model.deleteAll(getApplicationContext());

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

            return true;

        } else if (id == R.id.action_about){
            Intent intent = new Intent(this, Activity_about.class);
            startActivity(intent);
        } else if(id == R.id.notification){
            model.sendNotification(getApplicationContext());
        }

        return super.onOptionsItemSelected(item);
    }

    public void deleteTask (View view){
        model.deleteTask(getApplicationContext(), nbrItems, headlines, headline, texts, times);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
