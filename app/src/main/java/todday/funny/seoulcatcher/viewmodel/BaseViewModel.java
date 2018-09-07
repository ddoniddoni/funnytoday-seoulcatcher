package todday.funny.seoulcatcher.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.databinding.BaseObservable;

import todday.funny.seoulcatcher.BaseActivity;

public class BaseViewModel extends BaseObservable {
    public Context mContext;

    public BaseViewModel(Context context) {
        mContext = context;
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
