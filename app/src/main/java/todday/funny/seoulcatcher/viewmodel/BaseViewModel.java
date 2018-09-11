package todday.funny.seoulcatcher.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;

import todday.funny.seoulcatcher.BaseActivity;
import todday.funny.seoulcatcher.server.ServerDataController;
import todday.funny.seoulcatcher.ui.activity.ImageViewerActivity;
import todday.funny.seoulcatcher.util.Keys;

public class BaseViewModel extends BaseObservable {
    public Context mContext;
    public ServerDataController mServerDataController;

    public ObservableBoolean showLoading = new ObservableBoolean(false);

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

    public void openImageViewer(View view, String path, String transName) {
        transName = transName + "openImageViewer";
        Intent intent = new Intent(mContext, ImageViewerActivity.class);
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, Pair.create(view, transName));
        intent.putExtra(Keys.PATH, path);
        intent.putExtra(Keys.TRANS_NAME, transName);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mContext.startActivity(intent, activityOptionsCompat.toBundle());
        } else {
            mContext.startActivity(intent);
        }
    }
}
