package todday.funny.seoulcatcher.ui.dialog;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import todday.funny.seoulcatcher.R;
import todday.funny.seoulcatcher.databinding.MembershipBinding;
import todday.funny.seoulcatcher.util.Keys;
import todday.funny.seoulcatcher.viewmodel.MembershipViewModel;

public class MembershipDialog extends DialogFragment {
    private MembershipBinding binding;

    public static MembershipDialog newInstance(String level) {
        Bundle args = new Bundle();
        args.putString(Keys.LEVEL, level);
        MembershipDialog fragment = new MembershipDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_membership, container, false);
        if (getArguments() != null) {
            String path = getArguments().getString(Keys.PATH);
             MembershipViewModel model = new MembershipViewModel(getActivity());
            binding.setModel(model);
        }
        return binding.getRoot();
    }

}
