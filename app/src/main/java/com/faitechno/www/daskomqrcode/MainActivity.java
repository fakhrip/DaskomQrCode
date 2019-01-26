package com.faitechno.www.daskomqrcode;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class MainActivity extends AppCompatActivity {

    Button scanButton, cekListPraktikanButton, cekStatusPraktikan;
    EditText ipAddressET, nimPraktikanET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA},
                        1000);
                return;
            }
        }

        cekStatusPraktikan = findViewById(R.id.cek_status_praktikan);
        cekListPraktikanButton = findViewById(R.id.cek_list_praktikan);
        scanButton = findViewById(R.id.scanButton);
        ipAddressET = findViewById(R.id.editText);
        nimPraktikanET = findViewById(R.id.nim_praktikan_et);

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                intent.putExtra("EXTRA_IPADDR", ipAddressET.getText().toString());
                startActivity(intent);
            }
        });

        cekListPraktikanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PraktikanListActivity.class);
                intent.putExtra("EXTRA_IPADDR", ipAddressET.getText().toString());
                startActivity(intent);
            }
        });

        cekStatusPraktikan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Loading....");
                progressDialog.show();

                ApiManager apiManager;
                apiManager = ApiManager.getInstance(ipAddressET.getText().toString());
                apiManager.getPraktikanData(nimPraktikanET.getText().toString(), new Callback<Praktikan>() {
                    @Override
                    public void onResponse(@NonNull Call<Praktikan> call, @NonNull Response<Praktikan> response) {
                        if (response.body() != null) {
                            Toast.makeText(MainActivity.this, response.body().getNama()+"sudah berhasil mengumpulkan TP", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Why null? why not? :v", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(@NonNull Call<Praktikan> call, @NonNull Throwable t) {
                        progressDialog.dismiss();
                        Log.d("ERRORUY", "onFailure: "+t.getMessage());
                        Toast.makeText(MainActivity.this, "Aww something is happening sorry", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1000: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this, "Thank you for accepting the permission", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(this, "You cant use this app without accepting the permission", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
