package schedule.star.com.schedule;

import android.app.PendingIntent;

import java.io.Serializable;
import java.util.GregorianCalendar;

/**
 * Created by SUPERSTAR on 10.02.2017.
 */

public class Alarm implements Serializable {
    private String m_alarmTime;
    private boolean isOn;
    transient private PendingIntent m_pendingIntent;
    private GregorianCalendar m_gregorianCalendar;
    private int m_order;

    public Alarm(String alarmTime, boolean status, int order, PendingIntent pendingIntent, GregorianCalendar gregorianCalendar )
    {
        m_alarmTime = alarmTime;
        isOn = status;
        m_order = order;
        m_pendingIntent = pendingIntent;
        m_gregorianCalendar = gregorianCalendar;
    }

    public int getOrder() { return m_order; }

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

    public void setPendingIntent(PendingIntent pendingIntent) { m_pendingIntent = pendingIntent; }

    public PendingIntent getPendingIntent()
    {
        return m_pendingIntent;
    }

    public GregorianCalendar getCalendar() { return m_gregorianCalendar; }

    public long getDifference()
    {
        long difference = m_gregorianCalendar.getTimeInMillis() - System.currentTimeMillis();

        return difference;
    }

    @Override
    public String toString()
    {
        return m_alarmTime;
    }
}
