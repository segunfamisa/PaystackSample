package com.segunfamisa.sample.paystacksample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

import co.paystack.android.Paystack;
import co.paystack.android.PaystackSdk;
import co.paystack.android.model.Card;
import co.paystack.android.model.Token;

public class PayWithPaystackActivity extends AppCompatActivity {

    private static final int GROUP_LEN = 4;

    private EditText mEditEmail;
    private EditText mEditCardNumber;
    private EditText mEditExpiryMonth;
    private EditText mEditExpiryYear;
    private EditText mEditCVV;

    private Button mButtonCreateToken;

    private TextView mTextToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_with_paystack);

        //setup toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Pay with Paystack");

        PaystackSdk.initialize(this);

        //setup views
        mEditEmail = (EditText) findViewById(R.id.edit_email);
        mEditCardNumber = (EditText) findViewById(R.id.edit_card_number);
        mEditExpiryMonth = (EditText) findViewById(R.id.edit_expiry_month);
        mEditExpiryYear = (EditText) findViewById(R.id.edit_expiry_year);
        mEditCVV = (EditText) findViewById(R.id.edit_cvv);
        mTextToken = (TextView) findViewById(R.id.textview_token);
        mButtonCreateToken = (Button) findViewById(R.id.button_create_token);


        //add text changed listener
        mEditCardNumber.addTextChangedListener(cardWatcher);
        mEditExpiryMonth.addTextChangedListener(new ExpiryWatcher(mEditExpiryMonth));
        mEditExpiryYear.addTextChangedListener(new ExpiryWatcher(mEditExpiryYear));

        //button text listener
        mButtonCreateToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validate fields
                //String number, Integer expiryMonth, Integer expiryYear, String cvc
                try{
                    String number = Utils.cleanNumber(mEditCardNumber.getText().toString().trim());
                    int expiryMonth = Integer.parseInt(mEditExpiryMonth.getText().toString().trim());
                    int expiryYear = Integer.parseInt(mEditExpiryYear.getText().toString().trim());
                    String cvv = mEditCVV.getText().toString().trim();

                    Card card = new Card.Builder(number, expiryMonth, expiryYear, cvv).build();

                    if(card.isValid()) {
                        //create token
                        createToken(card);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void createToken(Card card) {
        //show progress
        PaystackSdk.createToken(card, new Paystack.TokenCallback() {
            @Override
            public void onCreate(Token token) {
                if(token != null) {
                    //dismiss progress


                    mTextToken.setText(token.last4 + "\n" + token.token);

                    //send the token to your server for charging
                }
            }

            @Override
            public void onError(Exception error) {
                //dismiss progress, show error to the user
                mTextToken.setText(error.toString());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Text Watcher to format card number
     */
    private TextWatcher cardWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String number = s.toString();
            if (number.length() >= GROUP_LEN) {
                String formatted = Utils.formatForViewing(number, GROUP_LEN);
                if (!number.equalsIgnoreCase(formatted)) {
                    mEditCardNumber.removeTextChangedListener(cardWatcher);
                    mEditCardNumber.setText(formatted);
                    mEditCardNumber.setSelection(formatted.length());
                    mEditCardNumber.addTextChangedListener(cardWatcher);
                }
            }
        }
    };

    /**
     * Expiry Watcher
     */
    private class ExpiryWatcher implements TextWatcher {

        private EditText editText;

        public ExpiryWatcher(EditText editText) {
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            try{
                int number = Integer.parseInt(s.toString());
                int length = s.length();
                switch (editText.getId()) {
                    case R.id.edit_expiry_month:
                        if(length == 1) {
                            if(number > 1) {
                                //add a 0 in front
                                setText("0"+number);
                            }
                        } else {
                            if(number > 12) {
                                setText("12");
                            }

                            //request focus on the next field
                            mEditExpiryYear.requestFocus();
                        }
                        break;
                    case R.id.edit_expiry_year:
                        String stringYear = (Calendar.getInstance().get(Calendar.YEAR) + "").substring(2);
                        int currentYear = Integer.parseInt(stringYear);

                        if(length == 1) {
                            int firstDigit = Integer.parseInt(String.valueOf(currentYear).substring(0, length));
                            if(number < firstDigit) {
                                setText(firstDigit+"");
                            }
                        } else {
                            if(number < currentYear){
                                setText(currentYear+"");
                            }

                            mEditCVV.requestFocus();
                        }
                        break;
                }
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }
        }

        private void setText(String text) {
            editText.setText(text);
            editText.setSelection(editText.getText().toString().trim().length());
        }
    }
}
