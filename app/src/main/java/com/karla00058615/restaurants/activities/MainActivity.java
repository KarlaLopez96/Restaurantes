package com.karla00058615.restaurants.activities;

import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.karla00058615.restaurants.R;
import com.karla00058615.restaurants.adapters.RestaurantPagerAdapter;
import com.karla00058615.restaurants.fragments.RestaurantListFragment;
import com.karla00058615.restaurants.models.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RestaurantListFragment.onRestaurantSelected,
        NavigationView.OnNavigationItemSelectedListener{

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    RestaurantPagerAdapter pagerAdapter;
    List<Restaurant> restaurantList;
    RestaurantListFragment restaurantFragment, favoriteFragment;
    DrawerLayout drawerLayout;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList("restaurantList", (ArrayList<? extends Parcelable>) restaurantList);

        //saving fragments instance
        if (restaurantFragment.isAdded())
            getSupportFragmentManager().putFragment(outState, "restaurantFragment", restaurantFragment);

        if (favoriteFragment.isAdded())
            getSupportFragmentManager().putFragment(outState, "favoriteFragment", favoriteFragment);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null || !savedInstanceState.containsKey("instance")){
            //setting up initial list
            restaurantList = fillRestaurants1();

            //creating fragments
            restaurantFragment = new RestaurantListFragment();
            favoriteFragment = new RestaurantListFragment();

            //configuring fragments
            restaurantFragment.setList(restaurantList);
            favoriteFragment.setList(favRestaurants(restaurantList));
        } else {
            restaurantList = savedInstanceState.getParcelableArrayList("instance");
            restaurantFragment = (RestaurantListFragment) getSupportFragmentManager().getFragment(savedInstanceState, "restaurantFragment");
            favoriteFragment = (RestaurantListFragment) getSupportFragmentManager().getFragment(savedInstanceState, "favoriteFragment");
        }

        //setting up the Toolbar
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        //setting up the Drawer
        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_header);
        navigationView.setNavigationItemSelectedListener(this);

        //setting up the toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //setting up the PagerAdapter
        pagerAdapter = new RestaurantPagerAdapter(getSupportFragmentManager(), this);
        pagerAdapter.addItem("Menu", restaurantFragment);
        pagerAdapter.addItem("Favorites", favoriteFragment);

        //setting up the Viewpager
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                List<Restaurant> newList = position == 0 ? restaurantList : favRestaurants(restaurantList);
                RestaurantListFragment listFragment = ((RestaurantListFragment)pagerAdapter.getItem(position));
                if (listFragment != null)
                    listFragment.updateList(newList);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //setting up the Tablayout
        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(viewPager, true);
        if(savedInstanceState == null){
            navigationView.setCheckedItem(R.id.resturant1);
        }
    }

    private ArrayList<Restaurant> fillRestaurants1(){
        ArrayList<Restaurant> l = new ArrayList<>();
        l.add(new Restaurant(1, "Pizza Grande", 3, false));
        l.add(new Restaurant(1, "Pizza pequeña", 4, false));
        l.add(new Restaurant(1, "Ensalada", 3, false));
        l.add(new Restaurant(1, "Lasaña", 2, false));

        return l;
    }

    private ArrayList<Restaurant> fillRestaurants2(){
        ArrayList<Restaurant> l = new ArrayList<>();
        l.add(new Restaurant(1, "Carne", 3, false));
        l.add(new Restaurant(1, "Costillas", 4, false));
        l.add(new Restaurant(1, "Hamburguesa", 3, false));
        l.add(new Restaurant(1, "Alitas", 2, false));

        return l;
    }

    private ArrayList<Restaurant> favRestaurants(List<Restaurant> restaurants){
        ArrayList<Restaurant> favs = new ArrayList<>();

        for (Restaurant restaurant : restaurants){
            if (restaurant.isFavorite()) favs.add(restaurant);
        }

        return favs;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.resturant1:
                restaurantList = fillRestaurants1();

                restaurantFragment.setList(restaurantList);
                favoriteFragment.setList(favRestaurants(restaurantList));

                pagerAdapter = new RestaurantPagerAdapter(getSupportFragmentManager(), this);
                pagerAdapter.addItem("Restaurants", restaurantFragment);
                pagerAdapter.addItem("Favorites", favoriteFragment);

                viewPager.setAdapter(pagerAdapter);
                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        List<Restaurant> newList = position == 0 ? restaurantList : favRestaurants(restaurantList);
                        RestaurantListFragment listFragment = ((RestaurantListFragment)pagerAdapter.getItem(position));
                        if (listFragment != null)
                            listFragment.updateList(newList);
                    }

                    @Override
                    public void onPageSelected(int position) {

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
                break;
            case R.id.restaurant2:
                restaurantList = fillRestaurants2();

                restaurantFragment.setList(restaurantList);
                favoriteFragment.setList(favRestaurants(restaurantList));

                pagerAdapter = new RestaurantPagerAdapter(getSupportFragmentManager(), this);
                pagerAdapter.addItem("Restaurants", restaurantFragment);
                pagerAdapter.addItem("Favorites", favoriteFragment);

                viewPager.setAdapter(pagerAdapter);
                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        List<Restaurant> newList = position == 0 ? restaurantList : favRestaurants(restaurantList);
                        RestaurantListFragment listFragment = ((RestaurantListFragment)pagerAdapter.getItem(position));
                        if (listFragment != null)
                            listFragment.updateList(newList);
                    }

                    @Override
                    public void onPageSelected(int position) {

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
                break;
            case R.id.restaurant3:
                Intent intent = new Intent(this,RestaurantInfo.class);
                startActivity(intent);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
