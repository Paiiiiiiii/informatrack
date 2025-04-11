package com.example.informatrack.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.informatrack.R;
import com.example.informatrack.model.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> userList;
    private OnApproveClickListener listener;

    public interface OnApproveClickListener {
        void onApproveClick(User user);
    }

    public UserAdapter(List<User> userList, OnApproveClickListener listener) {
        this.userList = userList;
        this.listener = listener;
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView tvIdUser, tvUsername;
        private Button btnApprove;

        public UserViewHolder(View itemView) {
            super(itemView);
            tvIdUser = itemView.findViewById(R.id.tvIdUser);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            btnApprove = itemView.findViewById(R.id.btnApprove);
        }

        public void bind(User user, OnApproveClickListener listener) {
            tvIdUser.setText("ID: " + user.getId_user());
            tvUsername.setText(user.getUsername());
            btnApprove.setOnClickListener(v -> listener.onApproveClick(user));
        }
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.bind(userList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}


