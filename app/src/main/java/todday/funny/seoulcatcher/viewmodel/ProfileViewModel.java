package todday.funny.seoulcatcher.viewmodel;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.v4.widget.SwipeRefreshLayout;

import java.util.List;

import todday.funny.seoulcatcher.interactor.OnLoadScheduleListListener;
import todday.funny.seoulcatcher.interactor.OnLoadUserDataFinishListener;
import todday.funny.seoulcatcher.model.Schedule;
import todday.funny.seoulcatcher.model.User;

public class ProfileViewModel extends BaseViewModel implements SwipeRefreshLayout.OnRefreshListener {
    public ObservableArrayList<Object> mProfileList = new ObservableArrayList<>();

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

    public void getUser(final String userId) {
        showLoading.set(true);
        mServerDataController.getUser(userId, new OnLoadUserDataFinishListener() {
            @Override
            public void onComplete(User user) {
                mUser.set(user);
                mProfileList.add(0, user);
                getUserSchedule(userId);
                showLoading.set(false);
            }
        });
    }

    public void getUserSchedule(String userId) {
        showLoading.set(true);
        mServerDataController.getUserSchedule(userId, new OnLoadScheduleListListener() {
            @Override
            public void onComplete(List<Schedule> scheduleList) {
                mProfileList.addAll(scheduleList);
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

