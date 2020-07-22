package com.example.assign6oopen;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView linearLayoutListView;
    List<String> returnArray;
    String stringURL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&minmagnitude=7&starttime=";

    class QuakeAsyncTask extends AsyncTask<String, Void, List<String>> {
        QuakeAsyncTask() {
        }

        /* access modifiers changed from: protected */
        public List<String> doInBackground(String... stringurl) {
            MainActivity.this.returnArray = Utils.fetchEarthquakeData(stringurl[0]);
            return MainActivity.this.returnArray;
        }

        public void onPostExecute(List<String> postExecuteResult) {
            if (postExecuteResult.isEmpty()) {
                Toast.makeText(MainActivity.this.getApplicationContext(), "NO EARTHQUAKES", 1).show();
            }
            CustomListAdapter arrayAdapter = new CustomListAdapter(MainActivity.this, postExecuteResult);
            MainActivity mainActivity = MainActivity.this;
            mainActivity.linearLayoutListView = (ListView) mainActivity.findViewById(R.id.listViewManyItemsID);
            MainActivity.this.linearLayoutListView.setAdapter(arrayAdapter);
            MainActivity.this.linearLayoutListView.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    TextView currentLng = (TextView) view.findViewById(R.id.textViewLng);
                    String stringLat = ((TextView) view.findViewById(R.id.textViewLat)).getText().toString();
                    String stringLng = currentLng.getText().toString();
                    StringBuilder sb = new StringBuilder();
                    sb.append("https://www.openstreetmap.org/?mlat=");
                    sb.append(stringLng);
                    sb.append("&mlon=");
                    sb.append(stringLat);
                    sb.append("#map=5/");
                    sb.append(stringLng);
                    sb.append("/");
                    sb.append(stringLat);
                    String URLstring = sb.toString();
                    Intent intent = new Intent(MainActivity.this, WebviewActivity.class);
                    intent.putExtra("URLkey", URLstring);
                    MainActivity.this.startActivity(intent);
                }
            });
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main);
        Intent intent = getIntent();
        String intentExtra1 = intent.getStringExtra("order");
        String intentExtra3 = intent.getStringExtra("limit");
        String intentExtra4 = intent.getStringExtra("start");
        StringBuilder sb = new StringBuilder();
        sb.append(this.stringURL);
        sb.append(intentExtra4);
        sb.append("&limit=");
        sb.append(intentExtra3);
        sb.append("&orderby=");
        sb.append(intentExtra1);
        this.stringURL = sb.toString();
        new QuakeAsyncTask().execute(new String[]{this.stringURL});
    }
}
