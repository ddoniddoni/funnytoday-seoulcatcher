package todday.funny.seoulcatcher.server;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import todday.funny.seoulcatcher.R;
import todday.funny.seoulcatcher.interactor.OnLoadMemberShipsListener;
import todday.funny.seoulcatcher.interactor.OnLoadScheduleListListener;
import todday.funny.seoulcatcher.interactor.OnLoadUserDataFinishListener;
import todday.funny.seoulcatcher.interactor.OnUploadFinishListener;
import todday.funny.seoulcatcher.model.MemberShip;
import todday.funny.seoulcatcher.model.Schedule;
import todday.funny.seoulcatcher.model.User;
import todday.funny.seoulcatcher.util.ImageConverter;
import todday.funny.seoulcatcher.util.Keys;
import todday.funny.seoulcatcher.util.ToastMake;

public class ServerDataController {
    private String TAG = ServerDataController.class.getSimpleName();
    private Context mContext;

    private FirebaseFirestore db; //Cloud FireBase
    private FirebaseStorage storage; //소티지
    private StorageReference storageReference;
    private User mLoginUser;
    private String mLoginUserId;
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
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }

    public String getLoginUserId() {
        if (mLoginUserId == null) {
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                mLoginUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            }
        }
        return mLoginUserId;
    }

    //ur 가져오기
    private void getImageDownLoadURL(@NonNull UploadTask uploadTask, @NonNull OnCompleteListener onCompleteListener) {
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return task.getResult().getStorage().getDownloadUrl();
            }
        }).addOnCompleteListener(onCompleteListener);
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
                    getUser(getLoginUserId(), new OnLoadUserDataFinishListener() {
                        @Override
                        public void onComplete(User user) {
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
        if (userId.equals(getLoginUserId()) && mLoginUser != null) {
            onLoadUserDataFinishListener.onComplete(mLoginUser);
        } else {
            db.collection(Keys.USERS).document(userId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    User user = documentSnapshot.toObject(User.class);
                    onLoadUserDataFinishListener.onComplete(user);
                }
            });
        }
    }

    public void updateUser(String userId, String name, String nickName, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
        Log.d(TAG + "updateUser", "userId = " + userId);
        Map<String, Object> updateUser = new HashMap<>();
        updateUser.put("name", name);
        updateUser.put("nickName", nickName);
        if (onSuccessListener != null && onFailureListener != null) {
            db.collection(Keys.USERS).document(userId).update(updateUser)
                    .addOnSuccessListener(onSuccessListener)
                    .addOnFailureListener(onFailureListener);
        }
    }

    public void updateUserLevel(User user, OnSuccessListener<Void> onSuccessListener, OnFailureListener onFailureListener) {
        Log.d(TAG + "updateUserLevel", "userId = " + user.getId());
        user.setLevel(String.valueOf(Integer.parseInt(user.getLevel()) + 1));
        Map<String, Object> updateUser = new HashMap<>();
        updateUser.put("level", user.getLevel());
        if (onSuccessListener != null && onFailureListener != null) {
            db.collection(Keys.USERS).document(user.getId()).update(updateUser)
                    .addOnSuccessListener(onSuccessListener)
                    .addOnFailureListener(onFailureListener);
        }
    }


    //유저 프로파일 업데이트
    public void updateUserProfile(CompositeDisposable compositeDisposable, final String userId, String profileUrl, final OnUploadFinishListener onUploadFinishListener) {
        compositeDisposable.add(ImageConverter.stringToBitmap(mContext, profileUrl).subscribe(new Consumer<Bitmap>() {
            @Override
            public void accept(Bitmap bitmap) throws Exception {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] data = baos.toByteArray();
                UploadTask uploadTask = storageReference.child(Keys.USERS).child(userId).child(Keys.USER_PROFILE).putBytes(data);
                getImageDownLoadURL(uploadTask, new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();

                            db.collection(Keys.USERS).document(userId).update("photoUrl", String.valueOf(downloadUri));
                            onUploadFinishListener.onUploadFinish(String.valueOf(downloadUri));
                        }
                    }
                });
            }
        }));
    }

    //유저 배경 업데이트
    public void updateUserBackground(CompositeDisposable compositeDisposable, final String userId, String backgroundUrl, final OnUploadFinishListener onUploadFinishListener) {
        compositeDisposable.add(ImageConverter.stringToBitmap(mContext, backgroundUrl).subscribe(new Consumer<Bitmap>() {
            @Override
            public void accept(Bitmap bitmap) throws Exception {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] data = baos.toByteArray();
                UploadTask uploadTask = storageReference.child(Keys.USERS).child(userId).child(Keys.USER_BACKGROUND).putBytes(data);
                getImageDownLoadURL(uploadTask, new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            db.collection(Keys.USERS).document(userId).update("backgroundUrl", String.valueOf(downloadUri));
                            onUploadFinishListener.onUploadFinish(String.valueOf(downloadUri));
                        }
                    }
                });
            }
        }));
    }

    /**
     * 스케줄 관련
     */

    public void getUserSchedule(String userId, final OnLoadScheduleListListener onLoadScheduleListListener) {
        db.collection(Keys.USERS).document(userId).collection(Keys.SCHEDULES).orderBy("date", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<Schedule> scheduleArrayList = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (int i = 0; i < task.getResult().getDocuments().size(); i++) {
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(i);
                        Schedule schedule = documentSnapshot.toObject(Schedule.class);
                        scheduleArrayList.add(schedule);
                    }
                }
                onLoadScheduleListListener.onComplete(scheduleArrayList);
            }
        });
    }

    /**
     * 멤버쉽
     */
    /**
     * 세팅 멤버쉽
     */
    public void setMemberShipsData() {
        ArrayList<MemberShip> memberShips = new ArrayList<>();
        //name color discountRate
        memberShips.add(new MemberShip("PARIS BAGUETTE"
                , "https://firebasestorage.googleapis.com/v0/b/funnytoday-seoulcatcher.appspot.com/o/memberShips%2FlogoB_mod_renew.png?alt=media&token=ddd71f4f-59b2-48fc-8eec-df75c40e0222",
                "#14408B", 10));
        memberShips.add(new MemberShip("MEGABOX"
                , "https://firebasestorage.googleapis.com/v0/b/funnytoday-seoulcatcher.appspot.com/o/memberShips%2Flogo_megabox.png?alt=media&token=444afc4f-337f-4605-95d1-29ca74c0c8f8",
                "#361F66", 10));
        memberShips.add(new MemberShip("CGV"
                , "https://firebasestorage.googleapis.com/v0/b/funnytoday-seoulcatcher.appspot.com/o/memberShips%2Flogo_cvg.png?alt=media&token=21d630c9-99e2-4009-a5e7-2d98f383665d",
                "#E71A0F", 10));
        memberShips.add(new MemberShip("OLIVE_YOUNG"
                , "https://firebasestorage.googleapis.com/v0/b/funnytoday-seoulcatcher.appspot.com/o/memberShips%2Flogo_olive_young.png?alt=media&token=0e70a663-be5d-4853-8bf9-206ac3f9760c",
                "#000000", 10));
        memberShips.add(new MemberShip("INNISFREE"
                , "https://firebasestorage.googleapis.com/v0/b/funnytoday-seoulcatcher.appspot.com/o/memberShips%2Flogo_innisfree.png?alt=media&token=350121d0-5df1-4d1e-98bb-caeb2995662d",
                "#015D23", 10));
        memberShips.add(new MemberShip("BASKIN ROBBINS"
                , "https://firebasestorage.googleapis.com/v0/b/funnytoday-seoulcatcher.appspot.com/o/memberShips%2Flogo_baskinrobbins.png?alt=media&token=52192869-2494-4fd9-9e5d-f0c1f411ee3d",
                "#7D7364", 10));
        memberShips.add(new MemberShip("LOTTE CINEMA"
                , "https://firebasestorage.googleapis.com/v0/b/funnytoday-seoulcatcher.appspot.com/o/memberShips%2Flogo_lotte_cinema.gif?alt=media&token=243e1597-36d3-4244-a650-44108532dbe9",
                "#C5C5C5", 10));
        for (MemberShip memberShip : memberShips) {
            db.collection(Keys.MEMBER_SHIPS).document("5").collection(Keys.ITEMS).add(memberShip);
        }
    }

    /**
     * 멤버쉽 가져오기
     */
    public void getMemberShips(String level, final OnLoadMemberShipsListener onLoadMemberShipsListener) {
        int levelInt = Integer.parseInt(level);
        level = levelInt > 5 ? "5" : level;
        db.collection(Keys.MEMBER_SHIPS).document(level).collection(Keys.ITEMS).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<MemberShip> memberShips = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (int i = 0; i < task.getResult().getDocuments().size(); i++) {
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(i);
                        MemberShip memberShip = documentSnapshot.toObject(MemberShip.class);
                        memberShips.add(memberShip);
                    }
                }
                onLoadMemberShipsListener.onComplete(memberShips);
            }
        });
    }


}