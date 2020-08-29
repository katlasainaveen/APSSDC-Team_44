package com.example.recipeapp2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Add_Recipe extends AppCompatActivity {

    ImageButton back;
    ImageView imageView1, imageView2;
    Spinner code;
    EditText name, description, notes, nutrition, prepareationtime, cooktime, totaltime, rating, ingredients, procedure;
    Button submit;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__recipe);

        back = findViewById(R.id.back_btn_breakfest2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imageView1 = findViewById(R.id.news_add_image);
        imageView1.setClickable(true);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                if (intent.resolveActivity(getPackageManager()) != null) {
//                    startActivityForResult(intent, 101);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"),101);
                }
            }
        });

        imageView2 = findViewById(R.id.news_add_wimage);
        imageView2.setClickable(true);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                if (intent.resolveActivity(getPackageManager()) != null) {
//                    startActivityForResult(intent, 101);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"),102);
                }
            }
        });

        progressBar = findViewById(R.id.progress_bar3);
        progressBar.setVisibility(View.INVISIBLE);

        ingredients = findViewById(R.id.gate1pw1asqs2_greater_reason);
        final List<String> res = Arrays.asList(ingredients.getText().toString().split("[,]", 0));

        //tower
        final String[] code_values = {"Code", "Breakfast", "Lunch", "Snacks", "Dinner"};
        code = findViewById(R.id.gatepass_greater_tower_spinner);
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(Add_Recipe.this, android.R.layout.simple_spinner_dropdown_item, code_values);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        code.setAdapter(adapter4);

        name = findViewById(R.id.gatepass_greater_datein);
        description = findViewById(R.id.gatepass_greater_timeout);
        notes = findViewById(R.id.gatepass_greater_place);
        nutrition = findViewById(R.id.gatepass_greater_reason);
        prepareationtime = findViewById(R.id.gatepasqs_greater_reason);
        cooktime = findViewById(R.id.gate1pasqs_greater_reason);
        totaltime = findViewById(R.id.gate1p1asqs_greater_reason);
        rating = findViewById(R.id.gate1p1asqs2_greater_reason);
        procedure = findViewById(R.id.gate1p1asqs2_gr1eater_reason);

        submit = findViewById(R.id.logout_btn_2);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                final String name_txt = name.getText().toString();
                final String desc = description.getText().toString();
                final String note = notes.getText().toString();
                final String nutrients = nutrition.getText().toString();
                final String prepare = prepareationtime.getText().toString();
                final String cook = cooktime.getText().toString();
                final String total = totaltime.getText().toString();
                final String rating_txt = rating.getText().toString();
                final String proce = procedure.getText().toString();
                final String code_txt = code.getSelectedItem().toString();

                if (name_txt.isEmpty() | desc.isEmpty() | note.isEmpty() | nutrients.isEmpty() | prepare.isEmpty() | cook.isEmpty() |
                        total.isEmpty() | rating_txt.isEmpty() | proce.isEmpty() | code_txt.isEmpty()) {
                    Toast.makeText(Add_Recipe.this, "Required Details are Missing", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
                else {
                    final StorageReference ref = FirebaseStorage.getInstance().getReference()
                            .child("News_Images")
                            .child(name_txt + "1");

                    final StorageReference ref2 = FirebaseStorage.getInstance().getReference()
                            .child("News_Images")
                            .child(name_txt + "2");

                    imageView1.buildDrawingCache();
                    Bitmap bitmap = ((BitmapDrawable) imageView1.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
                    final byte[] data = baos.toByteArray();

                    imageView2.buildDrawingCache();
                    Bitmap bitmap2 = ((BitmapDrawable) imageView2.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
                    bitmap2.compress(Bitmap.CompressFormat.JPEG, 80, baos2);
                    final byte[] data2 = baos2.toByteArray();

                    UploadTask uploadTask = ref.putBytes(data);
                    uploadTask
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(Add_Recipe.this, "Image Upload Failed", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            })
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    UploadTask uploadTask2 = ref2.putBytes(data2);
                                    uploadTask2
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception exception) {
                                                    progressBar.setVisibility(View.INVISIBLE);
                                                    Toast.makeText(Add_Recipe.this, "Image Upload Failed", Toast.LENGTH_SHORT).show();
                                                    return;
                                                }
                                            })
                                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                        @Override
                                                        public void onSuccess(Uri uri1) {
                                                            final String imageurl1 = String.valueOf(uri1);

                                                            ref2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                @Override
                                                                public void onSuccess(Uri uri2) {
                                                                    String imageurl2 = String.valueOf(uri2);

                                                                    final long timestamp = new MyDate().timestamp();
                                                                    String name_to_doc = name_txt + "-" + timestamp;

                                                                    final Map<String, Object> data1 = new HashMap<>();
                                                                    data1.put("Name", name_txt);
                                                                    data1.put("Notes", note);
                                                                    data1.put("Nutrition", nutrients);
                                                                    data1.put("PreparationTime", prepare);
                                                                    data1.put("Procedure", proce);
                                                                    data1.put("cooktime", cook);
                                                                    data1.put("totaltime", total);
                                                                    data1.put("image_url_1", imageurl1);
                                                                    data1.put("image_url_2", imageurl2);
                                                                    data1.put("code", code_txt);
                                                                    data1.put("Rating", Integer.parseInt(rating_txt));
                                                                    data1.put("Description", desc);
                                                                    data1.put("Ingredients", res);
                                                                    data1.put("key", name_to_doc);

                                                                    FirebaseFirestore.getInstance().collection(code_txt).document(name_to_doc)
                                                                            .set(data1)
                                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                @Override
                                                                                public void onSuccess(Void aVoid) {
                                                                                    Toast.makeText(Add_Recipe.this, "Its Live", Toast.LENGTH_SHORT).show();
                                                                                    finish();
                                                                                }
                                                                            })
                                                                            .addOnFailureListener(new OnFailureListener() {
                                                                                @Override
                                                                                public void onFailure(@NonNull Exception e) {
                                                                                    Toast.makeText(Add_Recipe.this, "Upload Failed", Toast.LENGTH_SHORT).show();
                                                                                    return;
                                                                                }
                                                                            });
                                                                    progressBar.setVisibility(View.INVISIBLE);
                                                                }
                                                            });
                                                        }
                                                    });
                                                }
                                            });
                                }
                            });
                }

            }
        });


















    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                        imageView1.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED)  {
                Toast.makeText(Add_Recipe.this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == 102) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                        imageView2.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED)  {
                Toast.makeText(Add_Recipe.this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }


//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
//            Uri selectedImage = data.getData();
//            String[] filePathColumn = { MediaStore.Images.Media.DATA };
//            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
//            cursor.moveToFirst();
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            String picturePath = cursor.getString(columnIndex);
//            cursor.close();
//            imageView1.setImageBitmap(BitmapFactory.decodeFile(picturePath));
//        }
//        else if (requestCode == 2 && resultCode == RESULT_OK && null != data) {
//            Uri selectedImage = data.getData();
//            String[] filePathColumn = { MediaStore.Images.Media.DATA };
//            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
//            cursor.moveToFirst();
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            String picturePath = cursor.getString(columnIndex);
//            cursor.close();
//            imageView2.setImageBitmap(BitmapFactory.decodeFile(picturePath));
//        }
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode != RESULT_CANCELED) {
//            switch (requestCode) {
//                case 101:
//                    if (resultCode == RESULT_OK && data != null) {
//                        Uri selectedImage = data.getData();
//                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                        if (selectedImage != null) {
//                            Cursor cursor = getContentResolver().query(selectedImage,
//                                    filePathColumn, null, null, null);
//                            if (cursor != null) {
//                                cursor.moveToFirst();
//
//                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                                String picturePath = cursor.getString(columnIndex);
//                                imageView1.setImageBitmap(BitmapFactory.decodeFile(picturePath));
//                                cursor.close();
//                            }
//                        }
//
//                    }
//
//                    break;
//                case 102:
//                    if (resultCode == RESULT_OK && data != null) {
//                        Uri selectedImage = data.getData();
//                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                        if (selectedImage != null) {
//                            Cursor cursor = getContentResolver().query(selectedImage,
//                                    filePathColumn, null, null, null);
//                            if (cursor != null) {
//                                cursor.moveToFirst();
//
//                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                                String picturePath = cursor.getString(columnIndex);
//                                imageView2.setImageBitmap(BitmapFactory.decodeFile(picturePath));
//                                cursor.close();
//                            }
//                        }
//
//                    }
//
//                    break;
//            }
//        }
//    }





















}