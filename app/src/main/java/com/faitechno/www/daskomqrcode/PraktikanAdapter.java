package com.faitechno.www.daskomqrcode;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class PraktikanAdapter extends RecyclerView.Adapter<PraktikanAdapter.MyViewHolder> {
 
    private List<Praktikan> praktikanList;
 
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView namadankelas, status;
 
        MyViewHolder(View view) {
            super(view);
            namadankelas = view.findViewById(R.id.namadankelas);
            status = view.findViewById(R.id.statusText);
        }
    }
 
 
    public PraktikanAdapter(List<Praktikan> praktikanList) {
        this.praktikanList = praktikanList;
    }
 
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.praktikan_list_row, parent, false);
 
        return new MyViewHolder(itemView);
    }
 
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Praktikan praktikan = praktikanList.get(position);
        holder.namadankelas.setText(praktikan.getKelas()+"-"+praktikan.getNama());
        if(praktikan.getStatus() == 1){
            if(isWithinRange(Objects.requireNonNull(getDateFromSQL(praktikan.getUpdated_at())))){
                holder.status.setText("Sudah mengumpulkan");
            } else {
                holder.status.setText("Telat mengumpulkan");
            }
        } else if(praktikan.getStatus() == 0) {
            holder.status.setText("Belum mengumpulkan");
        }
    }

    private Date getDateFromSQL(String sqlTime){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat fmt = new SimpleDateFormat("MM-dd-yyyy HH:mm");
        try {
            return fmt.parse(sqlTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean isWithinRange(Date testDate) {

        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.HOUR_OF_DAY, 17);
        calendar1.set(Calendar.MINUTE,0);
        calendar1.set(Calendar.SECOND,0);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.HOUR_OF_DAY, 18);
        calendar2.set(Calendar.MINUTE,0);
        calendar2.set(Calendar.SECOND,0);

        return !(testDate.before(calendar1.getTime()) || testDate.after(calendar2.getTime()));
    }
 
    @Override
    public int getItemCount() {
        return praktikanList.size();
    }
}