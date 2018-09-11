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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        model = getMainViewModel();
        model.setNavigationItemSelectedListener();
        binding.setModel(model);
        model.initFragmentList(R.id.container);
    }
}
