package schedule.star.com.schedule;

import android.app.PendingIntent;

import java.io.Serializable;
import java.util.GregorianCalendar;

/**
 * Created by msı on 10.02.2017.
 */

public class Alarm implements Serializable {
    private String m_alarmTime;
    private boolean isOn;
    private PendingIntent m_pendingIntent;
    private GregorianCalendar m_gregorianCalendar;

    public Alarm(String alarmTime, boolean status, PendingIntent pendingIntent, GregorianCalendar gregorianCalendar )
    {
        m_alarmTime = alarmTime;
        isOn = status;
        m_pendingIntent = pendingIntent;
        m_gregorianCalendar = gregorianCalendar;
    }

    public void setAlarmTime(String alarmTime)
    {
        m_alarmTime = alarmTime;
    }

    public void setStatus(boolean status)
    {
        isOn = status;
    }

    public String getAlarmTime()
    {
        return m_alarmTime;
    }

    public boolean getStatus()
    {
        return isOn;
    }

    public PendingIntent getPendingIntent()
    {
        return m_pendingIntent;
    }

    public long getDifference() {return Math.abs(m_gregorianCalendar.getTimeInMillis() - System.currentTimeMillis());}

    @Override
    public String toString()
    {
        return m_alarmTime;
    }
}
