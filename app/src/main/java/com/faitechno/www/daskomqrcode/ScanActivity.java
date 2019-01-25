package com.faitechno.www.daskomqrcode;

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
        ApiManager apiManager;
        apiManager = ApiManager.getInstance(ipaddr);

        apiManager.changePraktikanStatus(result.getText(), new Callback<Praktikan>() {
            @Override
            public void onResponse(Call<Praktikan> call, Response<Praktikan> response) {
                Log.d("TEST", "onResponse: "+call.request().toString());
                Toast.makeText(ScanActivity.this, response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Praktikan> call, Throwable t) {

                Toast.makeText(ScanActivity.this,
                        "Error is " + t.getMessage()
                        , Toast.LENGTH_LONG).show();
            }
        });

        Toast.makeText(this, result.getText(), Toast.LENGTH_SHORT).show();
        onBackPressed();
    }
}
