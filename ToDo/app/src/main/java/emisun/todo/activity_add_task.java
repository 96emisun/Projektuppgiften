package emisun.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class Activity_add_task extends ActionBarActivity {

    EditText edtHeadline;
    EditText edtText;
    Model model;
    String filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        edtHeadline = (EditText) findViewById(R.id.edtHeadline);
        edtText = (EditText) findViewById(R.id.edtText);
        model = new Model(getApplicationContext(), null);
        filename = MainActivity.filename;
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

            model.deleteAll(getApplicationContext());

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.action_about){

            Intent intent = new Intent(this, Activity_about.class);
            startActivity(intent);

        } else if(id == R.id.notification){

            model.sendNotification(getApplicationContext());

        }

        return super.onOptionsItemSelected(item);
    }

    public void save (View view) {
        String time = model.getCurrentDateTime("yyMMddHHmmss");
        String entry = edtHeadline.getText().toString() + ";" + edtText.getText().toString() + ";" + time + "\n";

        model.saveCSV(getApplicationContext(), entry);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
