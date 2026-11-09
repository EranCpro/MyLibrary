package com.example.mylibrary;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

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

        EditText etBook = findViewById(R.id.etBook);
        EditText etAuthor = findViewById(R.id.etAuthor);
        EditText etPages = findViewById(R.id.etPages);
        Button btnSave = findViewById(R.id.btnSave);
        Button btnLoad = findViewById(R.id.btnLoad);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String book = etBook.getText().toString();
                if (book.isEmpty()) {
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

                Toast.makeText(MainActivity.this, "הכל תקין! הנתונים נשמרו בהצלחה", Toast.LENGTH_SHORT).show();






            }
        });




    }
}