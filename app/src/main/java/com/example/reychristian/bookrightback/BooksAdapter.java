package com.example.reychristian.bookrightback;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Rey Christian on 11/3/2017.
 */

public class BooksAdapter extends CursorRecyclerViewAdapter<BooksAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Book> booksList;

    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements ItemClickListener, View.OnClickListener {
        TextView title, author, overflow;
        ImageView thumbnail;
        View container;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            author = (TextView) view.findViewById(R.id.author);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (TextView) view.findViewById(R.id.overflow);
            container = view.findViewById(R.id.container);

            view.setOnClickListener(this);
        }

        private ItemClickListener itemClickListener;

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view, int position) {
            itemClickListener.onClick(view, getAdapterPosition());
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition());
        }
    }

    public BooksAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_books, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(itemView);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, Cursor c) {

//        if (booksList.get(position).getId() == 6) {
//            holder.container.setBackgroundColor(Color.GREEN);
//        } else if (booksList.get(position).getId() == 7) {
//            holder.container.setBackgroundColor(Color.RED);
//        } else if (booksList.get(position).getId() == 8) {
//            holder.container.setBackgroundColor(Color.YELLOW);
//        }

        int id = c.getInt(c.getColumnIndex(Book.COLUMN_ID));
        String title = c.getString(c.getColumnIndex(Book.COLUMN_TITLE));
        String author = c.getString(c.getColumnIndex(Book.COLUMN_AUTHOR));
        String genre = c.getString(c.getColumnIndex(Book.COLUMN_GENRE));
        String publisher = c.getString(c.getColumnIndex(Book.COLUMN_PUBLISHER));
        String status = c.getString(c.getColumnIndex(Book.COLUMN_STATUS));

        byte[] image = c.getBlob(c.getColumnIndex(Book.COLUMN_IMAGE));
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);

        String datePublished = c.getString(c.getColumnIndex(Book.COLUMN_DATEPUBLISHED));

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = new Date();
        try {
            startDate = df.parse(datePublished);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setAuthor(author);
        book.setGenre(genre);
        book.setPublisher(publisher);
        book.setPublishedDate(startDate);
        book.setImage(image);
        book.setStatus(status);

        holder.title.setText(title);
        holder.author.setText("by " + author);
        holder.thumbnail.setImageBitmap(bitmap);

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, BookDetailsActivity.class);

                i.putExtra("bookId", book.getId());
                view.getContext().startActivity(i);
            }
        });

//        holder.setItemClickListener(new ItemClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//
//                String date = "";
//
//                Intent i = new Intent(context, BookDetailsActivity.class);
//
//                i.putExtra("bookId", book.getId());
//                i.putExtra("bookTitle", book.getTitle());
//                i.putExtra("bookAuthor", book.getAuthor());
//                i.putExtra("bookGenre", book.getGenre());
//                i.putExtra("bookPublisher", book.getPublisher());
//                i.putExtra("bookImage", book.getImage());

//                date = sdf.format(book.getPublishedDate());
//                i.putExtra("datePublished", date);
//
//                if (book.getDateBorrowed() == null) {
//                    i.putExtra("dateBorrowed", "N/A");
//                } else {
//                    date = sdf.format(book.getDateBorrowed());
//                    i.putExtra("dateBorrowed", date);
//                }
//
//                if (book.getDateDue() == null) {
//                    i.putExtra("dateDue", "N/A");
//                } else {
//                    date = sdf.format(book.getDateDue());
//                    i.putExtra("dateDue", date);
//                }

//                view.getContext().startActivity(i);
//            }
//        });

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // creating a popup menu
                PopupMenu popup = new PopupMenu(context, holder.overflow);


                // inflating menu from xml resource
                popup.inflate(R.menu.menu_popup);

                // adding a click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_edit:
                                Toast.makeText(context, "Edit clicked!", Toast.LENGTH_LONG).show();
                                String date = "";

                                Intent i = new Intent(context, EditBookActivity.class);
                                i.putExtra("bookTitle", book.getTitle());
                                i.putExtra("bookAuthor", book.getAuthor());
                                i.putExtra("bookGenre", book.getGenre());
                                i.putExtra("bookPublisher", book.getPublisher());
                                i.putExtra("bookImage", book.getImage());

                                date = sdf.format(book.getPublishedDate());
                                i.putExtra("datePublished", date);

                                if (book.getDateBorrowed() == null) {
                                    i.putExtra("dateBorrowed", "N/A");
                                } else {
                                    date = sdf.format(book.getDateBorrowed());
                                    i.putExtra("dateBorrowed", date);
                                }

                                if (book.getDateDue() == null) {
                                    i.putExtra("dateDue", "N/A");
                                } else {
                                    date = sdf.format(book.getDateDue());
                                    i.putExtra("dateDue", date);
                                }

                                context.startActivity(i);

                                break;
                            case R.id.action_delete:
                                Toast.makeText(context, "Delete clicked!", Toast.LENGTH_LONG).show();
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });

                // displaying the popup
                popup.show();
            }
        });
    }


}
