package todday.funny.seoulcatcher.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import todday.funny.seoulcatcher.model.Schedule;

public class ScheduleViewModel extends BaseViewModel {
    public ObservableField<Schedule> mSchedule = new ObservableField<>();

    public String userUid = FirebaseAuth.getInstance().getUid();

    public ScheduleViewModel(Context context) {
        super(context);

    }

    public ScheduleViewModel(Context context, Schedule schedule) {
        super(context);
        mSchedule.set(schedule);
    }

    public ArrayList getEventDay() {
        ArrayList<String> list = new ArrayList<>();
        list.add("2018,9,18");
        list.add("2018,10,12");
        list.add("2018,10,19");
        list.add("2018,10,24");

        return list;
    }
}
