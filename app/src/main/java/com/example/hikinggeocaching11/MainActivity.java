package com.example.hikinggeocaching11;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {
   public RadioGroup hello;
   public RadioButton yeah;
   public int courseNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onClickBtn(View v)
    {
        hello =  (RadioGroup)findViewById(R.id.select);
        int selected_id = hello.getCheckedRadioButtonId();
        yeah = (RadioButton)findViewById(selected_id);
        this.courseNumber = selected_id % 10;
        Toast.makeText(MainActivity.this,
                String.valueOf(courseNumber),Toast.LENGTH_SHORT ).show();

        switchActivities();

    }

    public int getCourseNum()
    {
        return this.courseNumber;

    }

    private void switchActivities()
    {
        // Create intent, create bundle which passes data to new activity, startActivity
        //Intent switchActivityIntent = new Intent(this, MapsActivity.class);

        // code
        Intent mIntent = new Intent(this, MapsActivity.class);
        mIntent.putExtra("crNum", this.courseNumber);

        startActivity(mIntent);
    }
}