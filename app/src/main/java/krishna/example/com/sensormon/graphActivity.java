package krishna.example.com.sensormon;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

/**
 * Created by Krishna Vemuri on 8/28/2017.
 */

public class graphActivity extends AppCompatActivity implements SensorEventListener{

    private GraphView graphAcel,graphProx,graphLight,graphMag;
    private LineGraphSeries<DataPoint> aseries,lseries,pseries,mseries;
    private int i,j,k,l;
    private Button first;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_layout);

        first = (Button)findViewById(R.id.gofirst);
        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(graphActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        graphAcel = (GraphView)findViewById(R.id.graphAcel);
        graphProx = (GraphView)findViewById(R.id.graphProx);
        graphLight = (GraphView)findViewById(R.id.graphLight);
        graphMag = (GraphView)findViewById(R.id.graphMag);

        graphAcel.getViewport().setScrollable(true);
        graphAcel.getViewport().setScalable(true);
        graphAcel.getViewport().setMaxX(1000);
        graphAcel.setTitle("Accelerometer data plot");

        graphMag.getViewport().setScrollable(true);
        graphMag.getViewport().setScalable(true);
        graphMag.getViewport().setMaxX(1000);
        graphMag.setTitle("Magnetic field plot");

        graphProx.getViewport().setScrollable(true);
        graphProx.getViewport().setScalable(true);
        graphProx.getViewport().setMaxX(10);
        graphProx.setTitle("Proimity graph");

        graphLight.getViewport().setScrollable(true);
        graphLight.getViewport().setScalable(true);
        graphLight.getViewport().setMaxX(100);
        graphLight.setTitle("Light Intensity graph");

        SensorManager sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        Sensor lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        Sensor acceleromter = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor magnet = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        Sensor proxim = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if(acceleromter!=null){
            sensorManager.registerListener(this,acceleromter,SensorManager.SENSOR_DELAY_NORMAL);
        }else {
            Toast.makeText(this,"Accelerometer not available",Toast.LENGTH_SHORT).show();
        }

        if(lightSensor!=null){
            sensorManager.registerListener(this,lightSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }else {
            Toast.makeText(this,"Light Sensor not available",Toast.LENGTH_SHORT).show();
        }

        if(magnet!=null){
            sensorManager.registerListener(this,magnet,SensorManager.SENSOR_DELAY_NORMAL);
        }else {
            Toast.makeText(this,"Magnetic Sensor not available",Toast.LENGTH_SHORT).show();
        }

        if(magnet!=null){
            sensorManager.registerListener(this,proxim,SensorManager.SENSOR_DELAY_FASTEST);
        }else {
            Toast.makeText(this,"Proximity Sensor not available",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()){
            case Sensor.TYPE_ACCELEROMETER:
                double rms = Math.sqrt(event.values[0]*event.values[0]+event.values[1]*event.values[1]+event.values[2]*event.values[2]);
                aseries.appendData(new DataPoint(i++,rms),true,1000);
                break;
            case Sensor.TYPE_PROXIMITY:
                pseries.appendData(new DataPoint(j++,event.values[0]),true,10);
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                mseries.appendData(new DataPoint(k++,event.values[0]),true,1000);
                break;
            case Sensor.TYPE_LIGHT:
                lseries.appendData(new DataPoint(l++,event.values[0]),true,100);
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        aseries = new LineGraphSeries<>();
        mseries = new LineGraphSeries<>();
        pseries = new LineGraphSeries<>();
        lseries = new LineGraphSeries<>();

        graphMag.addSeries(mseries);
        graphLight.addSeries(lseries);
        graphProx.addSeries(pseries);
        graphAcel.addSeries(aseries);

        i=j=k=l=0;
    }

}
