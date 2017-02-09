package schedule.star.com.schedule;

import java.io.Serializable;

/**
 * Created by msı on 10.02.2017.
 */

public class Alarm implements Serializable {
    private String m_alarmTime;
    private boolean isOn;

    public Alarm(String alarmTime, boolean status)
    {
        m_alarmTime = alarmTime;
        isOn = status;
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

    @Override
    public String toString()
    {
        return m_alarmTime;
    }
}
