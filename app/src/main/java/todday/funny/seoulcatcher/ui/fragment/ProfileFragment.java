package todday.funny.seoulcatcher.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import todday.funny.seoulcatcher.BaseActivity;
import todday.funny.seoulcatcher.R;
import todday.funny.seoulcatcher.databinding.ProfileBinding;
import todday.funny.seoulcatcher.viewmodel.ProfileViewModel;


public class ProfileFragment extends Fragment {
    private ProfileBinding binding;
    private ProfileViewModel model;

    public static ProfileFragment newInstance() {
        Bundle args = new Bundle();
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        model = new ProfileViewModel(getActivity());
        binding.setModel(model);
        return binding.getRoot();
    }
}
