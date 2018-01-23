package com.example.halshammari.bmiown;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class BMIActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    private Spinner spinner_Length;
    private Spinner spinner_Weight;

    private EditText editText_length;
    private EditText editText_weight;

    private TextView mlengthinCM;
    private TextView mWeightKG;

    private TextView mBMI;


    private String length_Unit = "centimetre";
    private String weight_Unit = "kilogram";

    private String length_Value = "0.0";
    private String weight_Value = "0.0";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test2);

        spinner_Length = (Spinner) findViewById(R.id.spinner1);
        spinner_Weight = (Spinner) findViewById(R.id.spinner2);

        spinner_Length.setOnItemSelectedListener(this);
        spinner_Weight.setOnItemSelectedListener(this);


        mlengthinCM = (TextView) findViewById(R.id.txt_answer_length);
        mWeightKG = (TextView) findViewById(R.id.txt_answer_weight);


        editText_length = (EditText) findViewById(R.id.editText_length);
        editText_weight = (EditText) findViewById(R.id.editText_weigth);;

        mBMI = (TextView) findViewById(R.id.bmi);


        //*-------------------------- lenght
        editText_length.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                /*mlengthinCM.setText(s);
                Log.d("units","efter text changed to  = "+ s);*/
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if(s.length() > 0)
                {
                    //mlengthinCM.setText(s);
                    length_Value=s.toString();
                    convertLength();
                    Log.d("units","efter text changed to  = "+ length_Value);
                }
            }
        });

        //*-------------------------- weight
        editText_weight.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                /*mWeightKG.setText(s);
                Log.d("units","On text changed  = "+ s);*/
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if(s.length() > 0){
                    weight_Value = s.toString();
                    convertWeight();
                    Log.d("units","efter text changed to  = "+ weight_Value);
                }
            }
        });



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        if(parent.getId() == spinner_Length.getId())
        {//for length
            Object length = parent.getItemAtPosition(position);
            length_Unit = length.toString();
            convertLength();
            Log.d("units","you length in cm = " + length_Unit); //
        }else if(parent.getId() == spinner_Weight.getId()) {// for weight
            Object weight = parent.getItemAtPosition(position);
            weight_Unit = weight.toString();
            convertWeight();
            Log.d("units","you weight in kg = "+ weight_Unit);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }

    DecimalFormat twoDForm = new DecimalFormat("##.##");
    //convert
    private void convertLength()
    {
        double cm = 0;
        double x = Double.parseDouble(length_Value);

        if(length_Unit.equals("Metre"))
        {
            // 1 m = 100 cm
            cm = (x*100);
        }else if (length_Unit.equals("Feet"))
        {
            double meters = x/3.2808;
            cm = (meters*100);
        }else if (length_Unit.equals("Inch"))
        {
            double feet = x / 12;
            double meters = feet/3.2808;
            cm = (meters*100);
        } else{
            cm = x;
        }
        length_Value = twoDForm.format(cm);
        mlengthinCM.setText(length_Value + " cm");
        setBMI();
    }

    private void convertWeight()
    {
        double kg = 0;
        double x = Double.parseDouble(weight_Value);
        if(weight_Unit.equals("Pound"))
        {
            kg = x*0.454;  //0.453592
        }else if(weight_Unit.equals("Stone"))
        {
            kg = x*(6.35029);
        }
        else {
            kg = x;
        }
        weight_Value = ""+kg;
        mWeightKG.setText(weight_Value + " kg");
        setBMI();
    }

    private void setBMI()
    {
        double weightInKilos= Double.parseDouble(weight_Value);
        double heightInCentiMeters= Double.parseDouble(length_Value);
        double heightInMeters = heightInCentiMeters/100;
        double bmi = weightInKilos / Math.pow(heightInMeters, 2.0);
        mBMI.setText("Your BMI is "+twoDForm.format(bmi));
    }



}
