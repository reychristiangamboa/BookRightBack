package com.example.reychristian.bookrightback;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddBookActivity extends AppCompatActivity {

    ImageButton ibAddBook;
    ImageView ivImage;
    Button bTakePhoto, bGallery;
    EditText etTitle, etAuthor, etPublisher, etDatePublished;

    Date javaDate;

    String spinnerValue = "";

    DatabaseHelper databaseHelper;

    Bitmap thumbnail;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int SELECT_FILE = 2;

    Spinner sGenre;
    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        ibAddBook = (ImageButton) findViewById(R.id.ibAddBook);

        ivImage = (ImageView) findViewById(R.id.ivImage);

        bTakePhoto = (Button) findViewById(R.id.bTakePhoto);
        bTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchCamera(view);
            }
        });

        bGallery = (Button) findViewById(R.id.bGallery);
        bGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchGallery(view);
            }
        });

        if (!hasCamera()) {
            bTakePhoto.setClickable(false);
            bTakePhoto.setEnabled(false);
        }

        etTitle = (EditText) findViewById(R.id.etTitle);
        etAuthor = (EditText) findViewById(R.id.etAuthor);
        etPublisher = (EditText) findViewById(R.id.etPublisher);


        sGenre = (Spinner) findViewById(R.id.sGenre);
        adapter = ArrayAdapter.createFromResource(getBaseContext(), R.array.genres, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sGenre.setAdapter(adapter);
        sGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), parent.getItemAtPosition(position) + " selected", Toast.LENGTH_LONG).show();
                spinnerValue = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final Calendar myCalendar = Calendar.getInstance();
        etDatePublished = (EditText) findViewById(R.id.etDatePublished);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                javaDate = myCalendar.getTime();

                String myFormat = "MMM dd, yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

                etDatePublished.setText(sdf.format(myCalendar.getTime()));
            }
        };


        etDatePublished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddBookActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        final String genreString = (String) sGenre.getSelectedItem();

        ibAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            // ADD BOOK here

                databaseHelper = new DatabaseHelper(getBaseContext());

                Book book = new Book();
                book.setTitle(etTitle.getText().toString());
                book.setAuthor(etAuthor.getText().toString());
                book.setGenre(spinnerValue);
                book.setPublisher(etPublisher.getText().toString());
                book.setPublishedDate(javaDate);
                book.setStatus("AVAILABLE");

                ivImage.setDrawingCacheEnabled(true);
                ivImage.buildDrawingCache();
                Bitmap bitmap = ivImage.getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                book.setImage(baos.toByteArray());

                databaseHelper.addBook(book);

                Toast.makeText(getBaseContext(), "Book added!", Toast.LENGTH_SHORT).show();

                finish();

            }
        });
    }

    // check if user's phone has a camera
    private boolean hasCamera() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    // launching the camera
    public void launchCamera(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Take a picture and pass results along to onActivityResult
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    // launching the gallery
    public void launchGallery(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    // If you want to return the image taken
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // GET the PHOTO
//            Bundle extras = data.getExtras();
//            Bitmap photo = (Bitmap) extras.get("data");
//            ivImage.setImageBitmap(photo);
            onCaptureImageResult(data);

        } else if (requestCode == SELECT_FILE && resultCode == RESULT_OK) {
            // GET the PHOTO
//            Uri selectedImageUri = data.getData();
//            ivImage.setImageURI(selectedImageUri);
            try{
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                ivImage.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    private void onCaptureImageResult(Intent data){
        thumbnail = (Bitmap) data.getExtras().get("data");
        ivImage.setImageBitmap(thumbnail);
    }
}
