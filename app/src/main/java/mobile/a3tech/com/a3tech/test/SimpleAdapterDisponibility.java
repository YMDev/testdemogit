package mobile.a3tech.com.a3tech.test;


import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.jaygoo.widget.RangeSeekBar;
import com.jaygoo.widget.SeekBar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import eltos.simpledialogfragment.SimpleTimeDialog;
import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.model.A3techDisponibility;
import mobile.a3tech.com.a3tech.utils.DateStuffs;

/**
 * Created by Suleiman on 03/02/17.
 */

public class SimpleAdapterDisponibility extends RecyclerView.Adapter<SimpleAdapterDisponibility.SimpleItemVH> {

    //  Data
    private List<A3techDisponibility> listeObjects = new ArrayList<>();

    private Context context;
    public static final String TAG_TIME_DISPO_FROM = "TAG_TIME_DISPO_FROM";
    public static final String TAG_TIME_DISPO_TO = "TAG_TIME_DISPO_TO";

    public SimpleAdapterDisponibility(Context context) {
        this.context = context;
    }

    public SimpleAdapterDisponibility(Context context, List objectMenu) {
        this.context = context;
        listeObjects = objectMenu;
    }

    public List<A3techDisponibility> getListeObjects() {
        return listeObjects;
    }

    public void setListeObjects(List<A3techDisponibility> listeObjects) {
        this.listeObjects = listeObjects;
    }

    public void addMissionb(A3techDisponibility missionV) {
        this.listeObjects.add(missionV);
        this.notifyDataSetChanged();
    }


    @Override
    public SimpleItemVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.a3tech_item_disponibilite, parent, false);

        return new SimpleItemVH(v);
    }

    @Override
    public void onBindViewHolder(final SimpleItemVH holder, int position) {
        final A3techDisponibility dispo = listeObjects.get(position);
        if (dispo == null) return;
        if (dispo.getTimeFrom() == 0l) {
            dispo.setTimeFrom(Calendar.getInstance().getTimeInMillis());
        }
        if (dispo.getTimeTo() == 0l) {
            dispo.setTimeTo(Calendar.getInstance().getTimeInMillis());
        }
        holder.timeFrom.setText(DateStuffs.dateToString(DateStuffs.HOURS_MINUTES_FORMAT, new Date(dispo.getTimeFrom())) + "");
        holder.timeTo.setText(DateStuffs.dateToString(DateStuffs.HOURS_MINUTES_FORMAT, new Date(dispo.getTimeTo())) + "");
        refreshRange(dispo, holder.rangeBar);
        holder.timeFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        StringBuffer buf = new StringBuffer();
                        buf
                                .append(String.format("%02d", selectedHour))
                                .append(":")
                                .append(String.format("%02d", selectedMinute));
                        holder.timeFrom.setText(buf);
                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.HOUR_OF_DAY, selectedHour);
                        cal.set(Calendar.MINUTE, selectedMinute);
                        dispo.setTimeFrom(cal.getTimeInMillis());
                        refreshRange(dispo, holder.rangeBar);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        holder.timeTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        StringBuffer buf = new StringBuffer();
                        buf
                                .append(String.format("%02d", selectedHour))
                                .append(":")
                                .append(String.format("%02d", selectedMinute));
                        holder.timeTo.setText(buf);
                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.HOUR_OF_DAY, selectedHour);
                        cal.set(Calendar.MINUTE, selectedMinute);
                        dispo.setTimeTo(cal.getTimeInMillis());
                        refreshRange(dispo, holder.rangeBar);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        holder.lundi.setOnClickListener(getOnclickDay(dispo, Calendar.MONDAY, holder.lundi, holder.txtLundi));
        holder.mardi.setOnClickListener(getOnclickDay(dispo, Calendar.TUESDAY, holder.mardi, holder.txtMardi));
        holder.mercredi.setOnClickListener(getOnclickDay(dispo, Calendar.WEDNESDAY, holder.mercredi, holder.txtMercredi));
        holder.jeudi.setOnClickListener(getOnclickDay(dispo, Calendar.THURSDAY, holder.jeudi, holder.txtJeudi));
        holder.vendredi.setOnClickListener(getOnclickDay(dispo, Calendar.FRIDAY, holder.vendredi, holder.txtVendredi));
        holder.samedi.setOnClickListener(getOnclickDay(dispo, Calendar.SATURDAY, holder.samedi, holder.txtSamedi));
        holder.dimanche.setOnClickListener(getOnclickDay(dispo, Calendar.SUNDAY, holder.dimanche, holder.txtDimanche));
    }

    private View.OnClickListener getOnclickDay(final A3techDisponibility dispo, final int day, final RelativeLayout view, final TextView label) {

        View.OnClickListener onClickListenerDay = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (day) {
                    case Calendar.MONDAY:
                        dispo.setValideForMonday(!dispo.getValideForMonday());
                        if(dispo.getValideForMonday()){
                            view.setBackground(context.getResources().getDrawable(R.drawable.circle_layout_days_selected));
                            label.setTextColor(Color.WHITE);
                        }else{
                            view.setBackground(context.getResources().getDrawable(R.drawable.circle_layout_days));
                            label.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                        }
                        break;
                    case Calendar.TUESDAY:
                        dispo.setValideForTuesday(!dispo.getValideForTuesday());
                        if(dispo.getValideForTuesday()){
                            view.setBackground(context.getResources().getDrawable(R.drawable.circle_layout_days_selected));
                            label.setTextColor(Color.WHITE);
                        }else{
                            view.setBackground(context.getResources().getDrawable(R.drawable.circle_layout_days));
                            label.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                        }
                        break;
                    case Calendar.WEDNESDAY:
                        dispo.setValideForWednesday(!dispo.getValideForWednesday());
                        if(dispo.getValideForWednesday()){
                            view.setBackground(context.getResources().getDrawable(R.drawable.circle_layout_days_selected));
                            label.setTextColor(Color.WHITE);
                        }else{
                            view.setBackground(context.getResources().getDrawable(R.drawable.circle_layout_days));
                            label.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                        }
                        break;
                    case Calendar.THURSDAY:
                        dispo.setValideForThursday(!dispo.getValideForThursday());
                        if(dispo.getValideForThursday()){
                            view.setBackground(context.getResources().getDrawable(R.drawable.circle_layout_days_selected));
                            label.setTextColor(Color.WHITE);
                        }else{
                            view.setBackground(context.getResources().getDrawable(R.drawable.circle_layout_days));
                            label.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                        }
                        break;
                    case Calendar.FRIDAY:
                        dispo.setValideForFriday(!dispo.getValideForFriday());
                        if(dispo.getValideForFriday()){
                            view.setBackground(context.getResources().getDrawable(R.drawable.circle_layout_days_selected));
                            label.setTextColor(Color.WHITE);
                        }else{
                            view.setBackground(context.getResources().getDrawable(R.drawable.circle_layout_days));
                            label.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                        }
                        break;
                    case Calendar.SATURDAY:
                        dispo.setValideForSaturday(!dispo.getValideForSaturday());
                        if(dispo.getValideForSaturday()){
                            view.setBackground(context.getResources().getDrawable(R.drawable.circle_layout_days_selected));
                            label.setTextColor(Color.WHITE);
                        }else{
                            view.setBackground(context.getResources().getDrawable(R.drawable.circle_layout_days));
                            label.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                        }
                        break;
                    case Calendar.SUNDAY:
                        dispo.setValideForSunday(!dispo.getValideForSunday());
                        if(dispo.getValideForSunday()){
                            view.setBackground(context.getResources().getDrawable(R.drawable.circle_layout_days_selected));
                            label.setTextColor(Color.WHITE);
                        }else{
                            view.setBackground(context.getResources().getDrawable(R.drawable.circle_layout_days));
                            label.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                        }
                        break;
                }
            }
        };
        return onClickListenerDay;
    }

    @Override
    public int getItemCount() {
        return listeObjects != null ? listeObjects.size() : 0;
    }


    private void refreshRange(A3techDisponibility dispo, RangeSeekBar rangeBar) {
        final Calendar calFrom = Calendar.getInstance();
        calFrom.setTimeInMillis(dispo.getTimeFrom());
        Integer timeFromHours = calFrom.get(Calendar.HOUR_OF_DAY);
        Integer timeFromMinutes = calFrom.get(Calendar.MINUTE);
        Float fromTime = Float.valueOf(timeFromHours + timeFromMinutes / 60);

        Calendar calTo = Calendar.getInstance();
        calTo.setTimeInMillis(dispo.getTimeTo());
        Integer timeToHours = calTo.get(Calendar.HOUR_OF_DAY);
        Integer timeToMinutes = calTo.get(Calendar.MINUTE);
        Float toTime = Float.valueOf(timeToHours + timeToMinutes / 60);

        /*if(fromTime > toTime){
            rangeBar.setValue(fromTime, 24);
            rangeBar.setValue(fromTime, toTime);
        }*/
        rangeBar.setValue(fromTime, toTime);
        if (onRangeTimeChangedListener != null) {
            onRangeTimeChangedListener.rangeChanged(dispo);
        }
    }

    protected static class SimpleItemVH extends RecyclerView.ViewHolder {
        TextView timeFrom;
        TextView timeTo;
        RangeSeekBar rangeBar;
        RelativeLayout lundi, mardi, mercredi, jeudi, vendredi, samedi, dimanche;
        TextView txtLundi, txtMardi, txtMercredi, txtJeudi, txtVendredi, txtSamedi, txtDimanche;

        public SimpleItemVH(View itemView) {
            super(itemView);
            timeTo = itemView.findViewById(R.id.to_time);
            timeFrom = itemView.findViewById(R.id.from_time);
            rangeBar = itemView.findViewById(R.id.rangebar);

            txtLundi = itemView.findViewById(R.id.label_lundi);
            txtMardi = itemView.findViewById(R.id.label_mardi);
            txtMercredi = itemView.findViewById(R.id.label_mercredi);
            txtJeudi = itemView.findViewById(R.id.label_jeudi);
            txtVendredi = itemView.findViewById(R.id.label_vendredi);
            txtSamedi = itemView.findViewById(R.id.label_samedi);
            txtDimanche = itemView.findViewById(R.id.label_dimanche);


            lundi = itemView.findViewById(R.id.lundi_container);
            mardi = itemView.findViewById(R.id.mardi_container);
            mercredi = itemView.findViewById(R.id.mercredi_container);
            jeudi = itemView.findViewById(R.id.jeudi_container);
            vendredi = itemView.findViewById(R.id.vendredi_container);
            samedi = itemView.findViewById(R.id.samedi_container);
            dimanche = itemView.findViewById(R.id.dimanche_container);
        }
    }


    OnRangeTimeChangedListener onRangeTimeChangedListener;

    public void setOnRangeTimeChangedListener(OnRangeTimeChangedListener onRangeTimeChangedListener) {
        this.onRangeTimeChangedListener = onRangeTimeChangedListener;
    }

    public interface OnRangeTimeChangedListener {
        void rangeChanged(A3techDisponibility dispo);
    }
}