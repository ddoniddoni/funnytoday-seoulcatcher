package todday.funny.seoulcatcher.viewmodel;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

public class MainViewModel extends BaseViewModel {
    public BottomNavigationView.OnNavigationItemSelectedListener mNavigationItemSelectedListener;


    public MainViewModel(Context context) {
        super(context);
    }

    public void setNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener mNavigationItemSelectedListener) {
        this.mNavigationItemSelectedListener = mNavigationItemSelectedListener;
    }

}
