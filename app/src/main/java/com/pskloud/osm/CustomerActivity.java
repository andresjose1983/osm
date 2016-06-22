package com.pskloud.osm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.pskloud.osm.model.Customer;
import com.pskloud.osm.model.Locality;
import com.pskloud.osm.model.Tax;
import com.pskloud.osm.util.CustomerSqlHelper;
import com.pskloud.osm.util.LocalitySqlHelper;

import java.util.ArrayList;
import java.util.List;

public class CustomerActivity extends DefaultActivity {

    private Spinner mSpIdentification;
    private EditText mEtIdentification;
    private EditText mEtName;
    private EditText mEtTelephone;
    private EditText mEtAddress;
    private Spinner mSpLocality;
    private Spinner mSpTax;
    private TextInputLayout mTilIdentification;
    private TextInputLayout mTilName;
    private TextInputLayout mTilPhone;
    private TextInputLayout mTilAddress;
    private CoordinatorLayout mClCustomer;

    private CustomerSqlHelper customerSqlHelper;
    private LocalitySqlHelper localitySqlHelper;

    private static final String CUSTOMER = "com.pskloud.osm.intent.CUSTOMER";
    private Customer customer;
    private List<Locality> localities;

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
                        if (customerSqlHelper.ADD.execute(customer))
                            super.showSnackBar(mClCustomer, R.string.customer_saved);
                    }else
                        customer = null;
                }else{
                    customer = getCustomer();
                    validateCustomer();
                    if(!isErrorEnable()) {
                        if (customerSqlHelper.UPDATE.execute(customer))
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

        mSpLocality = (Spinner)findViewById(R.id.sp_locality);
        mSpTax = (Spinner)findViewById(R.id.sp_tax);

        customerSqlHelper = new CustomerSqlHelper(this);
        localitySqlHelper = new LocalitySqlHelper(this);
    }

    @Override
    public void setUp() {

        localities = localitySqlHelper.GET.execute();
        if(localities != null){
            String[] strings = new String[localities.size()];
            int index = 0;
            for (Locality locality: localities) {
                strings[index] = locality.getName();
                ++index;
            }
            ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, strings);
            mSpLocality.setAdapter(stringArrayAdapter);
        }
        mSpTax.setAdapter(new ArrayAdapter<Tax>(this, android.R.layout.simple_list_item_1, Tax.values()));

        customer = (Customer) getIntent().getExtras().getSerializable(CUSTOMER);

        if(customer != null){
            mSpIdentification.setSelection(getIdentificationSelected());
            mEtIdentification.setText(customer.getTin().substring(2));
            mEtName.setText(customer.getName());
            mEtAddress.setText(customer.getAddress());
            if(customer.getPhones() != null && !customer.getPhones().isEmpty())
                mEtTelephone.setText(customer.getPhones().get(0));
            mSpLocality.setSelection(getLocalitySelection(customer.getZone()), true);
            mSpTax.setSelection(getTaxSelection(customer.getTaxType()));

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
        List<String> telephones = new ArrayList<>();
        telephones.add(getText(mEtTelephone));

        return new Customer.Builder().code(customer == null?code.replace("-", ""):customer.getCode())
                .name( getText(mEtName))
                .phones(telephones)
                .address(getText(mEtAddress))
                .isNew(customer == null?true:customer.isNew())
                .sync(customer == null?false: customer.isSync())
                .tin(code)
                .zone(localities.get(mSpLocality.getSelectedItemPosition()).getCode())
                .taxType(Tax.values()[mSpTax.getSelectedItemPosition()].getId())
                .build();
    }

    private int getLocalitySelection(String code){
        int index = 0;
        Log.e("codigo ", code);
        for (Locality locality : localities) {
            Log.e("hola", locality.getCode());
            if(code.equals(locality.getCode())){
                Log.e("codigo ", "entro ");
                return index;
            }
            ++index;
        }
        return 0;
    }

    private int getTaxSelection(int id){
        int index = 0;
        for (Tax tax : Tax.values()) {
            if(id == tax.getId()){
                return index;
            }
            ++index;
        }
        return 0;
    }
}
