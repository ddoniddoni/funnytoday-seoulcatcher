package todday.funny.seoulcatcher.ui.activity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import todday.funny.seoulcatcher.BaseActivity;
import todday.funny.seoulcatcher.R;
import todday.funny.seoulcatcher.databinding.IntroBinding;
import todday.funny.seoulcatcher.databinding.MainBinding;
import todday.funny.seoulcatcher.util.StartActivity;
import todday.funny.seoulcatcher.util.ToastMake;

public class IntroActivity extends BaseActivity {
    private IntroBinding binding;

    private final int RC_SIGN_IN = 123;

    List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.GoogleBuilder().build(),
            new AuthUI.IdpConfig.EmailBuilder().build(),
            new AuthUI.IdpConfig.PhoneBuilder().build()
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_intro);
        binding.animationLoader.setSpeed(1.5f);
    }

    @Override
    protected void onResume() {
        super.onResume();
        compositeDisposable.add(Single.timer(2500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        //StartActivity.startSingle(IntroActivity.this, new Intent(IntroActivity.this, MainActivity.class), true);
                        startActivityForResult(
                                AuthUI.getInstance()
                                        .createSignInIntentBuilder()
                                        .setAvailableProviders(providers)
                                        .build(),
                                RC_SIGN_IN);
                    }
                }));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK) {
                StartActivity.startSingle(this, new Intent(this, MainActivity.class));
            } else {
                ToastMake.make(this, data.toString());
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setLogo(R.drawable.ic_launcher_foreground)
                                .setAvailableProviders(providers)
                                .setTheme(R.style.LoginTheme)
                                .build(),
                        RC_SIGN_IN);
            }
        }
    }
}
