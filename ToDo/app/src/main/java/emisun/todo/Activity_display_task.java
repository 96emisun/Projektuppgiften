package emisun.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @author Emil Sundqvist
 */

/*
    This class displays the "Display Task" activity where a selected task is displayed.
 */

public class Activity_display_task extends ActionBarActivity {

    ArrayList<String> headlines;
    ArrayList<String> texts;
    ArrayList<String> times;

    String headline;
    String text;
    String filename;

    int nbrItems;
    Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_task);

        model = new Model(getApplicationContext(), null);
        filename = MainActivity.filename;

        TextView txvHeadline = (TextView) findViewById(R.id.txvHeadline);
        TextView txvText = (TextView) findViewById(R.id.txvText);

        // Receives the intent from MainActivity.
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_delete){
            // Deletes the file containing the data, then sends the user to MainActivity.
            model.deleteAll(getApplicationContext());

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.action_about){
            // Sends the user to Activity_about.
            Intent intent = new Intent(this, Activity_about.class);
            startActivity(intent);

        } else if(id == R.id.notification){
            // Displays a notification.
            model.sendNotification(getApplicationContext());

        }

        return super.onOptionsItemSelected(item);
    }

    /*
        Deletes the task that is currently being shown in Activity_display_task,
        then sends the user to MainActivity.
     */
    public void deleteTask (View view){
        model.deleteTask(getApplicationContext(), nbrItems, headlines, headline, texts, times);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
