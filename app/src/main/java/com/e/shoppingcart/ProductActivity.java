package com.e.shoppingcart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.net.URL;

public class ProductActivity extends AppCompatActivity {

    private DatabaseReference dbRef;
    //private StorageReference storageRef;

    private Bitmap bmp;
    private ImageView img;

    private class GetImageTask extends AsyncTask<Void, Void, Void> {
        ModelProduct prod;
        TextView txtName;
        ImageView img;

        public GetImageTask(ModelProduct prod, TextView txtName, ImageView img) {
            this.txtName = txtName;
            this.img = img;
            this.prod = prod;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL url = new URL(prod.imgUrl);
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch(Exception e) {
                e.printStackTrace();
            }

            try {
                URL url = new URL("https://firebasestorage.googleapis.com/v0/b/store-160a0.appspot.com/o/adidas.jpg?alt=media&token=1fca85c0-a408-4c54-bffd-7f083a04373c");
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(Void result) {
            txtName.setText(prod.name);
            img.setImageBitmap(bmp);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        //storageRef = FirebaseStorage.getInstance().getReference("Images");
        dbRef = FirebaseDatabase.getInstance().getReference().child("Product");

        final LinearLayout layout = findViewById(R.id.layoutProdImages);
        final LayoutInflater inflater = LayoutInflater.from(this);

        dbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ModelProduct prod = dataSnapshot.getValue(ModelProduct.class);
                System.out.println(prod);

                View view = inflater.inflate(R.layout.product_item, layout, false);
                TextView txtName = view.findViewById(R.id.txtviewProdName);
                ImageView img = view.findViewById(R.id.imgviewProdTest);
                new GetImageTask(prod, txtName, img).execute();
                layout.addView(view);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //imgView = findViewById(R.id.imgviewProdTest);
        //new GetImageTask().execute();

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
