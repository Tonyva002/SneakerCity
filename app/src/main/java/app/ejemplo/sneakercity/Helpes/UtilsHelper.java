package app.ejemplo.sneakercity.Helpes;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UtilsHelper {
    public static DatabaseReference getDatabase(){
        return FirebaseDatabase.getInstance().getReference();
    }

    public static String getUserId(){
        return FirebaseAuth.getInstance().getUid();
    }
}
