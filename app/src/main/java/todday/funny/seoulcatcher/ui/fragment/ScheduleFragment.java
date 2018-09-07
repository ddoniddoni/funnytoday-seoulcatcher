package todday.funny.seoulcatcher.ui.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import todday.funny.seoulcatcher.BaseActivity;
import todday.funny.seoulcatcher.R;
import todday.funny.seoulcatcher.util.CommonDecorator;
import todday.funny.seoulcatcher.util.EventDecorator;
import todday.funny.seoulcatcher.util.SaturdayDecorator;
import todday.funny.seoulcatcher.util.SundayDecorator;
import todday.funny.seoulcatcher.util.ToastMake;
import todday.funny.seoulcatcher.util.TodayDecorator;
import todday.funny.seoulcatcher.viewmodel.ScheduleViewModel;


public class ScheduleFragment extends Fragment {
    private ScheduleBinding binding;
    private ScheduleViewModel model;
    private Context context;
    private MaterialCalendarView calendarView = null;
    private SundayDecorator sundayDecorator = new SundayDecorator();
    private SaturdayDecorator saturdayDecorator = new SaturdayDecorator();
    private CommonDecorator commonDecorator = new CommonDecorator();
    private TodayDecorator todayDecorator = new TodayDecorator();
    private TodayDecorator setModel = new TodayDecorator();


    public static ScheduleFragment newInstance() {
        Bundle args = new Bundle();
        ScheduleFragment fragment = new ScheduleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_schedule, container, false);
        if (getActivity() != null && getActivity() instanceof BaseActivity) {
            model = ((BaseActivity) getActivity()).getScheduleViewModel();
            binding.setModel(model);
        }

        View view = binding.getRoot();
        context = container.getContext();
        calendarView = view.findViewById(R.id.calendarView);

        settingCalendar();
        return view;
    }

    private void createScheduleDateBase() {
        /*HashMap map = new HashMap<String,Object >();
        map["schecule"] = date

        FirebaseFirestore.getInstance().collection("users").document(userId).set(map).addOnSuccessListener {
            ToastMake.make(context!!,"데이터베이스 삽입 성공")
            Log.e("데이터", map.get("schecule").toString())
        }.addOnFailureListener {
            ToastMake.make(context!!,"데이터베이스 삽입 실패")
        }*/
    }

    private void settingCalendar() {
        final ArrayList<String>[] eventDay = new ArrayList[0];
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                 eventDay[0] = model.getEventDay();
                String year = String.valueOf(date.getYear());
                String month = String.valueOf(date.getMonth() + 1);
                String dayy = String.valueOf(date.getDay());
                String datee = year + "," + month + "," + dayy;

                for (int i = 0; i < eventDay[0].size(); i++) {
                    if (eventDay[0].get(i) == datee) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                                .setTitle("교육을 신청하시겠습니까?")
                                .setMessage(datee + " , " + "광나루")
                                .setPositiveButton("신청", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        createScheduleDateBase();
                                    }
                                })
                                .setNegativeButton("취소", null);
                        alertDialog.show();

                    }
                }
            }
        });
        new CheckPointCalender(eventDay[0]).executeOnExecutor(Executors.newSingleThreadExecutor());
        calendarView.addDecorators(commonDecorator,sundayDecorator,saturdayDecorator,todayDecorator);
    }

    private class CheckPointCalender extends AsyncTask<Void, Void, ArrayList<CalendarDay>> {

        private ArrayList<String> timeResult = new ArrayList<String>();
        private ArrayList<CalendarDay> list = new ArrayList<CalendarDay>();

        public CheckPointCalender(ArrayList<String> timeResult) {
            this.timeResult = timeResult;
        }

        @Override
        protected ArrayList<CalendarDay> doInBackground(Void... voids) {
            Calendar calendar = Calendar.getInstance();

            for (int i = 0; i < timeResult.size() + 1; i++) {
                CalendarDay day = CalendarDay.from(calendar);
                String[] time = timeResult.get(i).split(",");
                int year = Integer.parseInt(time[0]);
                int month = Integer.parseInt(time[1]);
                int dayy = Integer.parseInt(time[2]);
                calendar.set(year, month - 1, dayy);
                if (i != 0) {
                    list.add(day);
                }
            }

            return list;
        }

        @Override
        protected void onPostExecute(ArrayList<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            calendarView.addDecorator(new EventDecorator(Color.GREEN, list, context));
        }
    }

}
