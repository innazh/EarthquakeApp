package com.example.assign6oopen;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.Arrays;
import java.util.Calendar;

public class FirstActivity extends AppCompatActivity {
    Button buttonFirst;
    final int datePicker = 0;
    int day;
    TextView limitText;
    TextView magnitudeText;
    int month;
    OnDateSetListener pickerListener = new OnDateSetListener() {
        public void onDateSet(DatePicker view, int yearPar, int monthPar, int dayPar) {
            FirstActivity firstActivity = FirstActivity.this;
            firstActivity.year = yearPar;
            firstActivity.month = monthPar;
            firstActivity.day = dayPar;
            TextView textView = firstActivity.startText;
            StringBuilder sb = new StringBuilder();
            sb.append(FirstActivity.this.year);
            String str = "-";
            sb.append(str);
            sb.append(FirstActivity.this.month + 1);
            sb.append(str);
            sb.append(FirstActivity.this.day);
            textView.setText(sb.toString());
        }
    };
    String spinnerSelected = "magnitude";
    Button startButton;
    TextView startText;
    int year;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_first);
        this.startText = (TextView) findViewById(R.id.textViewStart);
        Calendar calendar = Calendar.getInstance();
        this.year = calendar.get(1);
        this.month = calendar.get(2);
        this.day = calendar.get(5);
        TextView textView = this.startText;
        StringBuilder sb = new StringBuilder();
        sb.append(this.year);
        String str = "-";
        sb.append(str);
        sb.append(this.month + 1);
        sb.append(str);
        sb.append(this.day);
        textView.setText(sb.toString());
        this.startButton = (Button) findViewById(R.id.buttonStart);
        this.startButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                FirstActivity.this.showDialog(0);
            }
        });
        Spinner spinner1 = (Spinner) findViewById(R.id.spinner);
        spinner1.setAdapter(new ArrayAdapter<>(this, 17367057, Arrays.asList(new String[]{"magnitude", "date"})));
        spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    FirstActivity.this.spinnerSelected = "magnitude";
                    return;
                }
                FirstActivity.this.spinnerSelected = "time";
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.limitText = (TextView) findViewById(R.id.textViewLimit);
        this.limitText.setFilters(new InputFilter[]{new LengthFilter(2)});
        this.magnitudeText = (TextView) findViewById(R.id.textViewMagnitude);
        this.magnitudeText.setFilters(new InputFilter[]{new LengthFilter(3)});
        this.buttonFirst = (Button) findViewById(R.id.firstButton);
        this.buttonFirst.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                Intent firstIntent = new Intent(FirstActivity.this, MainActivity.class);
                firstIntent.putExtra("order", FirstActivity.this.spinnerSelected);
                firstIntent.putExtra("limit", FirstActivity.this.limitText.getText().toString());
                firstIntent.putExtra("magnitude", FirstActivity.this.magnitudeText.getText().toString());
                firstIntent.putExtra("start", FirstActivity.this.startText.getText().toString());
                FirstActivity.this.startActivity(firstIntent);
            }
        });
    }

    /* access modifiers changed from: protected */
    public Dialog onCreateDialog(int id) {
        if (id != 0) {
            return null;
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this.pickerListener, this.year, this.month, this.day);
        return datePickerDialog;
    }
}
