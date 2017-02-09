package schedule.star.com.schedule;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

/**
 * Created by msı on 10.02.2017.
 */

public class AlarmAdapter extends BaseAdapter {

    private LayoutInflater m_layoutInflater;
    private List<Alarm> m_alarmList;


    public AlarmAdapter(Activity activity, List<Alarm> list)
    {
        m_layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        m_alarmList = list;
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
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View alarmView;

        alarmView = m_layoutInflater.inflate(R.layout.layout_alarm, null);

        TextView textView = (TextView) alarmView.findViewById(R.id.ALARMLAYOUT_TEXTVIEW_ALARMTIME);

        textView.setText(m_alarmList.get(position).getAlarmTime());

        return alarmView;

    }
}
