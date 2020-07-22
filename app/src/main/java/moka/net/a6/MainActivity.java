package moka.net.a6;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private Button submitBtn;
    private TextView eqNumEt;
    private TextView magnitudeText;
    private int day;
    private int month;
    private int year;
    private String orderBySelection = "magnitude";
    private Button startDateBtn;
    private TextView startDateTv;

    DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int yearPar, int monthPar, int dayPar) {
            year = yearPar;
            month = monthPar;
            day = dayPar;

            StringBuilder sb = new StringBuilder();
            sb.append(year);
            String str = "-";
            sb.append(str);
            sb.append(month + 1);
            sb.append(str);
            sb.append(day);

            startDateTv.setText(sb.toString());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startDateTv = findViewById(R.id.startDateTv);
        startDateBtn = findViewById(R.id.startDateBtn);
        eqNumEt = findViewById(R.id.numOfErthEt);
        submitBtn = findViewById(R.id.submitBtn);
        magnitudeText = findViewById(R.id.magnitudeEt);

        Calendar calendar = Calendar.getInstance();

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        StringBuilder sb = new StringBuilder();
        sb.append(year);
        String str = "-";
        sb.append(str);
        //The Months are numbered from 0 (January) to 11 (December).
        sb.append(month + 1);
        sb.append(str);
        sb.append(day);

        startDateTv.setText(sb.toString());

        startDateBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showDialog(0);
            }
        });

        eqNumEt.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
        magnitudeText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});

        Spinner orderBySpinner = findViewById(R.id.orderBySp);

        orderBySpinner.setAdapter(new ArrayAdapter<>( getApplicationContext(), android.R.layout.select_dialog_item, new String[]{"magnitude", "date"}));
        orderBySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    orderBySelection = "magnitude";
                    return;
                }
                orderBySelection = "time";
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, OutputActivity.class);
                intent.putExtra("orderby", orderBySelection);
                intent.putExtra("limit", eqNumEt.getText().toString());
                intent.putExtra("magnitude", magnitudeText.getText().toString());
                intent.putExtra("starttime", startDateTv.getText().toString());
                startActivity(intent);
            }
        });
    }

    public Dialog onCreateDialog(int id) {
        if (id != 0) {
            return null;
        }
        DatePickerDialog datePickerDialog = new DatePickerDialog(this ,pickerListener, year, month, day);
        return datePickerDialog;
    }
   
}
