package app.taptik.video.downloader.videodownloader.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // them
        findViewById(R.id.btnOpen).setOnClickListener(v ->{
            Map<String, Object> user = new HashMap<>();
            user.put("first", "Ada");
            user.put("last", "Lovelace");
            user.put("born", 1815);

            //Add a new document with a generated ID
            db.collection("users")
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                            Toast.makeText(MainActivity.this, "OK", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "KO KO KO", Toast.LENGTH_SHORT).show();
                            Log.w("TAG", "Error adding document", e);
                        }
                    });
        });
        // sua id la id cua user
        findViewById(R.id.btnUpdate).setOnClickListener(v->{
            db.collection("users").document(
                            "1rSEMNhDysyOhpZg54aO").
                    update("first","34243242",
                            "last","laslsdddd","born",23423423)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(MainActivity.this, "Thanh Cong!!", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
        // xoa id là gía trị id của user
        findViewById(R.id.btnDelete).setOnClickListener(v->{
            db.collection("users").document("1rSEMNhDysyOhpZg54aO").delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(MainActivity.this, "Thanh Cong!!", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
        
        // lay danh sach
        findViewById(R.id.btnGetList).setOnClickListener(v ->{
            db.collection("users")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                ArrayList<User> users = new ArrayList<>();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d("TAG", document.getId() + " => " + document.getData());
                                    User user = new User(document.getId(),
                                            document.getData().get("first").toString(),
                                            document.getData().get("last").toString(),
                                            document.getData().get("born").toString());
                                    users.add(user);
                                }
                            } else {
                                Log.w("TAG", "Error getting documents.", task.getException());
                            }
                        }
                    });
        });




    }
}