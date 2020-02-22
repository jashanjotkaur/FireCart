package com.e.shoppingcart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {

    private DatabaseReference dbRef;
    private StorageReference storageRef;
    private ImageView imgView;

    private Bitmap bmp;
    private class GetImageTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL url = new URL("https://firebasestorage.googleapis.com/v0/b/store-160a0.appspot.com/o/adidas.jpg?alt=media&token=1fca85c0-a408-4c54-bffd-7f083a04373c");
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(Void result) {
            imgView.setImageBitmap(bmp);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        storageRef = FirebaseStorage.getInstance().getReference("Images");
        dbRef = FirebaseDatabase.getInstance().getReference().child("Product");
        imgView = findViewById(R.id.imgviewProdTest);
        new GetImageTask().execute();

        /*List<String> sizes = new ArrayList<String>();
        sizes.add("small");
        sizes.add("medium");
        sizes.add("large");
        ModelProduct obj = new ModelProduct("Nike Shoes", sizes, 2000, "");
        dbRef.push().setValue(obj);*/


        //Uri file = Uri.fromFile(new File("F:\\Development\\Android_Studio_Ws\\tmp\\nike.jpg"));
        //StorageReference riversRef = storageRef.child("images/nike.jpg");
        //riversRef.putFile(file);
            /*.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Get a URL to the uploaded content
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    // ...
                }
            });*/

        /*Button btnUpload = findViewById(R.id.btnProdUpload);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });
        */
    }

    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            //Uri imguri = data.getData();
            //ContentResolver ct = getContentResolver();
            //MimeTypeMap mtm = MimeTypeMap.getSingleton();
            //String ext = mtm.getExtensionFromMimeType(ct.getType(imguri));

            //StorageReference tmp = storageRef.child(System.currentTimeMillis() + "." + ext);

        }
    }*/
}
