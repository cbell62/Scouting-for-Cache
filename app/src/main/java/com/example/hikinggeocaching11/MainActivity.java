package com.example.hikinggeocaching11;
import android.content.Intent;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {
   public RadioGroup hello;
   public RadioButton yeah;
   public int courseNumber = 0;

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
        courseNumber = selected_id % 10;
        Toast.makeText(MainActivity.this,
                String.valueOf(courseNumber),Toast.LENGTH_SHORT ).show();

        switchActivities();

    }

    public int getCourseNum()
    {
        return courseNumber;

    }

    private void switchActivities()
    {
        Intent switchActivityIntent = new Intent(this, MapsActivity.class);
        startActivity(switchActivityIntent);
    }
}