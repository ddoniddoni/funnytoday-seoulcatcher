package todday.funny.seoulcatcher.ui.activity;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import java.util.List;

import todday.funny.seoulcatcher.BaseActivity;
import todday.funny.seoulcatcher.R;
import todday.funny.seoulcatcher.databinding.MainBinding;
import todday.funny.seoulcatcher.ui.fragment.EducationFragment;
import todday.funny.seoulcatcher.ui.fragment.HistoryFragment;
import todday.funny.seoulcatcher.ui.fragment.ScheduleFragment;
import todday.funny.seoulcatcher.ui.fragment.ProfileFragment;
import todday.funny.seoulcatcher.ui.fragment.SettingFragment;
import todday.funny.seoulcatcher.viewmodel.MainViewModel;

public class MainActivity extends BaseActivity {
    private MainBinding binding;
    private MainViewModel model;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        model = getMainViewModel();
        model.setNavigationItemSelectedListener(navigationItemSelectedListener);
        binding.setModel(model);

        replaceFramgment(ProfileFragment.newInstance());
    }

    private void init(){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.container, model.getScheduleFragment(), "schedule");
        fragmentTransaction.add(R.id.container, model.getEducationFragment(), "education");
        fragmentTransaction.add(R.id.container, model.getProfileFragment(), "profile");
        fragmentTransaction.add(R.id.container, model.getHistoryFragment(), "history");
        fragmentTransaction.add(R.id.container, model.getSettingFragment(), "setting");

        fragmentTransaction.hide(model.getScheduleFragment());
        fragmentTransaction.hide(model.getEducationFragment());
        fragmentTransaction.hide(model.getHistoryFragment());
        fragmentTransaction.hide(model.getSettingFragment());
        fragmentTransaction.commit();
    }

    public void replaceFramgment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, fragment)
                .commit();
    }

    public BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.navigation_educationSchedule:
                    replaceFramgment(ScheduleFragment.newInstance());
                    return true;
                case R.id.navigation_education:
                    replaceFramgment(EducationFragment.newInstance());
                    return true;
                case R.id.navigation_profile:
                    replaceFramgment(ProfileFragment.newInstance());
                    return true;
                case R.id.navigation_history:
                    replaceFramgment(HistoryFragment.newInstance());
                    return true;
                case R.id.navigation_setting:
                    replaceFramgment(SettingFragment.newInstance());
                    return true;
            }
            return false;
        }
    };

    private void selectView(String tag){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        @SuppressLint("RestrictedApi") List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment.getTag().equals(tag)) {
                fragmentTransaction.show(fragment);
            } else {
                fragmentTransaction.hide(fragment);
            }
        }
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }
}
