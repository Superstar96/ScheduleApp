package schedule.star.com.schedule;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by SUPERSTAR on 10.02.2017.
 */

public class AlarmAdapter extends BaseAdapter {

    private LayoutInflater m_layoutInflater;
    private List<Alarm> m_alarmList;
    private Context m_context;

    public AlarmAdapter(Activity activity, List<Alarm> list) //Constructor
    {
        m_context = activity;
        m_layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE); //Inflate işlemi için Android Sistemin Layout Infllater Servisi kullanılacak.
        m_alarmList = list; //İçinde Alarm bilgilerimizi tutacak List.
    }

    @Override
    public int getCount()
    {
        return m_alarmList.size();
    }

    @Override
    public Alarm getItem(int position)
    {
        return  m_alarmList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) //Inflate işlemini yapıp oluşan View'u geri döndürecek method (ListView'un her bir satırında gösterilecek View burada oluşturulur)
    {
        View alarmView;
        final ViewHolder holder = new ViewHolder();
        final Alarm alarm = this.getItem(position);
        final PendingIntent pendingIntent = alarm.getPendingIntent();

        alarmView = m_layoutInflater.inflate(R.layout.layout_alarm, null);

        holder.alarmTime = (TextView) alarmView.findViewById(R.id.ALARMLAYOUT_TEXTVIEW_ALARMTIME);

        holder.alarmTime.setText(m_alarmList.get(position).getAlarmTime());

        holder.buttonActivate = (ImageButton)alarmView.findViewById(R.id.ALARMLAYOUT_IMAEGBUTTON_ACTIVATE);
        holder.buttonCancel = (ImageButton)alarmView.findViewById(R.id.ALARMLAYOUT_IMAGEBUTTON_CANCEL);
        holder.buttonDelete = (ImageButton)alarmView.findViewById(R.id.ALARMLAYOUT_IMAEGBUTTON_DELETE);


        //holder.buttonActivate.setAlpha(0.3f);

        //Button Listeners

        //Activate Button'u iptal edilmiş bir alarmı eğer tarihi geçmediyse tekrar aktif hale getirir.
        holder.buttonActivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                long difference = alarm.getDifference();

                if(difference <= 0) {
                    Toast.makeText(m_context, "***Tarihi Geçmiş Alarm Tekrar Aktifleştirilemez***", Toast.LENGTH_LONG).show();
                    return;
                }

                Toast.makeText(m_context, "***ALARM AKTİF EDİLDİ***", Toast.LENGTH_LONG).show();

                holder.buttonCancel.setAlpha(1f);
                holder.buttonActivate.setAlpha(0.3f);
                holder.buttonActivate.setClickable(false);
                holder.buttonCancel.setClickable(true);

                alarm.setStatus(false);

                //Toast.makeText(m_context, "Alarmın Çalmasına Kalan Süre " + alarm.getDifference()/1000. + " sn", Toast.LENGTH_LONG).show();

                AlarmManager manager = (AlarmManager) m_context.getSystemService(Context.ALARM_SERVICE);
                manager.setExact(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + alarm.getDifference(), pendingIntent);
            }
        });

        //Cancel Button'u Daha önceden ayarlanmış olan bir alarmı iptal etmek için kullanılır
        holder.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(m_context, "***ALARM İPTAL EDİLDİ***", Toast.LENGTH_LONG).show();

                holder.buttonActivate.setAlpha(1f);
                holder.buttonCancel.setAlpha(0.3f);
                holder.buttonCancel.setClickable(false);
                holder.buttonActivate.setClickable(true);

                alarm.setStatus(true);

                AlarmManager manager = (AlarmManager) m_context.getSystemService(Context.ALARM_SERVICE);
                manager.cancel(pendingIntent);
            }
        });

        //Delete Button'a basılınca Adapter'e verilen ArrayList'ten o satır silinecek ve Adapter bundan haberdar edilecek(Belirli bir alarmı silmek için kullanılır)
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(m_context, "***ALARM SİLİNDİ***", Toast.LENGTH_LONG).show();

                MainActivity.m_alarms.remove(position);
                MainActivity.m_alarmAdapter.notifyDataSetChanged();

                AlarmManager manager = (AlarmManager) m_context.getSystemService(Context.ALARM_SERVICE);
                manager.cancel(pendingIntent);
            }
        });

        if (!alarm.getStatus()) {
            holder.buttonCancel.setClickable(true);
            holder.buttonCancel.setAlpha(1f);
            holder.buttonActivate.setClickable(false);
            holder.buttonActivate.setAlpha(0.3f);

        }
        else {
            holder.buttonCancel.setClickable(false);
            holder.buttonCancel.setAlpha(0.3f);
            holder.buttonActivate.setClickable(true);
            holder.buttonActivate.setAlpha(1f);
        }

        return alarmView;
    }
}
