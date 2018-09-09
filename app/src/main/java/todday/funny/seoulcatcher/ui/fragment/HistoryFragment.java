package todday.funny.seoulcatcher.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import todday.funny.seoulcatcher.BaseActivity;
import todday.funny.seoulcatcher.R;
import todday.funny.seoulcatcher.databinding.EducationBinding;
import todday.funny.seoulcatcher.databinding.HistoryBinding;
import todday.funny.seoulcatcher.viewmodel.EducationViewModel;
import todday.funny.seoulcatcher.viewmodel.HistoryViewModel;

public class HistoryFragment extends Fragment {

    private HistoryBinding binding = null;
    private HistoryViewModel model = null;

    public static HistoryFragment newInstance() {
        Bundle args = new Bundle();
        HistoryFragment fragment = new HistoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false);
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            model = ((BaseActivity) getActivity()).getHistoryViewModel();
            binding.setModel(model);
        }
        return binding.getRoot();
    }


}

