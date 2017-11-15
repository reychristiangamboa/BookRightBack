package com.example.reychristian.bookrightback;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Rey Christian on 11/3/2017.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Book> booksList;

    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title_history, tv_author_history, tv_db_history, tv_dd_history, tv_dr_history;
        ImageView iv_history;

        public MyViewHolder(View view) {
            super(view);
            tv_title_history = (TextView) view.findViewById(R.id.tv_title_history);
            tv_author_history = (TextView) view.findViewById(R.id.tv_author_history);
            tv_db_history = (TextView) view.findViewById(R.id.tv_db_history);
            tv_dd_history = (TextView) view.findViewById(R.id.tv_dd_history);
            tv_dr_history = (TextView) view.findViewById(R.id.tv_dr_history);
            iv_history = (ImageView) view.findViewById(R.id.iv_history);
        }
    }

    public HistoryAdapter(Context context, ArrayList<Book> booksList) {
        this.context = context;
        this.booksList = booksList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_history, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(itemView);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final Book book = booksList.get(position);

        String date = "";

        holder.tv_title_history.setText(book.getTitle());
        holder.tv_author_history.setText("by " + book.getAuthor());

        date = sdf.format(book.getDateBorrowed());
        holder.tv_db_history.setText(date);

        date = sdf.format(book.getDateDue());
        holder.tv_dd_history.setText(date);

        date = sdf.format(book.getDateReturned());
        holder.tv_dr_history.setText(date);

        // loading album cover using Glide library
        Glide.with(context).load(book.getImage()).into(holder.iv_history);

    }


    @Override
    public int getItemCount() {
        return booksList.size();
    }

}
