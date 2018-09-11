package todday.funny.seoulcatcher.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import todday.funny.seoulcatcher.R;
import todday.funny.seoulcatcher.model.ScheduleModel;

public class ScheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<ScheduleModel> scheduleModels ;
    private ArrayList<String> scheduleModelsKey ;
    private String uid ;
    private Context context;

    public ScheduleAdapter(Context context, ArrayList<ScheduleModel> scheduleModels,ArrayList<String> scheduleModelsKey){
        this.context = context;
        this.uid =  FirebaseAuth.getInstance().getUid();
        this.scheduleModels = scheduleModels;
        this.scheduleModelsKey = scheduleModelsKey;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_schedule,viewGroup,false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        ScheduleViewHolder scheduleViewHolder = (ScheduleViewHolder)viewHolder;

        scheduleViewHolder.textView.setText(scheduleModels.get(position).getDate());
        scheduleViewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                        .setTitle("교육을 취소 하시겠습니까?")
                        .setMessage(scheduleModels.get(position).getDate()+ "  광나루")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Log.e("취소 확인!","성공!!!!!");
                                FirebaseFirestore.getInstance().collection("users").document(uid).collection("schedule").document(scheduleModelsKey.get(position)).delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.e("schedule 삭제","삭제성공!");

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.e("schedule 삭제","삭제실패!");
                                    }
                                });
                            }
                        })
                        .setNegativeButton("취소", null);
                alertDialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return scheduleModels.size();
    }

    private class ScheduleViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;
        private Button button;

        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.scheduleItem_textView);
            button = itemView.findViewById(R.id.scheduleItem_button_cancel);
        }
    }

}