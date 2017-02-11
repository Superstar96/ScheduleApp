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
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.util.*;

public class MainActivity extends AppCompatActivity {
    private TimePicker m_alarmTimer;
    public static AlarmAdapter m_alarmAdapter;
    private AlarmReceiver m_alarmReceiver;
    private ListView m_alarmListView;
    private CalendarView m_calendarView;
    private AlarmManager m_alarmManager;
    private PendingIntent m_pendingIntent;
    private GregorianCalendar m_calendar;
    public static ArrayList<Alarm> m_alarms;
    private int m_year=0, m_month=0, m_dayOfMonth=0;
    private static int m_count = 0;

    //*************************************************************************************************************************************************

    //INITIALIZERS

    private void init()
    {
        m_alarms = new ArrayList<>();
        this.initGUI();
    }

    private void initGUI()
    {
        m_alarmTimer = (TimePicker)this.findViewById(R.id.MAINACTIVITY_TIMEPICKER_ALARM);

        m_calendarView = (CalendarView)this.findViewById(R.id.MAINACTIVITY_CALENDARVIEW_CALENDAR);

        m_calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth)
            {
                m_year = year;
                m_month = month + 1;
                m_dayOfMonth = dayOfMonth;

                String date = String.format("***Seçilen Tarih***%nGÜN:%d%nAY:%d%nYIL:%d", dayOfMonth, month+1, year);

                Toast.makeText(MainActivity.this, date, Toast.LENGTH_LONG).show();
            }
        });

        m_alarmListView = (ListView)this.findViewById(R.id.MAINACTIVITY_LISTVIEW_ALARMLIST);
        m_alarmAdapter = new AlarmAdapter(this, m_alarms);
        m_alarmListView.setAdapter(m_alarmAdapter);

        TabHost tabHost = (TabHost)findViewById(R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec tabfirst = tabHost.newTabSpec("TAG1");
        tabfirst.setContent(R.id.tab1);
        tabfirst.setIndicator("TIME");
        tabHost.addTab(tabfirst);

        TabHost.TabSpec tabsecond = tabHost.newTabSpec("TAG2");
        tabsecond.setContent(R.id.tab2);
        tabsecond.setIndicator("DATE");
        tabHost.addTab(tabsecond);

        TabHost.TabSpec tabthird = tabHost.newTabSpec("TAG3");
        tabthird.setContent(R.id.tab3);
        tabthird.setIndicator("ALARMS");
        tabHost.addTab(tabthird);
    }

    //*************************************************************************************************************************************************

    //BUTTON METHODS

    public void onExitButtonClicked(View v) {this.finish();}

    public void onListButtonClicked(View v) //Kullanıcın ayarladağı saatleri tutan ArrayList'i Bundle'a koy ve Intent ile diğer aktiviteyi başlatıp ona gönder.
    {
        Intent intent = new Intent(this, ListActivity.class);

        startActivity(intent);
    }

    public void onSetAlarmButtonClicked(View v) //Kullanıcının ayarladığı saatin yazısını ArrayList'e aktar
    {
        //Toast.makeText(this, String.format("m_year:%d%nm_month:%d%nm_dayOfMonth:%d%n", m_year, m_month, m_dayOfMonth), Toast.LENGTH_LONG).show();

/*        String time = String.valueOf(m_alarmTimer.getHour());
        time += ":" + String.valueOf(m_alarmTimer.getMinute());*/

        Intent intent = new Intent (this, AlarmReceiver.class);
        //intent.putExtra("TIME", time);

        m_calendar = new GregorianCalendar();

        m_calendar = this.setDate(m_calendar);

        if(m_calendar == null) {
            return;
        }

        long difference = m_calendar.getTimeInMillis() - System.currentTimeMillis();

        if(difference <= 0) {
            Toast.makeText(this, "Geçmiş Bir Tarihe Alarm Ayarlanamaz!!!", Toast.LENGTH_LONG).show();
            return;
        }

        Toast.makeText(this, "***ALARM AYARLANDI***", Toast.LENGTH_LONG).show();

        Toast.makeText(this, "Alarmın Çalmasına Kalan Süre = " + difference/1000. + " sn", Toast.LENGTH_LONG).show();

        String dateTime = this.getDate(m_calendar);

        Toast.makeText(this, dateTime, Toast.LENGTH_LONG).show();


        m_alarmManager = (AlarmManager)this.getSystemService(ALARM_SERVICE);
        m_pendingIntent = PendingIntent.getBroadcast(this, m_count++ , intent, 0);

        m_alarms.add(new Alarm(dateTime, true, m_pendingIntent, m_calendar));
        m_alarmAdapter.notifyDataSetChanged();

        m_alarmManager.setExact(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() +  difference, m_pendingIntent);
    }

    //*************************************************************************************************************************************************

    //CLASS METHODS

    private GregorianCalendar setDate(GregorianCalendar gregorianCalendar)
    {
        if(m_year == 0 || m_month == 0 || m_dayOfMonth == 0) {
            Toast.makeText(this, "Önce geçerli bir tarih seçmelisiniz!!!", Toast.LENGTH_LONG).show();
            return null;
        }

        gregorianCalendar.set(Calendar.YEAR, m_year);
        gregorianCalendar.set(Calendar.MONTH, m_month-1);
        gregorianCalendar.set(Calendar.DAY_OF_MONTH, m_dayOfMonth);
        gregorianCalendar.set(Calendar.HOUR_OF_DAY, m_alarmTimer.getCurrentHour());
        gregorianCalendar.set(Calendar.MINUTE, m_alarmTimer.getCurrentMinute());
        gregorianCalendar.set(Calendar.SECOND, 0);

        return gregorianCalendar;
    }

    private String getDate(GregorianCalendar gregorianCalendar)
    {
        String date = String.format("%d:%d %d/%d/%d",gregorianCalendar.get(Calendar.HOUR_OF_DAY), gregorianCalendar.get(Calendar.MINUTE),
                                                                                        gregorianCalendar.get(Calendar.DAY_OF_MONTH), gregorianCalendar.get(Calendar.MONTH) + 1, gregorianCalendar.get(Calendar.YEAR));
        return date;
    }

    //*************************************************************************************************************************************************

    //OVERRIDE METHODS

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
