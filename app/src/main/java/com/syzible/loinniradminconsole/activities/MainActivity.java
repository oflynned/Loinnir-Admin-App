package com.syzible.loinniradminconsole.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.syzible.loinniradminconsole.R;
import com.syzible.loinniradminconsole.fragments.LocalityFrag;
import com.syzible.loinniradminconsole.fragments.ToolsFrag;
import com.syzible.loinniradminconsole.helpers.FragmentUtils;
import com.syzible.loinniradminconsole.helpers.LocalPrefs;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.nav_tools:
                FragmentUtils.setFragment(getFragmentManager(), new ToolsFrag());
                return true;
            case R.id.nav_locality_chats:
                FragmentUtils.setFragment(getFragmentManager(), new LocalityFrag());
                return true;
        }
        return false;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentUtils.setFragment(getFragmentManager(), new ToolsFrag());

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_store_link) {
            Intent intent;
            String appStoreLink = "market://details?id=com.syzible.loinnir";
            String webStoreLink = "https://play.google.com/store/apps/details?id=com.syzible.loinnir";

            try {
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(appStoreLink));
            } catch (android.content.ActivityNotFoundException e) {
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(webStoreLink));
            }

            startActivity(intent);
        } else if (id == R.id.action_log_out) {
            LocalPrefs.logOut(this);
            startActivity(new Intent(this, LoginActivity.class));
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
