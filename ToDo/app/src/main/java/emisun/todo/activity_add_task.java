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
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;


public class activity_add_task extends Activity {

    EditText edtHeadline;
    EditText edtText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        edtHeadline = (EditText) findViewById(R.id.edtHeadline);
        edtText = (EditText) findViewById(R.id.edtText);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_add_task, menu);
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

    public void saveCSV (View view) {

        String filename = "test.csv";
        String entry = edtHeadline.getText().toString() + ";" + edtText.getText().toString() + ";" + "201504181231" + "\n";

        try {

            FileOutputStream out = openFileOutput(filename, Context.MODE_APPEND);
            out.write(entry.getBytes());
            out.close();
            Context context = getApplicationContext();
            Toast.makeText(context, "Saved successfully", Toast.LENGTH_LONG).show();

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
