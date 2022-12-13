package com.example.videoservice.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.videoservice.R;
import com.example.videoservice.slider.Slide;
import java.util.ArrayList;

public class VideoUploadFragment extends Fragment {

    private static final String PICK_VIDEO = "PICK_VIDEO";

    Button uploadButton;
    ProgressBar progressBar;
    TextView chooseVideoEditText;
    private Uri videoUri;
    MediaController mediaController;

    public VideoUploadFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_upload, container, false);
        uploadButton = view.findViewById(R.id.buttonUpload);
        progressBar = view.findViewById(R.id.progressBar);
        chooseVideoEditText = view.findViewById(R.id.textViewChooseVideo);
        mediaController = new MediaController(view.getContext());


        chooseVideoEditText.setOnClickListener(v -> {
            chooseVideo(view);
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    ActivityResultLauncher<Intent> handleUpload = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    Toast.makeText(getContext(), "Vide URI: " + data.toString(),
                            Toast.LENGTH_LONG).show();

                }
            });


    public void chooseVideo(View view) {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        handleUpload.launch(intent);
    }
}