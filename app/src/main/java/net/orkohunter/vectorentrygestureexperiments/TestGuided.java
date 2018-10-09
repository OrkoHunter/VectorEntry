package net.orkohunter.vectorentrygestureexperiments;

import android.content.Intent;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import net.orkohunter.vectorentrygestureexperiments.R;

import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class TestGuided extends AppCompatActivity {

    private static final String DEBUG_TAG= "TEST GUIDED";
    private float startX;
    private float startY;
    private float endX;
    private float endY;

    private  boolean isDebugTest = false;

    /* Declare sound files */

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final MediaPlayer mp_start_guided = MediaPlayer.create(getApplicationContext(), R.raw.start_guided);
        final MediaPlayer mp_ok = MediaPlayer.create(getApplicationContext(), R.raw.ok);
        final MediaPlayer mp_error = MediaPlayer.create(getApplicationContext(), R.raw.error);
        final MediaPlayer mp_bottom_left_to_top_right = MediaPlayer.create(getApplicationContext(), R.raw.bottom_left_to_top_right);
        final MediaPlayer mp_bottom_right_to_top_left = MediaPlayer.create(getApplicationContext(), R.raw.bottom_right_to_top_left);
        final MediaPlayer mp_bottom_to_top = MediaPlayer.create(getApplicationContext(), R.raw.bottom_to_top);
        final MediaPlayer mp_left_to_right = MediaPlayer.create(getApplicationContext(), R.raw.left_to_right);
        final MediaPlayer mp_right_to_left = MediaPlayer.create(getApplicationContext(), R.raw.right_to_left);
        final MediaPlayer mp_top_left_to_bottom_right = MediaPlayer.create(getApplicationContext(), R.raw.top_left_to_bottom_right);
        final MediaPlayer mp_top_right_to_bottom_left = MediaPlayer.create(getApplicationContext(), R.raw.top_right_to_bottom_left);
        final MediaPlayer mp_top_to_bottom = MediaPlayer.create(getApplicationContext(), R.raw.top_to_bottom);

        setContentView(R.layout.activity_test_guided);


        Intent intent = getIntent();
        isDebugTest = intent.getBooleanExtra("isDebugTest", false);
        Log.d(DEBUG_TAG, "isDebugTest" + Boolean.toString(isDebugTest));

        mp_start_guided.start();



    }

    public double[] getDistancesFromEdges(float X, float Y) {
        /* Return an array with distances from corners in _inches_ */
        /* [TOP_LEFT_CORNER, TOP_RIGHT_CORNER, BOTTOM_RIGHT_CORNER, BOTTOM_LEFT_CORNER] */

        /* Get Maximum X and Y */
        Display mdisp = getWindowManager().getDefaultDisplay();
        Point mdispSize = new Point();
        mdisp.getSize(mdispSize);
        int maxX = mdispSize.x;
        int maxY = mdispSize.y;


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        double dist_top_left = Math.sqrt(Math.pow(Math.abs(X - 0) / dm.xdpi, 2) + Math.pow(Math.abs(Y - 0) / dm.ydpi, 2));
        double dist_top_right = Math.sqrt(Math.pow(Math.abs(X - maxX) / dm.xdpi, 2) + Math.pow(Math.abs(Y - 0) / dm.ydpi, 2));
        double dist_bottom_right = Math.sqrt(Math.pow(Math.abs(X - maxX) / dm.xdpi, 2) + Math.pow(Math.abs(Y - maxY) / dm.ydpi, 2));
        double dist_bottom_left = Math.sqrt(Math.pow(Math.abs(X - 0) / dm.xdpi, 2) + Math.pow(Math.abs(Y - maxY) / dm.ydpi, 2));

        double[] distances = new double[] {dist_top_left, dist_top_right, dist_bottom_right, dist_bottom_left};

        return distances;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        int action = event.getAction();

        switch(action) {
            case (MotionEvent.ACTION_DOWN) :
                startX = event.getX();
                startY = event.getY();
                Log.d(DEBUG_TAG,"Action was DOWN");
                Log.d(DEBUG_TAG, "X = " + Float.toString(startX));
                Log.d(DEBUG_TAG, "Y = " + Float.toString(startY));
                double[] distances = getDistancesFromEdges(event.getX(), event.getY());
                // Log.d(DEBUG_TAG, "distances = " + Arrays.toString(distances));
                return true;
            case (MotionEvent.ACTION_MOVE) :
                Log.d(DEBUG_TAG,"Action was MOVE");
                return true;
            case (MotionEvent.ACTION_UP) :
                endX = event.getX();
                endY = event.getY();
                Log.d(DEBUG_TAG,"Action was UP");
                Log.d(DEBUG_TAG, "X = " + Float.toString(endX));
                Log.d(DEBUG_TAG, "Y = " + Float.toString(endY));
                return true;
            case (MotionEvent.ACTION_CANCEL) :
                Log.d(DEBUG_TAG,"Action was CANCEL");
                return true;
            case (MotionEvent.ACTION_OUTSIDE) :
                Log.d(DEBUG_TAG,"Movement occurred outside bounds " +
                        "of current screen element");
                return true;
            default :
                return super.onTouchEvent(event);
        }

    }

}
