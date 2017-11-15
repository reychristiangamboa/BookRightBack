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

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditBookActivity extends AppCompatActivity {

    ImageButton ibEditBook;
    ImageView ivImageE;
    Button bTakePhotoE, bGalleryE;
    EditText etTitleE, etAuthorE, etPublisherE, etDatePublishedE;
    boolean hasTitle = false, hasAuthor = false, hasPublisher = false, hasDatePublished = false;

    String spinnerValue = "";
    Date javaDate;

    Bitmap thumbnail;


    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int SELECT_FILE = 2;

    Spinner sGenreE;
    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        ibEditBook = (ImageButton) findViewById(R.id.ibEditBook);
        ibEditBook.setEnabled(false);

        ivImageE = (ImageView) findViewById(R.id.ivImageE);

        bTakePhotoE = (Button) findViewById(R.id.bTakePhotoE);
        bTakePhotoE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchCamera(view);
            }
        });

        bGalleryE = (Button) findViewById(R.id.bGalleryE);
        bGalleryE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchGallery(view);
            }
        });

        if (!hasCamera()) {
            bTakePhotoE.setClickable(false);
            bTakePhotoE.setEnabled(false);
        }

        etTitleE = (EditText) findViewById(R.id.etTitleE);
        etAuthorE = (EditText) findViewById(R.id.etAuthorE);
        etPublisherE = (EditText) findViewById(R.id.etPublisherE);


        sGenreE = (Spinner) findViewById(R.id.sGenreE);
        adapter = ArrayAdapter.createFromResource(getBaseContext(), R.array.genres, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sGenreE.setAdapter(adapter);
        sGenreE.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               spinnerValue = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final Calendar myCalendar = Calendar.getInstance();
        etDatePublishedE = (EditText) findViewById(R.id.etDatePublishedE);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                javaDate = myCalendar.getTime();

                String myFormat = "MMM dd, yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

                etDatePublishedE.setText(sdf.format(myCalendar.getTime()));
            }
        };


        etDatePublishedE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(EditBookActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        String title = getIntent().getExtras().getString("bookTitle");
        String author = getIntent().getExtras().getString("bookAuthor");
        String publisher = getIntent().getExtras().getString("bookPublisher");
        String datePublished = getIntent().getExtras().getString("datePublished");
        int image = getIntent().getExtras().getInt("bookImage");

        ivImageE.setImageResource(image);
        etTitleE.setText(title);
        etAuthorE.setText(author);
        etPublisherE.setText(publisher);
        etDatePublishedE.setText(datePublished);



        etTitleE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("") || s.toString().trim().length() == 0) {
                    hasTitle = true;
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etAuthorE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("") || s.toString().trim().length() == 0) {
                    hasAuthor = true;
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etPublisherE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("") || s.toString().trim().length() == 0) {
                    hasPublisher = true;
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etDatePublishedE.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("") || s.toString().trim().length() == 0) {
                    hasDatePublished = true;
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        if (hasTitle && hasAuthor && hasPublisher && hasDatePublished) {
            ibEditBook.setEnabled(true);
        }

        ibEditBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                ivImageE.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    private void onCaptureImageResult(Intent data){
        thumbnail = (Bitmap) data.getExtras().get("data");
        ivImageE.setImageBitmap(thumbnail);
    }
}
