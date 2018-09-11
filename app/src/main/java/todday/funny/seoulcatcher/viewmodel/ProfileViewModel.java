package todday.funny.seoulcatcher.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;

import todday.funny.seoulcatcher.interactor.OnLoadUserDataFinishListener;
import todday.funny.seoulcatcher.model.User;

public class ProfileViewModel extends BaseViewModel {
    public ObservableField<User> mUser = new ObservableField<>();


    public ProfileViewModel(Context context, String userId) {
        super(context);
        showLoading.set(true);
        mServerDataController.getUser(userId, new OnLoadUserDataFinishListener() {
            @Override
            public void onFinish(User user) {
                mUser.set(user);
                showLoading.set(false);
            }
        });
    }

}

