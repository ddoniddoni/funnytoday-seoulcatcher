package todday.funny.seoulcatcher.ui.dialog;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import todday.funny.seoulcatcher.R;
import todday.funny.seoulcatcher.databinding.ScheduleDialogBinding;
import todday.funny.seoulcatcher.model.Schedule;
import todday.funny.seoulcatcher.util.Keys;
import todday.funny.seoulcatcher.util.ToastMake;
import todday.funny.seoulcatcher.viewmodel.ScheduleDialogViewModel;
import todday.funny.seoulcatcher.viewmodel.ScheduleViewModel;

public class ScheduleDialog extends DialogFragment implements OnMapReadyCallback{
    public ScheduleDialogBinding binding;

    private Button button_ok;
    private Button button_cancel;
    private GoogleMap map = null;
    private MapView mapView = null;
    private String date;

    private String uid;

    public static ScheduleDialog newInstance(String date) {
        Bundle args = new Bundle();
        args.putString("date",date);
        ScheduleDialog fragment = new ScheduleDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_schedule,container,false);

        ScheduleDialogViewModel model = new ScheduleDialogViewModel(getActivity());
        binding.setModel(model);

        View view = binding.getRoot();

        uid = FirebaseAuth.getInstance().getUid();

        button_ok = view.findViewById(R.id.schedueldialog_comfirm);
        button_cancel = view.findViewById(R.id.schedueldialog_cancel);
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputScheduleDateBase(date);
                dismiss();
                ToastMake.make(getContext(),date +" 날짜로 수강이 신청되었습니다.");
            }
        });
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        if(getArguments()!=null) {
            date = getArguments().getString("date");
        }

        model.textDate = date;

        mapView = view.findViewById(R.id.schedueldialog_mapview);
        if(mapView != null){
            mapView.onCreate(savedInstanceState);
        }
        mapView.getMapAsync(this);



        return view;
    }

    private void buttonOK(){

    }
    private void buttoncancel(){

    }
    private void inputScheduleDateBase(final String date) {
        FirebaseFirestore.getInstance().collection(Keys.USERS).document(uid).collection(Keys.SCHEDULES).document().set(new Schedule(date))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e("데이터 베이스 삽입 성공!", date);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("데이터 베이스 삽입 실패", e.toString());
            }
        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(37.551401,127.077142));

        markerOptions.title("광나루 안점체험관");

        map.addMarker(markerOptions);

        LatLng nowLang = new LatLng(37.551401,127.077142);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(nowLang, 12);
        map.animateCamera(cameraUpdate);

    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }
    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
