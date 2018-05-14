package com.karla00058615.restaurants.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.karla00058615.restaurants.R;
import com.karla00058615.restaurants.adapters.RestaurantsAdapter;
import com.karla00058615.restaurants.models.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantListFragment extends Fragment {

    RecyclerView recyclerView;
    RestaurantsAdapter restaurantsAdapter;
    List<Restaurant> restaurantList;
    onRestaurantSelected mCallBack;

    public RestaurantListFragment() {
    }

    public interface onRestaurantSelected{
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallBack = (onRestaurantSelected) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement onRestaurantSelectedListener");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null){
            restaurantList = savedInstanceState.getParcelableArrayList("restaurantList");
            restaurantsAdapter = savedInstanceState.getParcelable("adapter");
            restaurantsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null){
            restaurantList = savedInstanceState.getParcelableArrayList("restaurantList");
            restaurantsAdapter = savedInstanceState.getParcelable("adapter");
            restaurantsAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onSaveInstanceState(@Nullable Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList("restaurantList", (ArrayList<? extends Parcelable>) restaurantList);
        outState.putParcelable("adapter", restaurantsAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_restaurant_list, container, false);

        //setting up restaurant adapter
        if (savedInstanceState != null){
            restaurantsAdapter = savedInstanceState.getParcelable("adapter");
        }else{
            restaurantsAdapter = new RestaurantsAdapter(getContext(), restaurantList);
        }

        //setting up recyclerview
        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(restaurantsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));

        return v;
    }

    public void setList(List<Restaurant> l){
        this.restaurantList = l;
    }

    public void updateList(List<Restaurant> l){
        if (restaurantsAdapter != null)
            restaurantsAdapter.updateList(l);
    }
}
