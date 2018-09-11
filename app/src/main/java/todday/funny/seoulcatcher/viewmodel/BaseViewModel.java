package todday.funny.seoulcatcher.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.databinding.BaseObservable;

import todday.funny.seoulcatcher.BaseActivity;
import todday.funny.seoulcatcher.server.ServerDataController;

public class BaseViewModel extends BaseObservable {
    public Context mContext;
    public ServerDataController mServerDataController;

    public BaseViewModel(Context context) {
        mContext = context;
        mServerDataController = ServerDataController.getInstance(context);
    }

    public void hideKeyBoard() {
        if (mContext instanceof BaseActivity) {
            ((BaseActivity) mContext).hideKeyboard();
        }
    }

    public void close() {
        if (mContext instanceof Activity) {
            ((Activity) mContext).onBackPressed();
        }
        hideKeyBoard();
    }
}
