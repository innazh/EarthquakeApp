package com.example.assign6oopen;

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

/* compiled from: MainActivity */
class CustomListAdapter extends ArrayAdapter<String> {
    Activity context;
    List<String> itemname1;

    public CustomListAdapter(Activity activity, List<String> itemnameA) {
        super(activity, R.layout.one_item, itemnameA);
        this.context = activity;
        this.itemname1 = itemnameA;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.one_item, null, true);
        StringTokenizer tokens = new StringTokenizer((String) this.itemname1.get(position), "@@");
        String titleToken = tokens.nextToken();
        String timeToken = tokens.nextToken();
        String urlToken = tokens.nextToken();
        String latToken = tokens.nextToken();
        String lngToken = tokens.nextToken();
        String magToken = tokens.nextToken();
        TextView textInfo = (TextView) rowView.findViewById(R.id.textViewTitle);
        textInfo.setText(titleToken);
        Double magDouble = Double.valueOf(Double.parseDouble(magToken));
        if (magDouble.doubleValue() >= 7.5d) {
            textInfo.setBackgroundColor(this.context.getResources().getColor(17170454));
        }
        TextView textInfo2 = (TextView) rowView.findViewById(R.id.textViewDate);
        Date findDate = new Date(Long.parseLong(timeToken));
        LayoutInflater layoutInflater = inflater;
        StringTokenizer stringTokenizer = tokens;
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateformat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String newdate = dateformat.format(Long.valueOf(Date.parse(findDate.toString())));
        SimpleDateFormat simpleDateFormat = dateformat;
        StringBuilder sb = new StringBuilder();
        sb.append(newdate);
        String str = newdate;
        sb.append(" (UTC)");
        textInfo2.setText(sb.toString());
        if (magDouble.doubleValue() >= 7.5d) {
            textInfo2.setBackgroundColor(this.context.getResources().getColor(17170454));
        }
        ((TextView) rowView.findViewById(R.id.textViewURL)).setText(urlToken);
        ((TextView) rowView.findViewById(R.id.textViewLat)).setText(latToken);
        ((TextView) rowView.findViewById(R.id.textViewLng)).setText(lngToken);
        ((TextView) rowView.findViewById(R.id.textViewMag)).setText(magToken);
        return rowView;
    }
}
