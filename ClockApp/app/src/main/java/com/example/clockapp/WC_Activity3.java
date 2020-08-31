package com.example.clockapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class WC_Activity3 extends AppCompatActivity {
    Calendar current;
    Spinner spinner;
    TextView txtCurrentTime, timezone, txtTimeZoneTime;
    long miliSeconds;
    ArrayAdapter<String> idAdapter;
    SimpleDateFormat sdf;
    Date resultDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_w_c_3);

        spinner = (Spinner) findViewById(R.id.spinner);

        timezone = (TextView) findViewById(R.id.timezone);
        txtCurrentTime = (TextView) findViewById(R.id.txtCurrentTime);
        txtTimeZoneTime = (TextView) findViewById(R.id.txtTimeZoneTime);

        String[] idArray = TimeZone.getAvailableIDs();

        sdf = new SimpleDateFormat("EEEE, dd MMMM yyy HH:mm:ss");

        idAdapter = new ArrayAdapter<String> (this, android.R.layout.simple_spinner_item, idArray);

        idAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(idAdapter);

        getGMTtime();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                getGMTtime();
                String selectedID = (String) (parent.getItemAtPosition(position));

                TimeZone timeZone = TimeZone.getTimeZone(selectedID);
                String TimeZoneName = timeZone.getDisplayName();

                int TimeZoneOffset = timeZone.getRawOffset() / (60 * 1000);
                int hrs = TimeZoneOffset / 60;
                int mins = TimeZoneOffset % 60;

                miliSeconds = miliSeconds + timeZone.getRawOffset();

                resultDate = new Date(miliSeconds);
                System.out.println(sdf.format(resultDate));

                timezone.setText(TimeZoneName + " : GMT " + hrs + " : " + mins);
                txtTimeZoneTime.setText("" + sdf.format(resultDate));
                miliSeconds = 0;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void getGMTtime() {
        current = Calendar.getInstance();
        txtCurrentTime.setText("" + current.getTime());

        miliSeconds = current.getTimeInMillis();

        TimeZone tzCurrent = current.getTimeZone();
        int offset = tzCurrent.getRawOffset();
        if(tzCurrent.inDaylightTime(new Date())) {
            offset = offset + tzCurrent.getDSTSavings();
        }
        miliSeconds = miliSeconds - offset;

        resultDate = new Date(miliSeconds);
        System.out.println(sdf.format(resultDate));
    }
}