package emisun.todo;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class MainActivity extends ListActivity {

    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
    ArrayList<String> listItems;
    ArrayList<String> headlines;
    ArrayList<String> texts;
    ArrayList<String> times;

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedItemStance) {
        super.onCreate(savedItemStance);
        setContentView(R.layout.activity_main);

        listItems = new ArrayList<>();
        headlines = new ArrayList<>();
        texts = new ArrayList<>();
        times = new ArrayList<>();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        setListAdapter(adapter);

        ListView listView = (ListView) findViewById(android.R.id.list);

        OnItemClickListener onItemClickListener = new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), display_task.class);

                intent.putExtra("headline", headlines.get((int)id));
                intent.putExtra("text", texts.get((int)id));
                intent.putExtra("headlines", headlines);
                intent.putExtra("texts", texts);
                intent.putExtra("times", times);

                startActivity(intent);

            }
        };

        listView.setOnItemClickListener(onItemClickListener);

        readCSV(null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        File dir = getFilesDir();
        File file = new File(dir, "test.csv");
        file.delete();
        readCSV(null);

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void screenAdd (View view) {
        Intent intent = new Intent(this, activity_add_task.class);
        startActivity(intent);
    }

    public void addItem(String headline){
        listItems.add(headline);
        adapter.notifyDataSetChanged();
    }

    public void readCSV (View view) {
        String filename = "test.csv";
        FileInputStream in = null;
        String temp;
        String[] a;

        try {

            in = openFileInput(filename);
            byte[] reader = new byte[in.available()];

            while( in.read(reader) != -1 );

            Scanner scan = new Scanner(new String(reader));
            scan.useDelimiter("\\n");

            adapter.clear();

            while(scan.hasNext()){
                temp = scan.next();
                a = temp.split(";");

                headlines.add(a[0]);
                texts.add(a[1]);
                times.add(a[2]);

                addItem(a[0]);
            }

            scan.close();

        } catch (Exception e) {
            Log.e( "Error", e.getMessage() );
        } finally {
            if(in != null){
                try{
                    in.close();
                } catch (IOException e){
                    Log.e("Error", e.getMessage());
                }
            }
        }
    }
}
