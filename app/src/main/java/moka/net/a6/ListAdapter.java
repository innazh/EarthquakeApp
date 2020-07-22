package moka.net.a6;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TimeZone;

class ListAdapter extends ArrayAdapter<String> {
    private Activity activity;
    private List<String> list;

    public ListAdapter(Activity activity, List<String> itemnameA) {
        super(activity, R.layout.eq_row, itemnameA);
        this.activity = activity;
        this.list = itemnameA;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = this.activity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.eq_row, null, true);

        StringTokenizer tokens = new StringTokenizer(this.list.get(position), "@@");

        String titleToken = tokens.nextToken();
        String timeToken = tokens.nextToken();
        String urlToken = tokens.nextToken();
        String latToken = tokens.nextToken();
        String lngToken = tokens.nextToken();
        String magToken = tokens.nextToken();

        TextView titleTv = rowView.findViewById(R.id.titleTv);
        TextView dateTv = rowView.findViewById(R.id.dateTv);
        TextView urlTv = rowView.findViewById(R.id.urlTv);
        TextView latTv = rowView.findViewById(R.id.latTv);
        TextView lngTv = rowView.findViewById(R.id.lngTv);
        TextView magTv = rowView.findViewById(R.id.magTv);


        double magDouble = Double.parseDouble(magToken);

        if (magDouble >= 7.5d) {
            titleTv.setBackgroundColor(this.activity.getResources().getColor(android.R.color.holo_red_light));
            dateTv.setBackgroundColor(this.activity.getResources().getColor(android.R.color.holo_red_light));
        }

        Date findDate = new Date(Long.parseLong(timeToken));

        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateformat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String newdate = dateformat.format(Date.parse(findDate.toString()));

        StringBuilder sb = new StringBuilder();
        sb.append(newdate);
        sb.append(" (UTC)");

        titleTv.setText(titleToken);
        dateTv.setText(sb.toString());
        urlTv.setText(urlToken);
        latTv.setText(latToken);
        lngTv.setText(lngToken);
        magTv.setText(magToken);

        return rowView;
    }
}
