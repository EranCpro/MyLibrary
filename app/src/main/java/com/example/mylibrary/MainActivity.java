package com.example.mylibrary;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db;
    EditText etBook, etAuthor, etPages;
    TextView tvStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });





        db = FirebaseFirestore.getInstance();

        etBook = findViewById(R.id.etBook);
        etAuthor = findViewById(R.id.etAuthor);
        etPages = findViewById(R.id.etPages);
        Button btnSave = findViewById(R.id.btnSave);
        Button btnLoad = findViewById(R.id.btnLoad);
        tvStatus = findViewById(R.id.tvStatus);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = etBook.getText().toString();
                if (name.isEmpty()) {
                    etBook.setError("book name required");
                    etBook.requestFocus();
                    return;
                }

                String author = etAuthor.getText().toString();
                if (author.isEmpty()) {
                    etAuthor.setError("author name required");
                    etAuthor.requestFocus();
                    return;
                }

                String pages = etPages.getText().toString();
                if (pages.isEmpty()) {
                    etPages.setError("number of pages required");
                    etPages.requestFocus();
                    return;
                }

                Map<String, Object> book = new HashMap<>();
                book.put("title", etBook.getText().toString());
                book.put("author", etAuthor.getText().toString());
                int pagess = Integer.parseInt(etPages.getText().toString());
                book.put("pages", pagess);

                tvStatus.setText("saving...");

                db.collection("books").document("current").set(book)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                tvStatus.setText("saved on cloud");
                                Toast.makeText(MainActivity.this, "book  saved", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                tvStatus.setText("saving failed: " + e.getMessage());
                            }
                        });

            }
        });

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvStatus.setText("Loading...");

                db.collection("books").document("current").get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot doc) {

                                if (!doc.exists()) {
                                    tvStatus.setText("No books saved yet");
                                    return;
                                }

                                etBook.setText(doc.getString("title"));
                                etAuthor.setText(doc.getString("author"));
                                tvStatus.setText("Loaded from the cloud");

                                Long pages = doc.getLong("pages");
                                if (pages != null) {
                                    etPages.setText(String.valueOf(pages));
                                } else {
                                    etPages.setText("");
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                tvStatus.setText("Loading failed: " + e.getMessage());
                            }
                        });
            }
        });




    }
}