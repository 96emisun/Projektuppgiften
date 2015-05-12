package emisun.todo;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Emil on 2015-05-11.
 */
public class Model {

    Context context;
    ArrayList<String> listItems;
    String filename;

    public Model(Context context, ArrayList<String> listItems){
        this.context = context;
        this.listItems = listItems;
        this.filename = MainActivity.filename;
    }

    public void deleteAll(Context context){
        File dir = context.getFilesDir();
        File file = new File(dir, filename);
        file.delete();
    }

    public void readCSV (ArrayAdapter<String> adapter, ArrayList<String> headlines, ArrayList<String> texts, ArrayList<String> times) {
        FileInputStream in = null;
        String temp;
        String[] a;

        try {

            in = context.openFileInput(filename);
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

                addItem(a[0], listItems, adapter);
            }

            scan.close();

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
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

    public void addItem(String headline, ArrayList<String> listItems, ArrayAdapter<String> adapter){
        listItems.add(headline);
        adapter.notifyDataSetChanged();
    }

    public void saveCSV(Context context, String entry){
        try {

            FileOutputStream out = context.openFileOutput(filename, Context.MODE_APPEND);
            out.write(entry.getBytes());
            out.close();
            Toast.makeText(context, "Saved successfully", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void sendNotification(Context context){

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, 0);

        Notification n  = new Notification.Builder(context)
                .setContentTitle("Notification from ToDo.")
                .setSmallIcon(R.drawable.notif_icon)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .setStyle(new Notification.BigTextStyle().bigText("ToDo was created as a part of the course Programming 2")).build();


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, n);

    }

    public void deleteTask(Context context,
                           int nbrItems, ArrayList<String> headlines, String headline,
                           ArrayList<String> texts, ArrayList<String> times){

        if(nbrItems != 1) {
            for (int i = 0; i < headlines.size(); i++) {
                if (headline.equals(headlines.get(i))) {

                    headlines.remove(i);
                    texts.remove(i);
                    times.remove(i);

                }
            }
            try {

                File dir = context.getFilesDir();
                File file = new File(dir, filename);
                file.delete();

                FileOutputStream out = context.openFileOutput(filename, Context.MODE_APPEND);
                for (int i = 0; i < headlines.size(); i++) {
                    String entry = headlines.get(i) + ";" + texts.get(i) + ";" + times.get(i) + "\n";
                    out.write(entry.getBytes());
                }

                out.close();
                Toast.makeText(context, "Removed successfully", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            deleteAll(context);
        }
    }

}
