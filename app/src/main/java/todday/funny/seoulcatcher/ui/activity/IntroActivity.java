package todday.funny.seoulcatcher.ui.activity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import todday.funny.seoulcatcher.BaseActivity;
import todday.funny.seoulcatcher.R;
import todday.funny.seoulcatcher.databinding.IntroBinding;
import todday.funny.seoulcatcher.databinding.MainBinding;
import todday.funny.seoulcatcher.model.User;
import todday.funny.seoulcatcher.server.ServerDataController;
import todday.funny.seoulcatcher.util.Keys;
import todday.funny.seoulcatcher.util.SharedPrefsUtils;
import todday.funny.seoulcatcher.util.StartActivity;
import todday.funny.seoulcatcher.util.ToastMake;

public class IntroActivity extends BaseActivity {
    private IntroBinding binding;

    private boolean mAutoLogin;
    private final int RC_SIGN_IN = 123;

    private CompositeDisposable mCompositeDisposable;
    private ServerDataController mServerDataController;

    List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.EmailBuilder().build(),
            new AuthUI.IdpConfig.PhoneBuilder().build(),
            new AuthUI.IdpConfig.GoogleBuilder().build());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_intro);
        binding.animationLoader.setSpeed(1.5f);
        mAutoLogin = SharedPrefsUtils.getBooleanPreference(this, Keys.AUTO_LOGIN, false);
        mCompositeDisposable = new CompositeDisposable();
        mServerDataController = ServerDataController.getInstance(this);
        long delay = mAutoLogin ? 0 : 1500;
        compositeDisposable.add(Single.timer(delay, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if (mAutoLogin) { //자동로그인
                            startMainActivity();
                        } else {
                            startActivityForResult(
                                    AuthUI.getInstance()
                                            .createSignInIntentBuilder()
                                            .setAvailableProviders(providers)
                                            .setTheme(R.style.LoginTheme)
                                            .build(),
                                    RC_SIGN_IN);
                        }
                    }
                }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                User newUser = new User();
                newUser.setUser(user);
                mServerDataController.initUser(newUser,
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                startMainActivity();
                            }
                        }, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                ToastMake.make(getApplicationContext(), R.string.network_error);
                                finish();
                            }
                        });
                return;
            }
        }
        ToastMake.make(this, R.string.network_error);
        finish();
    }

    public void startMainActivity() {
        mCompositeDisposable.add(mServerDataController.getLoginUser().subscribe(new Consumer<User>() {
            @Override
            public void accept(User user) throws Exception {
                StartActivity.startSingle(IntroActivity.this, new Intent(IntroActivity.this, MainActivity.class), true);
            }
        }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }
}
