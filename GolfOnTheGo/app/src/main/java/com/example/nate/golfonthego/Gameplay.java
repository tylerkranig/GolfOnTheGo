package com.example.nate.golfonthego;

import android.content.Context;
import android.graphics.Point;
import android.os.SystemClock;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.Toast;

import com.example.nate.golfonthego.Models.Course;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.Observable;
import java.util.Observer;


/**
 * Created by Leo on 10/31/2017.
 */

public class Gameplay implements Observer{

    private GoogleMap courseMap;
    private Marker ballMark;
    private Context courseCon;
    private Swinger playerSwing;
    private static Gameplay game;
    private Course course;
    private int holeNumber;

    public boolean gamePlayInProgress;
    public boolean gameHasBeenStarted;

    private Gameplay(){ gameHasBeenStarted = false; }

    public void setParameters(GoogleMap gm, Marker m, Context c, int courseNumber, int holeNumber){
        courseMap = gm;
        ballMark = m;
        courseCon = c;
        playerSwing = Swinger.getSwinger();
        playerSwing.addObserver(this);
        course = new Course(courseNumber);
        holeNumber = holeNumber;
    }

    public static Gameplay getGameplay(){
        if (game == null)
            return game = new Gameplay();
        return game;
    }

    public void executeSwing(Button b, FragmentManager fg){
        b.setVisibility(View.INVISIBLE);
        b.setVisibility(View.GONE);

        swingFragment swingFrag = new swingFragment();
        FragmentTransaction swingFragTransaction = fg.beginTransaction();
        swingFragTransaction.replace(R.id.swingFrame, swingFrag);
        swingFragTransaction.addToBackStack(null);
        swingFragTransaction.commit();
    }

    public void moveBall(Context con){
        //test to see if swinger singleton works
        if(playerSwing != null){
            CharSequence text =
                    "\nPower:     " + playerSwing.power +
                    "\nOverswing:  " + playerSwing.overswing +
                    "\nError:      " + playerSwing.error +
                    "\nScore:      " + playerSwing.score;
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(con, text, duration);
            toast.show();
            animateMarker(ballMark, course.getHoleLocation(holeNumber), false);
            courseCon = con;
            playerSwing.first = true;
            this.gamePlayInProgress = false;
        }
    }

    public void animateMarker(final Marker marker, final LatLng toPosition,
                              final boolean hideMarker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = courseMap.getProjection();
        Point startPoint = proj.toScreenLocation(marker.getPosition());
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final long duration = 5000;

        final Interpolator interpolator = new LinearInterpolator();
        handler.post(new Runnable() {
            @Override
            public void run() {

                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * toPosition.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * toPosition.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));

                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                } else {
                    if (hideMarker) {
                        marker.setVisible(false);
                    } else {
                        marker.setVisible(true);
                    }
                }
            }
        });
    }

    @Override
    public void update(Observable o, Object n){
        System.out.println("why not here tho");
        moveBall(courseCon);
    }
}
