package com.androidsummit.androidsummitsampleapp.nessie;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.androidsummit.androidsummitsampleapp.BuildConfig;
import com.androidsummit.androidsummitsampleapp.Constants;
import com.androidsummit.androidsummitsampleapp.R;
import com.reimaginebanking.api.nessieandroidsdk.NessieError;
import com.reimaginebanking.api.nessieandroidsdk.NessieResultsListener;
import com.reimaginebanking.api.nessieandroidsdk.constants.TransactionMedium;
import com.reimaginebanking.api.nessieandroidsdk.models.Deposit;
import com.reimaginebanking.api.nessieandroidsdk.models.Purchase;
import com.reimaginebanking.api.nessieandroidsdk.requestclients.NessieClient;

/**
 * Created by Jose Garcia on 27/08/2016.
 */
public class NessieManageAccountActivity extends AppCompatActivity {


    private ViewGroup addPurchaseContainer;
    private ViewGroup addDepositContainer;

    private Button manageDepositButton;
    private Button managePurchaseButton;

    private Button addDepositButton;
    private Button addPurchaseButton;


    //private EditText purchaseMerchanID;
    private EditText purchaseAmount;
    private EditText purchaseDescription;

    private EditText depositAmount;
    private EditText depositDescription;


    private NessieClient mClient;

    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_manage_account);

        String key = BuildConfig.NESSIE_API_KEY;
        mClient = NessieClient.getInstance(key);

        addPurchaseContainer = (ViewGroup) findViewById(R.id.manage_purchase_details_container);
        addDepositContainer = (ViewGroup) findViewById(R.id.manage_deposit_details_container);

        manageDepositButton = (Button) findViewById(R.id.manage_deposit_button);
        managePurchaseButton = (Button) findViewById(R.id.manage_purchase_button);

        addDepositButton = (Button) findViewById(R.id.add_deposit_button);
        addPurchaseButton = (Button) findViewById(R.id.add_purchase_button);

        //purchaseMerchanID = (EditText) findViewById(R.id.purchase_merchant_id);
        purchaseAmount = (EditText) findViewById(R.id.purchase_amount);
        purchaseDescription = (EditText) findViewById(R.id.purchase_description);

        depositAmount = (EditText) findViewById(R.id.deposit_amount);
        depositDescription = (EditText) findViewById(R.id.deposit_description);

        manageDepositButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {

                addPurchaseContainer.setVisibility(View.GONE);
                addDepositContainer.setVisibility(View.VISIBLE);
            }
        });

        managePurchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {

                addPurchaseContainer.setVisibility(View.VISIBLE);
                addDepositContainer.setVisibility(View.GONE);
            }
        });

        addPurchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showProgressDialog();

                Purchase.Builder purchaseBuilder = new Purchase.Builder();
                purchaseBuilder.amount(Double.parseDouble(purchaseAmount.getText().toString()));
                purchaseBuilder.description(purchaseDescription.getText().toString());
                purchaseBuilder.merchant("56c66be6a73e492741507624");
                purchaseBuilder.medium("balance");

                mClient.PURCHASE.createPurchase(Constants.ACCOUNT_ID, purchaseBuilder.build(), purchaseListener);
            }
        });

        addDepositButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {

                showProgressDialog();

                Deposit.Builder depositBuilder = new Deposit.Builder();
                depositBuilder.amount(Double.parseDouble(depositAmount.getText().toString()));
                depositBuilder.description(depositDescription.getText().toString());
                depositBuilder.medium(TransactionMedium.BALANCE);

                mClient.DEPOSIT.createDeposit(Constants.ACCOUNT_ID, depositBuilder.build(), purchaseListener);
            }
        });

    }

    private void showProgressDialog() {

        progressDialog = ProgressDialog.show(this, "Creating something",
                                       "something cool", true);

    }

    private void hideProgressDialog() {

        if(progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void hideInputs() {

        addPurchaseContainer.setVisibility(View.GONE);
        addDepositContainer.setVisibility(View.GONE);
    }

    private NessieResultsListener purchaseListener =  new NessieResultsListener() {

        @Override
        public void onSuccess(Object result) {

            hideProgressDialog();
            hideInputs();
        }

        @Override
        public void onFailure(NessieError error) {

            hideProgressDialog();

            Log.d("JOSE", "onFailure: " + error.toString());
        }
    };
}

