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
    private Months []  m_months;

    //*************************************************************************************************************************************************

    //INITIALIZERS

    private void init()
    {
        m_alarms = new ArrayList<>();
        m_months = Months.values();
        this.setAlarmList();
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

                String date = String.format(Locale.ENGLISH, "***Seçilen Tarih***%n        GÜN:%d%n        AY:%s%n        YIL:%d", dayOfMonth, m_months[m_month], year);

                Toast.makeText(MainActivity.this, date, Toast.LENGTH_SHORT).show();
            }
        });

        m_alarmListView = (ListView)this.findViewById(R.id.MAINACTIVITY_LISTVIEW_ALARMLIST);
        m_alarmAdapter = new AlarmAdapter(this, m_alarms);
        m_alarmListView.setAdapter(m_alarmAdapter);

        //TAB Görünümünün Ayarlanması
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

    public void onSetAlarmButtonClicked(View v)  //Alarmı Ayarlayan Method
    {
        Intent intent = new Intent (this, AlarmReceiver.class);

        m_calendar = new GregorianCalendar();

        m_calendar = this.setDate(m_calendar); //Saati ve Tarihi alıp nesneye set et

        if(m_calendar == null) { //Kullanıcı henüz bir tarih belirtmemişse Ekleme
            return;
        }

        long difference = m_calendar.getTimeInMillis() - System.currentTimeMillis();

        if(difference <= 0) { //Alarmın Zamanı Zaten Geçmişse Ekleme
            Toast.makeText(this, "Geçmiş Bir Tarihe Alarm Ayarlanamaz!!!", Toast.LENGTH_LONG).show();
            return;
        }

        Toast.makeText(this, "***ALARM AYARLANDI***", Toast.LENGTH_SHORT).show();

        String dateTime = this.getDateTime(m_calendar); //Tarih ve Saatin Yazı Biçimini Al

        Toast.makeText(this, String.format(Locale.ENGLISH, "Alarm Zamanı%n") + dateTime, Toast.LENGTH_LONG).show();


        m_alarmManager = (AlarmManager)this.getSystemService(ALARM_SERVICE);
        m_pendingIntent = PendingIntent.getBroadcast(this, m_count , intent, 0);

        m_alarms.add(new Alarm(dateTime, false, m_count++, m_pendingIntent, m_calendar)); //Kurulan Alarmı Diziye Ekle
        m_alarmAdapter.notifyDataSetChanged(); //ListView'u bilgilendir

        m_alarmManager.setExact(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() +  difference, m_pendingIntent); //Alarmı Başlat
    }

    //*************************************************************************************************************************************************

    //CLASS METHODS

    private void setAlarmList()
    {
        FileReadWrite file = new FileReadWrite(this);

        m_alarms = file.getAlarms();
    }

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

    private String getDateTime(GregorianCalendar gregorianCalendar)
    {
        String minute = String.valueOf(gregorianCalendar.get(Calendar.MINUTE));
        String hour = String.valueOf(gregorianCalendar.get(Calendar.HOUR_OF_DAY));
        String day = String.valueOf(gregorianCalendar.get(Calendar.DAY_OF_MONTH));
        String month = String.valueOf(gregorianCalendar.get(Calendar.MONTH) + 1);
        String year = String.valueOf(gregorianCalendar.get(Calendar.YEAR));

        if (minute.length() < 2)
            minute = "0" + minute;

        if (hour.length() < 2)
            hour = "0" + hour;

        if (day.length() < 2)
            day = "0" + day;

        if (month.length() < 2)
            month = "0" + month;

        return String.format(Locale.ENGLISH, "%s:%s %s/%s/%s", hour, minute, day, month, year);
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
    protected void onPause()
    {
        FileReadWrite file = new FileReadWrite(this);
        file.setAlarms();
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.init();
    }
}