package todday.funny.seoulcatcher.viewmodel;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ScheduleViewModel extends BaseViewModel {

    public String userUid = FirebaseAuth.getInstance().getUid();

    public ScheduleViewModel(Context context) {
        super(context);
    }

    public ArrayList getEventDay(){
        ArrayList<String> list = new ArrayList<>();
        list.add("2018,9,18");
        list.add("2018,10,12");
        list.add("2018,10,19");
        list.add("2018,10,24");

        return list;
    }
}
