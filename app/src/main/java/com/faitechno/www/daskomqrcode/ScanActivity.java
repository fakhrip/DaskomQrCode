package com.faitechno.www.daskomqrcode;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView scannerView;
    String ipaddr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);
        ipaddr = getIntent().getStringExtra("EXTRA_IPADDR");
    }

    @Override
    public void onResume(){
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    public void onPause(){
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {

        //SEND API REQUEST BASED ON NIM GOT FROM RESULT using result.getText()
        final ProgressDialog progressDialog = new ProgressDialog(ScanActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        ApiManager apiManager;
        apiManager = ApiManager.getInstance(ipaddr);

        apiManager.changePraktikanStatus(result.getText(), new Callback<Praktikan>() {
            @Override
            public void onResponse(@NonNull Call<Praktikan> call, @NonNull Response<Praktikan> response) {

                if (response.body() != null) {
                    Toast.makeText(ScanActivity.this, response.body().getNama()+"-"+response.body().getNim(), Toast.LENGTH_LONG).show();
                    Toast.makeText(ScanActivity.this, "Selamat anda berhasil mengumpulkan TP", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
                onBackPressed();
            }

            @Override
            public void onFailure(@NonNull Call<Praktikan> call, @NonNull Throwable t) {

                Log.d("ERRORUY", "onFailure: "+t.getLocalizedMessage());
                Toast.makeText(ScanActivity.this,
                        "Error is " + t.getMessage()
                        , Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                onBackPressed();
            }
        });
    }
}
