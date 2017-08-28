package krishna.example.com.sensormon;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private TextView accelmeter,luminance,magnetic,proximity;
    private Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        next = (Button)findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,graphActivity.class);
                startActivity(intent);
            }
        });

        accelmeter = (TextView)findViewById(R.id.accelerometer);
        luminance = (TextView)findViewById(R.id.luminance);
        magnetic = (TextView)findViewById(R.id.magnetic);
        proximity = (TextView)findViewById(R.id.proximity);

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
            sensorManager.registerListener(this,proxim,SensorManager.SENSOR_DELAY_NORMAL);
        }else {
            Toast.makeText(this,"Proximity Sensor not available",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()){
            case Sensor.TYPE_ACCELEROMETER:
                accelmeter.setText(Math.sqrt(event.values[0]*event.values[0]+event.values[1]*event.values[1]+event.values[2]*event.values[2])+"");
                break;
            case Sensor.TYPE_PROXIMITY:
                proximity.setText(""+event.values[0]);
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                magnetic.setText(""+event.values[0]);
                break;
            case Sensor.TYPE_LIGHT:
                luminance.setText(""+event.values[0]);
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
