package emisun.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

/**
 * @author Emil Sundqvist
 */

/*
    This class displays the "Add Task" activity where a new task can be added.
 */

public class Activity_add_task extends ActionBarActivity {

    private EditText edtHeadline;
    private EditText edtText;
    private Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        edtHeadline = (EditText) findViewById(R.id.edtHeadline);
        edtText = (EditText) findViewById(R.id.edtText);
        model = new Model(getApplicationContext(), null);
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
            // Deletes the file containing the data, then sends the user to MainActivity
            model.deleteAll(getApplicationContext());

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.action_about){
            // Sends the user to Activity_about
            Intent intent = new Intent(this, Activity_about.class);
            startActivity(intent);

        } else if(id == R.id.notification){
            // Displays a notification
            model.sendNotification(getApplicationContext());

        }

        return super.onOptionsItemSelected(item);
    }

    /*
        Creates a String containing the user-entered information together
        with the date and time of the button press.
     */
    public void save (View view) {
        String time = model.getCurrentDateTime("yyMMddHHmmss");
        String entry = edtHeadline.getText().toString() + ";" + edtText.getText().toString() + ";" + time + "\n";

        model.saveCSV(getApplicationContext(), entry);

        // Sends the user back to MainActivity.
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
