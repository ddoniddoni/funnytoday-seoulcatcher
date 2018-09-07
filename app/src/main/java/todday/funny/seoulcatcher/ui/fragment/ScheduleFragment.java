package todday.funny.seoulcatcher.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import todday.funny.seoulcatcher.BaseActivity;
import todday.funny.seoulcatcher.R;
import todday.funny.seoulcatcher.databinding.EducationBinding;
import todday.funny.seoulcatcher.viewmodel.EducationViewModel;


public class ScheduleFragment extends Fragment {
    private EducationBinding binding;
    private EducationViewModel model;

    public static ScheduleFragment newInstance() {
        Bundle args = new Bundle();
        ScheduleFragment fragment = new ScheduleFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_education, container, false);
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            model = ((BaseActivity) getActivity()).getEducationViewModel();
            binding.setModel(model);
        }
        return binding.getRoot();
    }

}
