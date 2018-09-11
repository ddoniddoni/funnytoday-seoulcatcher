package todday.funny.seoulcatcher.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.support.v4.widget.SwipeRefreshLayout;

import todday.funny.seoulcatcher.interactor.OnLoadUserDataFinishListener;
import todday.funny.seoulcatcher.model.User;

public class ProfileViewModel extends BaseViewModel implements SwipeRefreshLayout.OnRefreshListener {
    public ObservableField<String> mUserId = new ObservableField<>();
    public ObservableField<User> mUser = new ObservableField<>();


    public ProfileViewModel(Context context, String userId) {
        super(context);
        mUserId.set(userId);
        getUser(userId);
    }

    public void getUser(String userId) {
        showLoading.set(true);
        mServerDataController.getUser(userId, new OnLoadUserDataFinishListener() {
            @Override
            public void onFinish(User user) {
                mUser.set(user);
                showLoading.set(false);
            }
        });
    }


    @Override
    public void onRefresh() {
        if (mUserId.get() != null) {
            getUser(mUserId.get());
        }
    }
}

