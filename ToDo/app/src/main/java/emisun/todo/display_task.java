package emisun.todo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;


public class display_task extends Activity {

    ArrayList<String> headlines;
    ArrayList<String> texts;
    ArrayList<String> times;

    String headline;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_task);

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
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void deleteTask (View view){
        for(int i = 0; i < headlines.size(); i++){
            if(headline.equals(headlines.get(i))){

                headlines.remove(i);
                texts.remove(i);
                times.remove(i);

            }
        }

        String filename = "test.csv";
        try {

            File dir = getFilesDir();
            File file = new File(dir, filename);
            file.delete();

            FileOutputStream out = openFileOutput(filename, Context.MODE_APPEND);
            for(int i = 0; i < headlines.size(); i++){
                String entry = headlines.get(i) + ";" + texts.get(i) + ";" + times.get(i) + "\n";
                out.write(entry.getBytes());
            }

            out.close();
            Context context = getApplicationContext();
            Toast.makeText(context, "Removed successfully", Toast.LENGTH_LONG).show();

            screenHome();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void screenHome () {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
