package moka.net.a6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class OutputActivity extends AppCompatActivity {
    private ListView linearLayoutListView;
    private String stringURL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&minmagnitude=7&starttime=";

    class EarthquakeAsyncTask extends AsyncTask<String, Void, List<String>> {
        EarthquakeAsyncTask() {
        }

        public List<String> doInBackground(String... stringurl) {
            return Utils.fetchEarthquakeData(stringurl[0]);
        }

        public void onPostExecute(List<String> postExecuteResult) {
            if (postExecuteResult.isEmpty()) {
                Toast.makeText(getApplicationContext(), "NO EARTHQUAKES", Toast.LENGTH_LONG).show();
            }

            ListAdapter arrayAdapter = new ListAdapter(OutputActivity.this, postExecuteResult);

            linearLayoutListView = findViewById(R.id.listViewId);
            linearLayoutListView.setAdapter(arrayAdapter);

            linearLayoutListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    TextView currentLng = view.findViewById(R.id.lngTv);
                    String lat = ((TextView) view.findViewById(R.id.latTv)).getText().toString();
                    String lng = currentLng.getText().toString();

                    StringBuilder sb = new StringBuilder();
                    sb.append("https://www.openstreetmap.org/?mlat=");
                    sb.append(lng);
                    sb.append("&mlon=");
                    sb.append(lat);
                    sb.append("#map=5/");
                    sb.append(lng);
                    sb.append("/");
                    sb.append(lat);

                    String URLstring = sb.toString();

                    Intent intent = new Intent(OutputActivity.this, WebviewActivity.class);
                    intent.putExtra("URLkey", URLstring);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);

        Intent intent = getIntent();

        StringBuilder sb = new StringBuilder();
        sb.append(stringURL);
        sb.append(intent.getStringExtra("starttime"));
        sb.append("&limit=");
        sb.append(intent.getStringExtra("limit"));
        sb.append("&orderby=");
        sb.append(intent.getStringExtra("orderby"));

        stringURL = sb.toString();

        new EarthquakeAsyncTask().execute(stringURL);
    }
}
