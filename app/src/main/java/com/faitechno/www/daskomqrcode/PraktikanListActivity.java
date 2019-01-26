package com.faitechno.www.daskomqrcode;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PraktikanListActivity extends AppCompatActivity {

    private List<Praktikan> praktikanList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PraktikanAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_praktikan_list);

        String ipaddr = getIntent().getStringExtra("EXTRA_IPADDR");

        recyclerView = findViewById(R.id.recycler_view);

        final ProgressDialog progressDialog = new ProgressDialog(PraktikanListActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        ApiManager apiManager;
        apiManager = ApiManager.getInstance(ipaddr);
        apiManager.getAllPraktikanData(new Callback<List<Praktikan>>() {
            @Override
            public void onResponse(@NonNull Call<List<Praktikan>> call, @NonNull Response<List<Praktikan>> response) {
                if (response.body() != null) {

                    Log.d("ERRORUY", "onResponse: "+response.body().toString());

                    mAdapter = new PraktikanAdapter(response.body());
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(mAdapter);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Praktikan>> call, @NonNull Throwable t) {

                progressDialog.dismiss();
                Log.d("ERRORUY", "onFailure: "+t.getMessage());
                Toast.makeText(PraktikanListActivity.this, "Aww something is happening sorry", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
