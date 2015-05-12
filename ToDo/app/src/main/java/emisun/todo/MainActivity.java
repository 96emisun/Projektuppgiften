package emisun.todo;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * @author Emil Sundqvist
 */

/*
    This class displays the "MainActivity" activity where all the tasks are displayed.
 */
public class MainActivity extends ListActivity {

    private ArrayList<String> listItems;
    private ArrayList<String> headlines;
    private ArrayList<String> texts;
    private ArrayList<String> times;

    private ListView listView;

    private ArrayAdapter<String> adapter;

    public static String filename;
    private Model model;

    @Override
    public void onCreate(Bundle savedItemStance) {
        super.onCreate(savedItemStance);
        setContentView(R.layout.activity_main);

        filename = "data.csv";

        headlines = new ArrayList<>();
        texts = new ArrayList<>();
        times = new ArrayList<>();
        listItems = new ArrayList<>();

        model = new Model(getApplicationContext(), listItems);

        // Creates the adapter that controls the ListView.
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        setListAdapter(adapter);

        listView = (ListView) findViewById(android.R.id.list);

        /*
            When an item in the list is clicked, the user is sent to Activity_display_task where
            the selected item is shown.
         */
        OnItemClickListener onItemClickListener = new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), Activity_display_task.class);

                int nbrItems = listView.getAdapter().getCount();

                intent.putExtra("nbrItems", nbrItems);

                intent.putExtra("headline", headlines.get((int)id));
                intent.putExtra("text", texts.get((int)id));
                intent.putExtra("headlines", headlines);
                intent.putExtra("texts", texts);
                intent.putExtra("times", times);

                startActivity(intent);

            }
        };

        listView.setOnItemClickListener(onItemClickListener);

        model.readCSV(adapter, headlines, texts, times);

    }

    // Sends the user to Activity_add_task.
    public void screenAdd (View view) {
        Intent intent = new Intent(this, Activity_add_task.class);
        startActivity(intent);
    }

    // Deletes the file containing the data.
    public void deleteAll(View view){
        model.deleteAll(getApplicationContext());

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    // Displays a notification.
    public void notification(View view){
        model.sendNotification(getApplicationContext());
    }

    // Sends the user to Activity_about.
    public void screenAbout(View view){
        Intent intent = new Intent(this, Activity_about.class);
        startActivity(intent);
    }
}
