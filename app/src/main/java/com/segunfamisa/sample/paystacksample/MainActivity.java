package com.segunfamisa.sample.paystacksample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button payWithPaystack = (Button) findViewById(R.id.button_pay_with_paystack);

        payWithPaystack.setOnClickListener(this);
    }

    /**
     * Handle click listener
     * @param view view that was clicked
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_pay_with_paystack:
                //start actiity
                startActivity(new Intent(MainActivity.this, PayWithPaystackActivity.class));
                break;
        }
    }
}
