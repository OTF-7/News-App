package com.omartaha.mocknewsapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> implements Filterable {
    private Context context;
    private ArrayAdapter<String> adapter;
    private List<News> newsList = new ArrayList<>();
    private List<News> newsList_for_search = new ArrayList<>();
    private Filter NewsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<News> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(newsList_for_search);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (News news : newsList_for_search) {
                    if (news.getNewsSection().toLowerCase().contains(filterPattern)) {
                        filteredList.add(news);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            newsList.clear();
            newsList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public NewsAdapter(Context context, ArrayList<News> newsList) {
        this.context = context;
        this.newsList = newsList;
        this.newsList_for_search = newsList;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_list_item, parent,
                false);
        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {

        GradientDrawable type_shape = (GradientDrawable) holder.newsType.getBackground();
        int type_color = getTypeColor(newsList.get(position).getNewsType());
        type_shape.setColor(type_color);
        holder.newsType.setText(newsList.get(position).getNewsType());

        holder.newsTitle.setText(newsList.get(position).getNewsTitle());
        holder.newsTitle.setSelected(true);
        holder.newsSection.setText(newsList.get(position).getNewsSection());
        String s = newsList.get(position).getNewsDateTime();
        int index = s.indexOf("T");
        holder.newsDate.setText(s.substring(0, index));
        holder.newsTime.setText(s.substring(index + 1, index + 6));


        if (newsList.get(position).getAuthors().size() > 0) {
            holder.author_label.setVisibility(View.VISIBLE);

            adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, newsList
                    .get(position).getAuthors());

            holder.authorsListView.setAdapter(adapter);
        } else {
            holder.author_label.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                News news = newsList.get(position);

                Uri newsUri = Uri.parse(news.getNewsUrl());

                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                context.startActivity(websiteIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
        notifyDataSetChanged();
    }

    public void clearNewsList() {
        this.newsList.clear();
    }

    private int getTypeColor(String type) {
        int typeColorResourceId;
        if (type.equals(context.getString(R.string.live_blog))) {
            typeColorResourceId = R.color.red;
        } else if (type.equals(context.getString(R.string.article))) {
            typeColorResourceId = R.color.blue;
        } else {
            typeColorResourceId = R.color.color_primary;
        }

        return ContextCompat.getColor(context, typeColorResourceId);
    }

    @Override
    public Filter getFilter() {
        return NewsFilter;
    }

    public static class NewsHolder extends RecyclerView.ViewHolder {
        TextView newsType, newsTitle, newsSection, newsDate, newsTime, author_label;
        ListView authorsListView;

        public NewsHolder(@NonNull View itemView) {
            super(itemView);
            newsType = itemView.findViewById(R.id.news_type);
            newsTitle = itemView.findViewById(R.id.news_title);
            newsTime = itemView.findViewById(R.id.news_time);
            newsSection = itemView.findViewById(R.id.news_section);
            newsDate = itemView.findViewById(R.id.news_date);
            authorsListView = itemView.findViewById(R.id.authors_list);
            author_label = itemView.findViewById(R.id.author_label);
        }
    }
}
