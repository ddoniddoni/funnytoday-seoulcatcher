package todday.funny.seoulcatcher.ui.activity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

public class IntroActivity extends BaseActivity {
    private IntroBinding binding;

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
                        StartActivity.startSingle(IntroActivity.this, new Intent(IntroActivity.this, MainActivity.class), true);
                    }
                }));
    }
}
