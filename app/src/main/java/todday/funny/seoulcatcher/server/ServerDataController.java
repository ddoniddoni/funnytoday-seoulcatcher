package todday.funny.seoulcatcher.server;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import todday.funny.seoulcatcher.interactor.OnLoadUserDataFinishListener;
import todday.funny.seoulcatcher.model.User;
import todday.funny.seoulcatcher.util.Keys;

public class ServerDataController {
    private String TAG = ServerDataController.class.getSimpleName();
    private Context mContext;

    private FirebaseFirestore db; //Cloud FireBase
    private FirebaseStorage storage; //소티지
    private StorageReference storageReference;
    public String mLoginUserId;
    public User mLoginUser;

    private int LIMIT_COUNT = 6;

    private static volatile ServerDataController singletonInstance = null;


    public static ServerDataController getInstance(Context context) {
        if (singletonInstance == null) {
            synchronized (ServerDataController.class) {
                if (singletonInstance == null) {
                    singletonInstance = new ServerDataController(context);
                }
            }
        }
        return singletonInstance;
    }

    private ServerDataController(Context context) {
        mContext = context;
        mLoginUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }

    /**
     * 유저관련
     */
    public void initUser(final User user, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
        if (onSuccessListener != null && onFailureListener != null) {
            db.collection(Keys.USERS).document(user.getId()).set(user)
                    .addOnSuccessListener(onSuccessListener)
                    .addOnFailureListener(onFailureListener);
        }
    }

    public Single<User> getLoginUser() {
        return Single.create(new SingleOnSubscribe<User>() {
            @Override
            public void subscribe(final SingleEmitter<User> emitter) throws Exception {
                if (mLoginUser == null) {
                    getUser(mLoginUserId, new OnLoadUserDataFinishListener() {
                        @Override
                        public void onFinish(User user) {
                            mLoginUser = user;
                            emitter.onSuccess(mLoginUser);
                        }
                    });
                } else {
                    emitter.onSuccess(mLoginUser);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void getUser(String userId, @NonNull final OnLoadUserDataFinishListener onLoadUserDataFinishListener) {
        Log.d(TAG + "getUser", "userId = " + userId);
        if (userId.equals(mLoginUserId) && mLoginUser != null) {
            onLoadUserDataFinishListener.onFinish(mLoginUser);
        } else {
            db.collection(Keys.USERS).document(userId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    User user = documentSnapshot.toObject(User.class);
                    onLoadUserDataFinishListener.onFinish(user);
                }
            });
        }
    }

    /**
     * 스케줄 관련
     */

}