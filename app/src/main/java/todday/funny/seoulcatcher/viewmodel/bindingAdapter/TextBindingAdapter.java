package todday.funny.seoulcatcher.viewmodel.bindingAdapter;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.TextView;

import todday.funny.seoulcatcher.R;
import todday.funny.seoulcatcher.util.DateFormat;

public class TextBindingAdapter {
    @BindingAdapter({"setLevelText"})
    public static void setLevelText(TextView view, String level) {
        Context context = view.getContext();
        String levelText = context.getString(R.string.level_text, level);
        view.setText(levelText);
    }

    @BindingAdapter({"setDdayText"})
    public static void setDdayText(TextView view, String date) {
        Context context = view.getContext();
        view.setText(DateFormat.getDdayStringFromCalendar(context, date));
    }
}
