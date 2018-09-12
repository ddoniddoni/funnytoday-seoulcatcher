package todday.funny.seoulcatcher.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import todday.funny.seoulcatcher.R;

public class AlertDialogCreate {
    private static volatile AlertDialogCreate singletonInstance = null;
    private AlertDialog.Builder alertDialog;
    private Context mContext;

    public static AlertDialogCreate getInstance(Context context) {
        if (singletonInstance == null) {
            synchronized (AlertDialogCreate.class) {
                if (singletonInstance == null) {
                    singletonInstance = new AlertDialogCreate(context);
                }
            }
        }
        return singletonInstance;
    }

    private AlertDialogCreate(Context context) {
        mContext = context;
        alertDialog = new AlertDialog.Builder(context);
        alertDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
    }

    public void deleteSchedule(DialogInterface.OnClickListener clickListener) {
        alertDialog.setTitle("교육");
        alertDialog.setMessage("해당 교육을 취소하시겠습니까?");
        alertDialog.setPositiveButton("확인", clickListener);
        alertDialog.show();
    }

    public void logout(DialogInterface.OnClickListener clickListener) {
        alertDialog.setTitle(mContext.getString(R.string.account));
        alertDialog.setMessage(mContext.getString(R.string.msg_logout));
        alertDialog.setPositiveButton(mContext.getString(R.string.log_out), clickListener);
        alertDialog.show();
    }
}
