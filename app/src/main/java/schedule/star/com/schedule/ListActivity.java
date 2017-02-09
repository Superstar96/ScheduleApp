package schedule.star.com.schedule;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    private ListView m_listView;

    private void init()
    {
        m_listView = (ListView)this.findViewById(R.id.LISTACTIVITY_LISTVIEW_ALARMLIST);
        Intent intent = this.getIntent();

        Bundle bundle = intent.getBundleExtra("BUNDLE");

        ArrayList<Alarm> alarms = new ArrayList<>();

        alarms = (ArrayList<Alarm>) bundle.getSerializable("LIST");

        AlarmAdapter alarmAdapter = new AlarmAdapter(this, alarms);

        m_listView.setAdapter(alarmAdapter);
    }

    public void onAlarmOnButtonClicked(View v)
    {
        Toast.makeText(this, "ALARM IS ALREADY ON", Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        this.init();
    }
}
