package com.example.simplebackgrouptask;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {
    private Context context;
    private List<User> userList;

    public UserListAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User currentUser = userList.get(position);
        holder.textViewId.setText(String.valueOf(currentUser.id));
        holder.textViewName.setText(currentUser.name);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;
        private UserListAdapter adapter;
        private TextView textViewId;

        public UserViewHolder(@NonNull View itemView, UserListAdapter adapter) {
            super(itemView);
            this.textViewId = itemView.findViewById(R.id.textViewId);
            this.textViewName = itemView.findViewById(R.id.textViewName);

            this.textViewName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UserListActivity.showUserDetail(view, Integer.parseInt(textViewId.getText().toString()));
                }
            });

            this.adapter = adapter;
        }
    }
}
