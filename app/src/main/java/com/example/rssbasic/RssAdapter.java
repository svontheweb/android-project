package com.example.rssbasic;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class RssAdapter extends RecyclerView.Adapter<RssAdapter.RssViewHolder> {

    private List<RssItem> rssItems;
    private Context context;

    public RssAdapter(List<RssItem> rssItems, Context context) {
        this.rssItems = rssItems;
        this.context = context;
    }

    @NonNull
    @Override
    public RssViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rss_item, parent, false);
        return new RssViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RssViewHolder holder, int position) {
        RssItem rssItem = rssItems.get(position);
        holder.titleTextView.setText(rssItem.getTitle());
        holder.descriptionTextView.setText(rssItem.getDescription());
        if (rssItem.getImageUrl() != null && !rssItem.getImageUrl().isEmpty()) {
            holder.imageView.setVisibility(View.VISIBLE);
            Picasso.get().load(rssItem.getImageUrl()).into(holder.imageView);
        } else {
            holder.imageView.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(v -> {
            Uri uri = Uri.parse(rssItem.getLink());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return rssItems.size();
    }

    public static class RssViewHolder extends RecyclerView.ViewHolder {

        public TextView titleTextView;
        public TextView descriptionTextView;
        public ImageView imageView;

        public RssViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}

