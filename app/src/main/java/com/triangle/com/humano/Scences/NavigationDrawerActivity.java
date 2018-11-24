package com.triangle.com.humano.Scences;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.triangle.com.humano.R;
import com.triangle.com.humano.Scences.Flagment.FeedFragment;
import com.triangle.com.humano.Scences.Flagment.ProfileFragment;

public class NavigationDrawerActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTranSection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        fragmentManager = getSupportFragmentManager();
        fragmentTranSection = fragmentManager.beginTransaction();

        final FeedFragment feedFragment = new FeedFragment();
        fragmentTranSection.replace(R.id.containerView,feedFragment);
        fragmentTranSection.isAddToBackStackAllowed();
        fragmentTranSection.addToBackStack(null);
        fragmentTranSection.commit();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                drawerLayout.closeDrawers();
                switch (menuItem.getItemId()) {
                    case R.id.feed:
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        FeedFragment feedFragment = new FeedFragment();
                        fragmentTransaction.replace(R.id.containerView, feedFragment);
                        fragmentTransaction.isAddToBackStackAllowed();
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        break;

                    case R.id.profile:
                        FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();
                        ProfileFragment profileFragment = new ProfileFragment();
                        fragmentTransaction1.replace(R.id.containerView,profileFragment);
                        fragmentTransaction1.isAddToBackStackAllowed();
                        fragmentTransaction1.addToBackStack(null);
                        fragmentTransaction1.commit();
                        break;

                }
                return false;
            }
        });

        android.support.v7.widget.Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.ic_icon_humano);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        toggle.syncState();
    }
}
