package com.example.videoservice.video;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.videoservice.R;

import java.net.URI;
import java.util.ArrayList;

public class VideoCardRecyclerViewAdapter
        extends RecyclerView.Adapter<VideoCardRecyclerViewAdapter.ViewHolder> {

    ArrayList<Video> videos;

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView videoCardTitle;
        VideoView videoCardPlayer;
        ImageView videoCardUserIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.videoCardTitle = itemView.findViewById(R.id.videoTitleCard);
            this.videoCardPlayer = itemView.findViewById(R.id.videoViewCard);
            this.videoCardUserIcon = itemView.findViewById(R.id.userIconCard);
        }
    }

    public VideoCardRecyclerViewAdapter(ArrayList<Video> dataSet){
        this.videos = dataSet;
    }

    public ArrayList<Video> fetchVideos() {

        return new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                      int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_video_card_list, parent, false);

        // Listeners

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VideoCardRecyclerViewAdapter.ViewHolder holder,
                                 int position) {

        TextView videoCardTitle = holder.videoCardTitle;
        VideoView videoCardPlayer = holder.videoCardPlayer;
        ImageView videoCardUserIcon = holder.videoCardUserIcon;

        videoCardTitle.setText(videos.get(position).getName());
        videoCardPlayer.setVideoURI(Uri.parse(videos.get(position).getUrl()));
        videoCardUserIcon.setImageResource(R.drawable.user); // for now
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }
}
