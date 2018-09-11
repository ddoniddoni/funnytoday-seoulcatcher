package todday.funny.seoulcatcher.viewmodel;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;

import java.util.List;

import todday.funny.seoulcatcher.interactor.OnLoadMemberShipsListener;
import todday.funny.seoulcatcher.model.MemberShip;
import todday.funny.seoulcatcher.util.Keys;

public class MembershipListViewModel extends BaseViewModel {
    public ObservableArrayList<Object> mMemberShipList = new ObservableArrayList<>();

    public MembershipListViewModel(Context context, String level) {
        super(context);
        initData(level);
    }

    public void initData(String level) {
        showLoading.set(true);
        mServerDataController.getMemberShips(level, new OnLoadMemberShipsListener() {
            @Override
            public void onComplete(List<MemberShip> memberShips) {
                mMemberShipList.add(Keys.BARCODE);
                mMemberShipList.addAll(memberShips);
                showLoading.set(false);
            }
        });
    }
}
