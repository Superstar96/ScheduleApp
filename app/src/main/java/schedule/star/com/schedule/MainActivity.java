package schedule.star.com.schedule;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.icu.util.TimeUnit;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.*;

public class MainActivity extends AppCompatActivity {
    private TimePicker m_alarmTimer;
    private AlarmReceiver m_alarmReceiver;
    private AlarmManager m_alarmManager;
    private PendingIntent m_pendingIntent;
    private Calendar m_calendar;
    private ArrayList<Alarm> m_alarms;
    private static int m_count = 0;

    private void init()
    {
        m_alarmTimer = (TimePicker)this.findViewById(R.id.MAINACTIVITY_TIMEPICKER_ALARM);
        m_alarms = new ArrayList<>();
    }

    //BUTTON METHODS

    public void onExitButtonClicked(View v) {this.finish();}

    public void onListButtonClicked(View v) //Kullanıcın ayarladağı saatleri tutan ArrayList'i Bundle'a koy ve Intent ile diğer aktiviteyi başlatıp ona gönder.
    {
        Intent intent = new Intent(this, ListActivity.class);

/*     alarm.add(new Alarm("12:45", true));
        alarm.add(new Alarm("14:09", true));
        alarm.add(new Alarm("08:34", true));
        alarm.add(new Alarm("17:29", true));
        alarm.add(new Alarm("23:56", true));*/

        Bundle bundle = new Bundle();
        bundle.putSerializable("LIST", m_alarms);

        intent.putExtra("BUNDLE", bundle);

        startActivity(intent);
    }

    public void onSetAlarmButtonClicked(View v) //Kullanıcının ayarladığı saatin yazısını ArrayList'e aktar
    {
        Toast.makeText(this, "ALARM SETTED", Toast.LENGTH_LONG).show();
        String time = String.valueOf(m_alarmTimer.getHour());
        time += ":" + String.valueOf(m_alarmTimer.getMinute());

        Intent intent = new Intent (this, AlarmReceiver.class);
        intent.putExtra("TIME", time);

        m_alarms.add(new Alarm(time, true));

        m_calendar = new GregorianCalendar();

        //Toast.makeText(this, "Şuan = " + System.currentTimeMillis() + "", Toast.LENGTH_LONG).show();

        m_calendar.set(Calendar.HOUR_OF_DAY, m_alarmTimer.getHour());
        m_calendar.set(Calendar.MINUTE, m_alarmTimer.getMinute());
        m_calendar.set(Calendar.SECOND, 0);

        //Toast.makeText(this, "Ayarlanan = "+ m_calendar.getTimeInMillis() + "", Toast.LENGTH_LONG).show();

        long difference = m_calendar.getTimeInMillis() - System.currentTimeMillis();

        Toast.makeText(this, "FARK = " + difference/1000., Toast.LENGTH_LONG).show();

        //Alttaki Kısım Zamanı Gelince Alarmı Çaldırma İşini Yapacak (Tasarım problemleri çözülene kadar bu kısım yorum satırına alınmıştır)
        /*m_alarmManager = (AlarmManager)this.getSystemService(ALARM_SERVICE);
        m_pendingIntent = PendingIntent.getBroadcast(this, m_count++ , intent, 0);
        m_alarmManager.setExact(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() +  difference, m_pendingIntent);*/
    }

    @Override
    protected void onResume()
    {
        m_alarmReceiver = new AlarmReceiver();

        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.init();
    }
}
