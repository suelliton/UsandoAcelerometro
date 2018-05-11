package com.example.aluno.usandoacelerometro;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class PrincipalActivity extends Activity implements SensorEventListener {
    MediaPlayer mp_direita  ;
    MediaPlayer mp_esquerda ;
    MediaPlayer mp_frente  ;
    MediaPlayer mp_tras ;
    MediaPlayer mp_centralizado ;
    boolean centralizado = false;


          private TextView tvValorX;
       private TextView tvValorY;
      private TextView tvValorZ;

              private SensorManager mSensorManager;
      private Sensor mAcelerometro;

              @Override
       protected void onCreate(Bundle savedInstanceState) {
                  super.onCreate(savedInstanceState);
                  setContentView(R.layout.activity_principal);
                  mp_direita  = MediaPlayer.create(this, R.raw.incline_para_direita);
                  mp_esquerda  = MediaPlayer.create(this, R.raw.incline_para_esquerda);
                  mp_frente = MediaPlayer.create(this, R.raw.incline_para_frente);
                  mp_tras = MediaPlayer.create(this, R.raw.incline_para_tras);
                  mp_centralizado =  MediaPlayer.create(this, R.raw.aparelho_centralizado);

                 tvValorX = (TextView) findViewById( R.id.tvValorX );
                 tvValorY = (TextView) findViewById( R.id.tvValorY );
                 tvValorZ = (TextView) findViewById( R.id.tvValorZ );

                mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
                mAcelerometro = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


           }

              @Override
       protected void onResume(){
                super.onResume();
                 mSensorManager.registerListener(this, mAcelerometro, SensorManager.SENSOR_DELAY_UI);
              }

                @Override
     protected void onPause(){
                super.onPause();
                  mSensorManager.unregisterListener(this);
              }

              @Override
      public void onSensorChanged(SensorEvent event) {
               float x = event.values[0];
                 float y = event.values[1];
                float z = event.values[2];

                if(x > 0.5 ){
                    //vibrar();
                    centralizado = false;
                    if(!mp_direita.isPlaying()&&!mp_esquerda.isPlaying()&&!mp_frente.isPlaying()&&!mp_tras.isPlaying()) {
                        mp_direita.start();
                    }
                }else if(x < -0.5 ){
                    centralizado = false;
                      if(!mp_direita.isPlaying()&&!mp_esquerda.isPlaying()&&!mp_frente.isPlaying()&&!mp_tras.isPlaying()) {
                          mp_esquerda.start();
                      }
                }else if(y > 0.5 ){
                    centralizado = false;
                    if(!mp_direita.isPlaying()&&!mp_esquerda.isPlaying()&&!mp_frente.isPlaying()&&!mp_tras.isPlaying()) {
                        mp_frente.start();
                    }
                }else if(y < -0.5 ){
                    centralizado = false;
                    if(!mp_direita.isPlaying()&&!mp_esquerda.isPlaying()&&!mp_frente.isPlaying()&&!mp_tras.isPlaying()) {
                        mp_tras.start();
                    }
                }else{
                    if(!centralizado) {
                        if (!mp_direita.isPlaying() && !mp_esquerda.isPlaying() && !mp_frente.isPlaying() && !mp_tras.isPlaying() && !mp_centralizado.isPlaying()) {
                            mp_centralizado.start();
                            centralizado = true;
                        }
                    }
                }

                tvValorX.setText( String.valueOf( x ) );
                 tvValorY.setText( String.valueOf( y ) );
                tvValorZ.setText( String.valueOf( z ) );

            }

               @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {


             }

    private void vibrar()    {
        Vibrator rr = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long milliseconds = 100;//'30' é o tempo em milissegundos, é basicamente o tempo de duração da vibração. portanto, quanto maior este numero, mais tempo de vibração você irá ter
        rr.vibrate(milliseconds);
    }
   }