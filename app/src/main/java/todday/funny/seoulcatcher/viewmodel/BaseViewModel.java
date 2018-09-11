package todday.funny.seoulcatcher.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.support.v4.app.DialogFragment;

import java.util.logging.Level;

import io.reactivex.disposables.CompositeDisposable;
import todday.funny.seoulcatcher.BaseActivity;
import todday.funny.seoulcatcher.model.User;
import todday.funny.seoulcatcher.server.ServerDataController;
import todday.funny.seoulcatcher.ui.dialog.AlertDialogCreate;
import todday.funny.seoulcatcher.ui.dialog.ImageViewerDialog;
import todday.funny.seoulcatcher.ui.dialog.MembershipDialog;
import todday.funny.seoulcatcher.ui.dialog.UserEditDialog;

public class BaseViewModel extends BaseObservable {
    public long mLastClickTime = 0;
    public Context mContext;
    public ServerDataController mServerDataController;
    public AlertDialogCreate mAlertDialogCreate;
    public CompositeDisposable mCompositeDisposable;

    public ObservableBoolean showLoading = new ObservableBoolean(false);

    public BaseViewModel(Context context) {
        mContext = context;
        mServerDataController = ServerDataController.getInstance(context);
        mAlertDialogCreate = AlertDialogCreate.getInstance(context);
        if (context instanceof BaseActivity) {
            mCompositeDisposable = ((BaseActivity) context).compositeDisposable;
        }
    }

    public boolean clickTimeCheck() {
        if (System.currentTimeMillis() - mLastClickTime < 700) {
            return true;
        }
        mLastClickTime = System.currentTimeMillis();
        return false;
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

    public void openImageViewer(String path) {
        ImageViewerDialog dialog = ImageViewerDialog.newInstance(path);
        startFragmentDialog(dialog, android.R.transition.slide_top);
    }

    public void openUserEdit(User user) {
        UserEditDialog dialog = UserEditDialog.newInstance(user);
        addFragmentDialog(dialog, android.R.transition.slide_right);
    }

    public void openMemberShip(String level) {
        MembershipDialog dialog = MembershipDialog.newInstance(level);
        addFragmentDialog(dialog, android.R.transition.slide_top);
    }

    /**
     * replace frahment
     */
    public void startFragmentDialog(DialogFragment dialogFragment, int transitionId) {
        if (clickTimeCheck()) {
            return;
        }
        if (mContext instanceof BaseActivity) {
            ((BaseActivity) mContext).startFragmentDialog(dialogFragment, transitionId);
        }
    }

    /**
     * add frahment
     */
    public void addFragmentDialog(DialogFragment dialogFragment, int transitionId) {
        if (clickTimeCheck()) {
            return;
        }
        if (mContext instanceof BaseActivity) {
            ((BaseActivity) mContext).addFragmentDialog(dialogFragment, transitionId);
        }
    }
}
