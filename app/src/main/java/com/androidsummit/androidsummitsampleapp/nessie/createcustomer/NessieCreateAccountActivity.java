package com.androidsummit.androidsummitsampleapp.nessie.createcustomer;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.androidsummit.androidsummitsampleapp.BuildConfig;
import com.androidsummit.androidsummitsampleapp.Constants;
import com.androidsummit.androidsummitsampleapp.R;
import com.androidsummit.androidsummitsampleapp.nessie.NessieCustomerListAdapter;
import com.reimaginebanking.api.nessieandroidsdk.NessieError;
import com.reimaginebanking.api.nessieandroidsdk.NessieResultsListener;
import com.reimaginebanking.api.nessieandroidsdk.constants.AccountType;
import com.reimaginebanking.api.nessieandroidsdk.models.Account;
import com.reimaginebanking.api.nessieandroidsdk.models.Customer;
import com.reimaginebanking.api.nessieandroidsdk.requestclients.NessieClient;

import java.util.List;

/**
 * Created by Jose Garcia on 27/08/2016.
 */
public class NessieCreateAccountActivity extends AppCompatActivity {

    private NessieClient mClient;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {

        super.onCreate(savedInstanceState, persistentState);

        setContentView(R.layout.activity_nessie_create_customer);


        String key = BuildConfig.NESSIE_API_KEY;
        mClient = NessieClient.getInstance(key);


        final TextView accountName = (TextView) findViewById(R.id.create_customer_account_name);
        final TextView accountBalance = (TextView) findViewById(R.id.create_customer_balance);

        Button customersBtn = (Button) findViewById(R.id.create_customer_button);
        customersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Account.Builder accountBuilder = new Account.Builder();
                accountBuilder.nickname(accountName.getText().toString());
                accountBuilder.balance(Integer.valueOf(accountBalance.getText().toString()));
                accountBuilder.type(AccountType.SAVINGS);

                mClient.ACCOUNT.createAccount(Constants.CUSTOMER_ID, accountBuilder.build(), new NessieResultsListener() {
                    @Override public void onSuccess(Object result) {


                    }

                    @Override public void onFailure(NessieError error) {

                    }
                });

            }
        });

    }
}
