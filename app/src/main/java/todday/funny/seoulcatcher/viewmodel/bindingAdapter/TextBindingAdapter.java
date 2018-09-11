package todday.funny.seoulcatcher.viewmodel.bindingAdapter;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
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

    @BindingAdapter({"setDiscountText", "setTypeText", "setTypeTextSize"})
    public static void setDiscountText(TextView view, int disCount, String setTypeText, int typeSize) {
        String text = disCount + " " + setTypeText;

        SpannableString spannableString = new SpannableString(text);
        if (typeSize != 0) {
            spannableString.setSpan(new AbsoluteSizeSpan(typeSize, true), text.length() - setTypeText.length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        view.setText(spannableString);
    }
}
