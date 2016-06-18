package com.pskloud.osm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;

import com.pskloud.osm.model.Customer;
import com.pskloud.osm.util.CustomerSqlHelper;

import java.util.ArrayList;
import java.util.List;

public class CustomerActivity extends DefaultActivity {

    private Spinner mSpIdentification;
    private EditText mEtIdentification;
    private EditText mEtName;
    private EditText mEtTelephone;
    private EditText mEtAddress;
    private TextInputLayout mTilIdentification;
    private TextInputLayout mTilName;
    private TextInputLayout mTilPhone;
    private TextInputLayout mTilAddress;
    private CoordinatorLayout mClCustomer;

    private CustomerSqlHelper customerSqlHelper;

    private static final String CUSTOMER = "com.pskloud.osm.intent.CUSTOMER";
    private Customer customer;

    public static void show(Context context, final Customer customer){
        context.startActivity(new Intent(context, CustomerActivity.class)
                .putExtra(CUSTOMER, customer));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_customer);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_customer, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_done:
                hideKeyboard();
                if(customer == null){
                    customer = getCustomer();
                    validateCustomer();
                    if(!isErrorEnable()){
                        if (customerSqlHelper.ADD_CUSTOMER.execute(customer))
                            super.showSnackBar(mClCustomer, R.string.customer_saved);
                    }else
                        customer = null;
                }else{
                    customer = getCustomer();
                    validateCustomer();
                    if(!isErrorEnable()) {
                        if (customerSqlHelper.UPDATE_CUSTOMER.execute(customer))
                            super.showSnackBar(mClCustomer, R.string.customer_saved);
                    }
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isErrorEnable(){
        return (mTilPhone.isErrorEnabled() || mTilName.isErrorEnabled() ||
                mTilPhone.isErrorEnabled() || mTilAddress.isErrorEnabled());
    }

    private void validateCustomer(){
        if(customer.getTin().isEmpty() || customer.getTin().length() < 9){
            mTilIdentification.setErrorEnabled(true);
            mTilIdentification.setError(getString(R.string.error_identification));
        }else{
            mTilIdentification.setErrorEnabled(false);
            mTilIdentification.setError(null);
        }

        if(customer.getName().trim().isEmpty()){
            mTilName.setErrorEnabled(true);
            mTilName.setError(getString(R.string.error_name));
        }else{
            mTilName.setErrorEnabled(false);
            mTilName.setError(null);
        }

        if(customer.getPhones().isEmpty() || customer.getPhones().get(0).length() < 11){
            mTilPhone.setErrorEnabled(true);
            mTilPhone.setError(getString(R.string.error_phone));
        }else{
            mTilPhone.setErrorEnabled(false);
            mTilPhone.setError(null);
        }

        if(customer.getAddress().isEmpty()){
            mTilAddress.setErrorEnabled(true);
            mTilAddress.setError(getString(R.string.error_address));
        }else{
            mTilAddress.setErrorEnabled(false);
            mTilAddress.setError(null);
        }
    }

    @Override
    public void init() {
        mSpIdentification = (Spinner)findViewById(R.id.sp_identification);
        mEtIdentification = (EditText)findViewById(R.id.et_identification);
        mEtName = (EditText)findViewById(R.id.et_name);
        mEtAddress = (EditText)findViewById(R.id.et_address);
        mEtTelephone = (EditText) findViewById(R.id.et_telephone);
        mClCustomer = (CoordinatorLayout) findViewById(R.id.cl_customer);

        mTilIdentification = (TextInputLayout)findViewById(R.id.til_identification);
        mTilName = (TextInputLayout)findViewById(R.id.til_name);
        mTilPhone = (TextInputLayout)findViewById(R.id.til_phone);
        mTilAddress = (TextInputLayout)findViewById(R.id.til_address);

        customerSqlHelper = new CustomerSqlHelper(this);
    }

    @Override
    public void setUp() {
        customer = (Customer) getIntent().getExtras().getSerializable(CUSTOMER);
        if(customer != null){
            mSpIdentification.setSelection(getIdentificationSelected());
            mEtIdentification.setText(customer.getTin().substring(2));
            mEtName.setText(customer.getName());
            mEtAddress.setText(customer.getAddress());
            if(customer.getPhones() != null && !customer.getPhones().isEmpty())
                mEtTelephone.setText(customer.getPhones().get(0));
        }
    }

    private String getText(EditText editText){
        return editText.getText().toString().trim().toUpperCase();
    }

    private int getIdentificationSelected(){
        if(customer.getTin() != null)
            switch (customer.getTin().charAt(0)){
                case 'V':
                    return 0;
                case 'J':
                    return 1;
                case 'P':
                    return 2;
                case 'G':
                    return 3;
                case 'E':
                    return 4;
            }
        return 0;
    }

    private Customer getCustomer(){

        String code = mSpIdentification.getSelectedItem().toString()+"-"+
                getText(mEtIdentification);
        List<String> strings = new ArrayList<>();
        strings.add(getText(mEtTelephone));

        return customer = new Customer(getText(mEtName),
                getText(mEtAddress),
                strings,
                code ,
                code,
                customer == null?code:customer.getCode(),
                1, "01", "01", false);
    }
}
