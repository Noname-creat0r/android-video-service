package com.example.videoservice.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.videoservice.R;

public class VideoCardFragment extends Fragment {

    public VideoCardFragment() {
    }

    public static VideoCardFragment newInstance(String param1, String param2) {

        return new VideoCardFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video_card, container, false);
    }
}