package todday.funny.seoulcatcher.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;

public class EmptyViewModel extends BaseViewModel {
    public ObservableField<String> mEmptyText = new ObservableField<>();

    public EmptyViewModel(Context context, String emptyText) {
        super(context);
        mEmptyText.set(emptyText);
    }
}
