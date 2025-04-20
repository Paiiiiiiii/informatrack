package com.example.informatrack.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.informatrack.api.ApiUpload;
import com.example.informatrack.api.GetDatabase;
import com.example.informatrack.model.ApiResponUpload;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JadwalViewModel extends ViewModel {

    private final MutableLiveData<ApiResponUpload> uploadResponse = new MutableLiveData<>();
    private final MutableLiveData<ApiResponUpload> processResponse = new MutableLiveData<>();

    public LiveData<ApiResponUpload> getUploadResponse() {
        return uploadResponse;
    }

    public LiveData<ApiResponUpload> getProcessResponse() {
        return processResponse;
    }

    public void uploadJadwal(MultipartBody.Part file, RequestBody keterangan) {
        ApiUpload apiUpload = GetDatabase.MyConectionApi().create(ApiUpload.class);
        Call<ApiResponUpload> call = apiUpload.uploadPdf(file, keterangan);

        call.enqueue(new Callback<ApiResponUpload>() {
            @Override
            public void onResponse(Call<ApiResponUpload> call, Response<ApiResponUpload> response) {
                if (response.isSuccessful() && response.body() != null) {
                    uploadResponse.setValue(response.body());
                } else {
                    Log.e("Upload Gagal", "onFailure: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ApiResponUpload> call, Throwable t) {
                Log.e("Koneksi Gagal", "onFailure: ", t);
                t.printStackTrace();
            }
        });
    }

    public void processPdf(String fileName) {
        ApiUpload apiUpload = GetDatabase.MyConectionApi().create(ApiUpload.class);
        Call<ApiResponUpload> call = apiUpload.processPdf(fileName);

        call.enqueue(new Callback<ApiResponUpload>() {
            @Override
            public void onResponse(Call<ApiResponUpload> call, Response<ApiResponUpload> response) {
                if (response.isSuccessful() && response.body() != null) {
                    processResponse.setValue(response.body());
                } else {
                    Log.e("Proses Gagal", "onFailure: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ApiResponUpload> call, Throwable t) {
                Log.e("Koneksi Gagal", "onFailure: ", t);
                t.printStackTrace();
            }
        });
    }
}