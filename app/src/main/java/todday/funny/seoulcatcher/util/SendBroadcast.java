package todday.funny.seoulcatcher.util;

import android.content.Context;
import android.content.Intent;

import todday.funny.seoulcatcher.model.User;

public class SendBroadcast {
    public static void path(Context context, String action, String path) {
        Intent send = new Intent(action);
        send.putExtra(Keys.PATH, path);
        context.sendBroadcast(send);
    }

    public static void user(Context context, String action, User user) {
        Intent send = new Intent(action);
        send.putExtra(Keys.USER, user);
        context.sendBroadcast(send);
    }
}
