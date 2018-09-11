package todday.funny.seoulcatcher.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.util.Pair;
import android.view.View;

import todday.funny.seoulcatcher.BaseActivity;
import todday.funny.seoulcatcher.server.ServerDataController;
import todday.funny.seoulcatcher.ui.activity.ImageViewerActivity;
import todday.funny.seoulcatcher.ui.dialog.ImageViewerDialog;
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

    public void openImageViewer( String path) {
        ImageViewerDialog dialog = ImageViewerDialog.newInstance(path);
        startFragmentDialog(dialog, android.R.transition.slide_top);
    }

    /**
     * replace frahment
     */
    public void startFragmentDialog(DialogFragment dialogFragment, int transitionId) {
        if (mContext instanceof BaseActivity) {
            ((BaseActivity) mContext).startFragmentDialog(dialogFragment, transitionId);
        }
    }

    /**
     * add frahment
     */
    public void addFragmentDialog(DialogFragment dialogFragment, int transitionId) {
        if (mContext instanceof BaseActivity) {
            ((BaseActivity) mContext).addFragmentDialog(dialogFragment, transitionId);
        }
    }
}
