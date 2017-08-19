package sketchpad.jackyoung.com.scalesketchpaddemo3;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import sketchpad.jackyoung.com.scalesketchpaddemo3.workbox.BitmapUtils;
import sketchpad.jackyoung.com.scalesketchpaddemo3.workbox.PathView;
import sketchpad.jackyoung.com.scalesketchpaddemo3.workbox.PenStrockAndColorSelect;
import sketchpad.jackyoung.com.scalesketchpaddemo3.workbox.ScaleSketchView;

public class MainActivity extends AppCompatActivity {
    private static final int PICTURE_REQUEST_GALLERY = 110;
    private static final int PICTURE_REQUEST_GALLERY_PERMISSION = 120;

    private ScaleSketchView pathView=null;
    private PenStrockAndColorSelect penStrockAndColorSelect=null;
    private LinearLayout linearLayout=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RelativeLayout rootView= (RelativeLayout) findViewById(R.id.mainRootView);
        pathView=new ScaleSketchView(getApplicationContext());


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
                penStrockAndColorSelect.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
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
    //why  not  refresh? because of Xmode
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
                penStrockAndColorSelect.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
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

    public void chooseGalleryPhoto() {

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PICTURE_REQUEST_GALLERY_PERMISSION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }else {
            Intent picture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(picture, PICTURE_REQUEST_GALLERY);
        }



    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PICTURE_REQUEST_GALLERY_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Intent picture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(picture, PICTURE_REQUEST_GALLERY);

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    public void composeEmail(String[] addresses, String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:zuogewoniu@hotmail.com")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_photo:
                // get photo  from gallery or camery,then add to PathView
                chooseGalleryPhoto();
                return true;

            case R.id.about_me:
                //link  me  github
                openWebPage("https://github.com/ShaunSheep/ScaleSketchPadDemo");
                return true;
            case R.id.feed_back:
                // send email to me
                composeEmail(new String[]{"zuogewoniu@hotmail.com"},"JackYoung");

                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PICTURE_REQUEST_GALLERY&&resultCode==RESULT_OK){
            if (data == null)
                return;
            Bitmap resultBimtap = BitmapUtils.getBitmapPathFromData(data, getApplicationContext());

            pathView.addBackgroudImage(resultBimtap);
        }

    }
}
