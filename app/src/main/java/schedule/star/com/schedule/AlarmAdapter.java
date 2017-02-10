package schedule.star.com.schedule;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by msı on 10.02.2017.
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
    public View getView(int position, View convertView, ViewGroup parent) //Inflate işlemini yapıp oluşan View'u geri döndürecek method (ListView'un her bir satırında gösterilecek View burada oluşturulur)
    {
        View alarmView;

        alarmView = m_layoutInflater.inflate(R.layout.layout_alarm, null);

        TextView textView = (TextView) alarmView.findViewById(R.id.ALARMLAYOUT_TEXTVIEW_ALARMTIME);

        textView.setText(m_alarmList.get(position).getAlarmTime());

        ImageButton button = (ImageButton) alarmView.findViewById(R.id.ALARMLAYOUT_BUTTON_CANCEL);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(m_context, "CANCELED BUTTON", Toast.LENGTH_LONG).show();
            }
        });

        return alarmView;

    }
}
