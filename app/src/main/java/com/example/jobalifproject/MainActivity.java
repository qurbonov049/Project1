package com.example.jobalifproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.security.cert.CertificateException;
import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Model> mList = new ArrayList<>();
    private ModelAdapter modelAdapter;
    ArrayList<Model> models = new ArrayList<>();
    private static Retrofit retrofit;
    private RecyclerView recyclerView;
    boolean isLoading = false;
    public static String BASE_URL = "https://guidebook.com";

    private ViewModelRoom mViewModelRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        mViewModelRoom = new ViewModelProvider(this).get(ViewModelRoom.class);
        populateData();
        initScrollListener();
    }

    private void populateData() {
        RequestInterface request = ApiClient1().create(RequestInterface.class);
        retrofit2.Call<String> call = request.getJSON();
        call.enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(retrofit2.Call<String> call, retrofit2.Response<String> response) {
                Log.i("Rustam", response.body());
                JSONResponse data;
                Gson gson = new Gson();
                data = gson.fromJson(response.body(), JSONResponse.class);
                mList.addAll(data.getResults());
                getRun();
            }

            @Override
            public void onFailure(retrofit2.Call<String> call, Throwable t) {
                Log.i("RTF", t.getMessage());


            }
        });
    }

    private void getRun() {

        for (int i = 0; i < 3; i++) {
            Model model = new Model();
            model.setUrl(mList.get(i).getUrl());
            model.setEndDate(mList.get(i).getEndDate());
            model.setName(mList.get(i).getName());
            model.setIcon(mList.get(i).getIcon());

            models.add(model);
            mViewModelRoom.insert(model);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        modelAdapter = new ModelAdapter(models, this);
        recyclerView.setAdapter(modelAdapter);


    }

    private void initScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == models.size() - 1) {
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });
    }

    private void loadMore() {
        if (models.size() <= mList.size() - 1) {
            models.add(null);
            modelAdapter.notifyItemInserted(models.size() - 1);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void run() {
                    models.remove(models.size() - 1);
                    int scrollPosition = models.size();
                    modelAdapter.notifyItemRemoved(scrollPosition);
                    int currentSize = scrollPosition;
                    int nextLimit = currentSize + 2;

                    while (currentSize - 1 < nextLimit) {
                        Model model = new Model();
                        model.setUrl(mList.get(currentSize).getUrl());
                        model.setEndDate(mList.get(currentSize).getEndDate());
                        model.setName(mList.get(currentSize).getName());
                        model.setIcon(mList.get(currentSize).getIcon());

                        models.add(model);
                        mViewModelRoom.insert(model);
                        currentSize++;
                    }

                    modelAdapter.notifyDataSetChanged();
                    isLoading = false;
                }
            }, 2500);
        } else
            Toast.makeText(MainActivity.this, "Loading data completed", Toast.LENGTH_SHORT).show();
    }

    private Retrofit ApiClient1() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getUnsafeOkHttpClient().build())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static OkHttpClient.Builder getUnsafeOkHttpClient() {

        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}