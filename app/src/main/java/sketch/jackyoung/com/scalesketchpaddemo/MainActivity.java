package sketch.jackyoung.com.scalesketchpaddemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import sketch.jackyoung.com.scalesketchpaddemo.workbox.PathView;
import sketch.jackyoung.com.scalesketchpaddemo.workbox.PenStrockAndColorSelect;
import sketch.jackyoung.com.scalesketchpaddemo.workbox.PointPath;

public class MainActivity extends AppCompatActivity {

    PathView pathView=null;
    PenStrockAndColorSelect penStrockAndColorSelect=null;
    LinearLayout linearLayout=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RelativeLayout rootView= (RelativeLayout) findViewById(R.id.mainRootView);
         pathView=new PathView(getApplicationContext());


        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        rootView.addView(pathView,1);

        linearLayout  = (LinearLayout) findViewById(R.id.ll_btn_group);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        penStrockAndColorSelect = (PenStrockAndColorSelect) findViewById(R.id.strock_color_select);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }

    public void eraser(View view) {
        pathView.useEraser();
    }
    public void stroke(View view) {
        linearLayout.setVisibility(View.GONE);
        penStrockAndColorSelect.setVisibility(View.VISIBLE);
        penStrockAndColorSelect.setCURRENT_TYPE(PenStrockAndColorSelect.STROCK_TYPE);
        penStrockAndColorSelect.setCallback(new PenStrockAndColorSelect.ColorSelectorCallback() {
            @Override
            public void onColorSelectCancel(PenStrockAndColorSelect sender) {

            }

            @Override
            public void onColorSelectChange(PenStrockAndColorSelect sender) {
                int position=sender.getPenPosition();
                penStrockAndColorSelect.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
                pathView.setPaintWidth(position);
            }
        });
    }
    //why  not  refresh?
    public void undo(View view) {
        pathView.undoPath();
    }

    public void color(View view) {
        linearLayout.setVisibility(View.GONE);
        penStrockAndColorSelect.setVisibility(View.VISIBLE);
        penStrockAndColorSelect.setCURRENT_TYPE(PenStrockAndColorSelect.COLOR_TYPE);
        penStrockAndColorSelect.setCallback(new PenStrockAndColorSelect.ColorSelectorCallback() {
            @Override
            public void onColorSelectCancel(PenStrockAndColorSelect sender) {

            }

            @Override
            public void onColorSelectChange(PenStrockAndColorSelect sender) {
                int position=sender.getPenPosition();
                penStrockAndColorSelect.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
                pathView.setPaintColor(position);
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_settings2:
                // User chose the "action_settings2" action, mark the current item
                // as a favorite...
                return true;
            case R.id.action_settings3:
                // User chose the "action_settings2" action, mark the current item
                // as a favorite...

                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }



}
