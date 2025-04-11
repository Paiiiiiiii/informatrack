package com.example.informatrack.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.informatrack.api.ApiAuth;
import com.example.informatrack.api.GetDatabase;
import com.example.informatrack.model.ApiResponse;
import com.example.informatrack.model.User;
import com.example.informatrack.model.UserList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminViewModel extends ViewModel {

    private final MutableLiveData<List<User>> unapprovedUsers = new MutableLiveData<>();
    private final MutableLiveData<String> toastMessage = new MutableLiveData<>();

    private final ApiAuth api = GetDatabase.MyConectionApi().create(ApiAuth.class);

    public LiveData<List<User>> getUnapprovedUsers() {
        return unapprovedUsers;
    }

    public LiveData<String> getToastMessage() {
        return toastMessage;
    }

    public void fetchUnapprovedUsers() {
        api.getUnapprovedUsers().enqueue(new Callback<UserList>() {
            @Override
            public void onResponse(Call<UserList> call, Response<UserList> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getUser() != null) {
                    unapprovedUsers.setValue(response.body().getUser());
                } else {
                    toastMessage.setValue("Tidak ada data user ditemukan");
                }
            }

            @Override
            public void onFailure(Call<UserList> call, Throwable t) {
                toastMessage.setValue("Gagal mengambil data");
            }
        });
    }

    public void approveUser(User user) {
        api.approveUser(user.getId_user()).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    toastMessage.setValue("Berhasil approve " + user.getUsername());
                    fetchUnapprovedUsers(); // refresh
                } else {
                    toastMessage.setValue("Gagal approve user");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                toastMessage.setValue("Gagal approve user");
            }
        });
    }
}
