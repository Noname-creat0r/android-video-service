package com.example.videoservice.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.videoservice.R;
import com.example.videoservice.slider.Slide;
import com.example.videoservice.slider.SliderPagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private List<Slide> slides;
    private ViewPager2 sliderPager;

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //sliderPager = getView().findViewById(R.id.sliderPager);

        slides = new ArrayList<>();
        slides.add(new Slide(R.drawable.test_img, "Nature calls part 1"));
        slides.add(new Slide(R.drawable.test_img, "Nature calls part 2"));

        //SliderPagerAdapter adapter = new SliderPagerAdapter(getContext(), slides);
        //sliderPager.setAdapter(adapter);
    }
}