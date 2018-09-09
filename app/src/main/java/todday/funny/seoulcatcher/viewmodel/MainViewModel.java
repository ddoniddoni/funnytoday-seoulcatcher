package todday.funny.seoulcatcher.viewmodel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import todday.funny.seoulcatcher.ui.fragment.EducationFragment;
import todday.funny.seoulcatcher.ui.fragment.HistoryFragment;
import todday.funny.seoulcatcher.ui.fragment.ProfileFragment;
import todday.funny.seoulcatcher.ui.fragment.ScheduleFragment;
import todday.funny.seoulcatcher.ui.fragment.SettingFragment;

public class MainViewModel extends BaseViewModel {

    private ScheduleFragment scheduleFragment = ScheduleFragment.newInstance();
    private EducationFragment educationFragment = EducationFragment.newInstance();
    private ProfileFragment profileFragment = ProfileFragment.newInstance();
    private HistoryFragment historyFragment = HistoryFragment.newInstance();
    private SettingFragment settingFragment = SettingFragment.newInstance();

    public BottomNavigationView.OnNavigationItemSelectedListener mNavigationItemSelectedListener;


    public MainViewModel(Context context) {
        super(context);
    }

    public void setNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener mNavigationItemSelectedListener) {
        this.mNavigationItemSelectedListener = mNavigationItemSelectedListener;
    }

    public ScheduleFragment getScheduleFragment() {
        return scheduleFragment;
    }

    public EducationFragment getEducationFragment() {
        return educationFragment;
    }

    public ProfileFragment getProfileFragment() {
        return profileFragment;
    }

    public HistoryFragment getHistoryFragment() {
        return historyFragment;
    }

    public SettingFragment getSettingFragment() {
        return settingFragment;
    }

}
