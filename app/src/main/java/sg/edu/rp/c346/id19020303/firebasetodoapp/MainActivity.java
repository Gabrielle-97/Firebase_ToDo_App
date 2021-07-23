package sg.edu.rp.c346.id19020303.firebasetodoapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MainActivity extends AppCompatActivity {

    private TextView tvTitle;
    private EditText etTitle;
    private TextView tvDate;
    private EditText etDate;
    private Button btnAdd;

    private FirebaseFirestore db;
    private CollectionReference colRef;
    private DocumentReference docRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTitle = findViewById(R.id.textViewTitle);
        etTitle = findViewById(R.id.editTextTitle);
        tvDate = findViewById(R.id.textViewDate);
        etDate = findViewById(R.id.editTextDate);

        btnAdd = findViewById(R.id.buttonAdd);

        db = FirebaseFirestore.getInstance();
        colRef = db.collection("todos");
        docRef = colRef.document("todo");
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException error) {

                if (error != null){
                    return;
                }
                if(snapshot != null && snapshot.exists()){

                    Message msg = snapshot.toObject(Message.class);//use POJO
                    tvTitle.setText(msg.getTitle());
                    tvDate.setText("Date: " + msg.getDate());

                }

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAddOnClick(v);
            }


        });


    }

    private void btnAddOnClick(View v) {
//        String text = etMessage.getText().toString();
//        docRef.update("text", text, "color", "red");

        String title = etTitle.getText().toString();
        String date = etDate.getText().toString();
        Message msg = new Message(title, date);
        docRef.set(msg);

    }

}