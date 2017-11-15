package com.example.reychristian.bookrightback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.text.SimpleDateFormat;

public class BookDetailsActivity extends AppCompatActivity {

    FloatingActionMenu fam_book_details;
    FloatingActionButton fab_borrow, fab_renew, fab_return;
    ImageView iv_image;
    TextView tv_title, tv_author, tv_genre, tv_publisher, tv_datePublished, tv_dateBorrowed, tv_dateDue;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        iv_image = (ImageView) findViewById(R.id.iv_image);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_author = (TextView) findViewById(R.id.tv_author);
        tv_genre = (TextView) findViewById(R.id.tv_genre);
        tv_publisher = (TextView) findViewById(R.id.tv_publisher);
        tv_datePublished = (TextView) findViewById(R.id.tv_datePublished);
        tv_dateBorrowed = (TextView) findViewById(R.id.tv_dateBorrowed);
        tv_dateDue = (TextView) findViewById(R.id.tv_dateDue);

        fam_book_details = (FloatingActionMenu) findViewById(R.id.fam_book_details);
        fab_borrow = (FloatingActionButton) findViewById(R.id.fab_borrow);
        fab_renew = (FloatingActionButton) findViewById(R.id.fab_renew);
        fab_return = (FloatingActionButton) findViewById(R.id.fab_return);

//        String title = getIntent().getExtras().getString("bookTitle");
//        String author = getIntent().getExtras().getString("bookAuthor");
//        String genre = getIntent().getExtras().getString("bookGenre");
//        String publisher = getIntent().getExtras().getString("bookPublisher");
//        String datePublished = getIntent().getExtras().getString("datePublished");
//        String dateBorrowed = getIntent().getExtras().getString("dateBorrowed");
//        String dateDue = getIntent().getExtras().getString("dateDue");
//        int image = getIntent().getExtras().getInt("bookImage");

        int id = getIntent().getExtras().getInt("bookId");

        databaseHelper = new DatabaseHelper(getBaseContext());

        Book b = databaseHelper.viewBook(id);

        Bitmap bitmap = BitmapFactory.decodeByteArray(b.getImage(), 0, b.getImage().length);

        iv_image.setImageBitmap(bitmap);
        tv_title.setText(b.getTitle());
        tv_author.setText("by " + b.getAuthor());
        tv_genre.setText(b.getGenre());
        tv_publisher.setText(b.getPublisher());

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");

        tv_datePublished.setText(sdf.format(b.getPublishedDate()));
        tv_dateBorrowed.setText("N/A");
        tv_dateDue.setText("N/A");

        if(b.getStatus().equals("AVAILABLE")){
            fab_borrow.setVisibility(View.VISIBLE);
            fab_renew.setVisibility(View.GONE);
            fab_return.setVisibility(View.GONE);
        }else if(b.getStatus().equals("BORROWED")){
            fab_renew.setVisibility(View.VISIBLE);
            fab_return.setVisibility(View.VISIBLE);
            fab_borrow.setVisibility(View.GONE);
        }

        fab_borrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BorrowDialog bd = new BorrowDialog();
                bd.show(getSupportFragmentManager(), "");
            }
        });

        fab_renew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RenewDialog rd = new RenewDialog();
                rd.show(getSupportFragmentManager(), "");
            }
        });

    }
}
