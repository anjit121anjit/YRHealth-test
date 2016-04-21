package com.techies11;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    Button btn_explore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        btn_explore =(Button) findViewById(R.id.btn_explore);

        btn_explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent ex = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(ex);
            }
        });



        // Set the drawer toggle as the DrawerListener
        // mDrawerLayout.setDrawerListener(mDrawerToggle);



        mNavigationView = (NavigationView) findViewById(R.id.drawerstuff);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);



        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();


                if (menuItem.getItemId() == R.id.nav_item_home) {

                    Intent i = new Intent(HomeActivity.this, HomeActivity.class);
                    startActivity(i);

                }

                if (menuItem.getItemId() == R.id.nav_item_doctors) {

                    Intent i = new Intent(HomeActivity.this, MainActivity.class);
                    startActivity(i);
                }
                if (menuItem.getItemId() == R.id.nav_item_log_sign) {

                    Intent i = new Intent(HomeActivity.this, FbLoginActivity.class);
                    startActivity(i);
                }
                if (menuItem.getItemId() == R.id.nav_item_appointment) {

                    Intent i = new Intent(HomeActivity.this, MyAppointmentActivity.class);
                    startActivity(i);

                }

                if (menuItem.getItemId() == R.id.nav_item_doc_admin_log) {

                    Uri uri = Uri.parse("http://www.bridgetechnosoft.com/hospital/ServerSite/Source/index.php"); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);

                }

                if (menuItem.getItemId() == R.id.nav_item_abt_us) {

                    Intent i = new Intent(HomeActivity.this, AboutusActivity.class);
                    startActivity(i);
                }
                if (menuItem.getItemId() == R.id.nav_item_contact_us) {



                    String mob = "+353899564620";
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", mob, null));
                    startActivity(intent);
                    /*Intent intent = new Intent("android.intent.action.CALL");
                    Uri data = Uri.parse("tel:"+ mob );
                    intent.setData(data);
                    startActivity(intent);*/
                }
                if (menuItem.getItemId() == R.id.nav_item_mail_us) {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto","mayankchauhan95@gmail.com", null));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
                    startActivity(Intent.createChooser(emailIntent, "Send email..."));

                }

                return false;
            }

        });

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, toolbar,R.string.app_name,
                R.string.app_name);



        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();

        return;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        Button setting = (Button) findViewById(R.id.action_settings);
        Button prfl = (Button) findViewById(R.id.action_profile);
        Button d_plan = (Button) findViewById(R.id.action_dietplan);
        Button rate = (Button) findViewById(R.id.action_rate);
        Button share = (Button) findViewById(R.id.action_share);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {


            case R.id.action_settings :
                Intent in = new Intent(HomeActivity.this,SettingsActivity.class);
                startActivity(in);

                break;
            case R.id.action_profile:
                Intent in1 = new Intent(HomeActivity.this,ProfileActivity.class);
                startActivity(in1);

                break;
            case R.id.action_dietplan:
                Intent in3 = new Intent(HomeActivity.this,DisplayActivity.class);
                startActivity(in3);

                break;
            case R.id.action_rate:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + this.getPackageName()));
                startActivity(browserIntent);

                break;
            case R.id.action_share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + this.getPackageName());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);

                break;
        }
        return false;
    }
}
