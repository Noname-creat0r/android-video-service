package com.example.videoservice.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.videoservice.R;

public class VideoCardListFragment extends Fragment {

    public VideoCardListFragment() {
    }

    public static VideoCardListFragment newInstance(String param1, String param2) {
        return new VideoCardListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video_card_list, container, false);
    }
}