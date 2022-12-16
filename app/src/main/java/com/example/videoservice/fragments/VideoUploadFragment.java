package com.example.videoservice.fragments;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.videoservice.R;
import com.example.videoservice.video.Video;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

public class VideoUploadFragment extends Fragment {

    private Uri videoUri;
    ProgressBar progressBar;

    StorageReference storageRef;
    DatabaseReference databaseRef;
    UploadTask uploadTask;

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

        EditText videoTitle = view.findViewById(R.id.editTextVideoTitle);
        EditText videoDescription = view.findViewById(R.id.editTextVideoDesc);
        Button uploadButton = view.findViewById(R.id.buttonUpload);
        progressBar = view.findViewById(R.id.progressBar);
        TextView chooseVideoEditText = view.findViewById(R.id.textViewChooseVideo);

        storageRef = FirebaseStorage.getInstance().getReference("videos");
        databaseRef = FirebaseDatabase
                .getInstance("https://android-vide-service-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("videos");

        chooseVideoEditText.setOnClickListener(v -> {
            chooseVideo(view);
        });

        uploadButton.setOnClickListener(v -> {
            uploadVideo(videoTitle.getText().toString(),
                    videoDescription.getText().toString(),
                    videoUri,
                    view);
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
                    videoUri = Uri.parse(data.getData().toString());
                    Toast.makeText(getContext(), "Video URI: " + videoUri.toString(),
                            Toast.LENGTH_LONG).show();

                }
            });


    public void chooseVideo(View view) {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        handleUpload.launch(intent);
    }

    private String getExt(Uri uri, View view){
        ContentResolver contentResolver = view.getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadVideo(String title, String description, Uri uri, View view){
        if (uri != null ||
                !TextUtils.isEmpty(title) ||
                !TextUtils.isEmpty(description)){
            progressBar.setVisibility(View.VISIBLE);
            final StorageReference ref = storageRef
                    .child(System.currentTimeMillis() + "." + getExt(uri, view));
            uploadTask = ref.putFile(uri);

            Task<Uri> urlTask = uploadTask.continueWithTask(task -> {
                if (!task.isSuccessful()){
                    throw Objects.requireNonNull(task.getException());
                }
                return ref.getDownloadUrl();
            })
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Uri downloadUrl = task.getResult();
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(view.getContext(), "Your video has been uploaded.",
                                Toast.LENGTH_SHORT).show();

                        Video video = new Video(title, downloadUrl.toString(), description);
                        databaseRef.push().setValue(video);
                    } else {
                        Toast.makeText(view.getContext(), "Failed to upload your video.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        } else {
            Toast.makeText(view.getContext(), "Fill in all the fields first!",
                    Toast.LENGTH_SHORT).show();
        }
    }
}