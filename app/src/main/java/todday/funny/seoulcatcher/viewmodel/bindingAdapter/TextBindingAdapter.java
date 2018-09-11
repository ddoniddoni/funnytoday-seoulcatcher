package todday.funny.seoulcatcher.viewmodel.bindingAdapter;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.TextView;

import todday.funny.seoulcatcher.R;

public class TextBindingAdapter {
    @BindingAdapter({"setLevleText"})
    public static void setLevleText(TextView view, String level) {
        Context context = view.getContext();
        String levelText = context.getString(R.string.level_text, level);
        view.setText(levelText);

    }
}
