package todday.funny.seoulcatcher.viewmodel;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.v4.widget.SwipeRefreshLayout;

import todday.funny.seoulcatcher.interactor.OnLoadUserDataFinishListener;
import todday.funny.seoulcatcher.model.User;

public class ProfileViewModel extends BaseViewModel implements SwipeRefreshLayout.OnRefreshListener {
    public ObservableArrayList<Object> mWriteList = new ObservableArrayList<>();

    public ObservableField<String> mUserId = new ObservableField<>();
    public ObservableField<User> mUser = new ObservableField<>();


    public ProfileViewModel(Context context, String userId) {
        super(context);
        initData(userId);
    }

    private void initData(String userId) {
        this.mUserId.set(userId);
        getUser(userId);
    }

    public void getUser(String userId) {
        showLoading.set(true);
        mServerDataController.getUser(userId, new OnLoadUserDataFinishListener() {
            @Override
            public void onFinish(User user) {
                mUser.set(user);
                mWriteList.add(0, user);
                showLoading.set(false);
            }
        });
    }


    @Override
    public void onRefresh() {
        if (mUserId.get() != null) {
            initData(mUserId.get());
        }
    }
}

