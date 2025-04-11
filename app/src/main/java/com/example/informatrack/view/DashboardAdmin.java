package com.example.informatrack.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.informatrack.R;
import com.example.informatrack.adapter.UserAdapter;
import com.example.informatrack.model.User;
import com.example.informatrack.viewmodel.AdminViewModel;

import java.util.ArrayList;
import java.util.List;

public class DashboardAdmin extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private List<User> userList = new ArrayList<>();
    private AdminViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin);

        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdapter(userList, this::approveUser);
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(AdminViewModel.class);

        // Observe data user yang belum di-approve
        viewModel.getUnapprovedUsers().observe(this, users -> {
            userList.clear();
            userList.addAll(users);
            adapter.notifyDataSetChanged();
        });

        // Observe toast message
        viewModel.getToastMessage().observe(this, msg -> {
            if (msg != null) Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        });

        viewModel.fetchUnapprovedUsers();
    }

    private void approveUser(User user) {
        viewModel.approveUser(user);
    }
}