package com.example.hikinggeocaching11;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Course2Activity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course1);

    }

    public void onClickBtn(View v)
    {
        switchActivities();
    }
    private void switchActivities()
    {
        // code
        Intent mIntent = new Intent(this, MapsActivity.class);
        mIntent.putExtra("crNum", 2);

        startActivity(mIntent);
    }
}