package com.syzible.loinniradminconsole.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.syzible.loinniradminconsole.R;
import com.syzible.loinniradminconsole.objects.CardItem;

import java.util.ArrayList;

/**
 * Created by ed on 11/09/2017.
 */

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.CardViewHolder> {

    private View view;
    private Context context;
    private ArrayList<CardItem> list;
    private Callback callback;

    public CardViewAdapter() {
    }

    public CardViewAdapter(ArrayList<CardItem> list) {
        this.list = list;
    }

    public CardViewAdapter(ArrayList<CardItem> list, Callback callback) {
        this.list = list;
        this.callback = callback;
    }

    @Override
    public CardViewAdapter.CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
        this.view = v;
        this.context = parent.getContext();
        return new CardViewAdapter.CardViewHolder(v);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(final CardViewAdapter.CardViewHolder holder, int position) {
        CardItem item = list.get(position);
        holder.title.setText(item.getTitle());
        holder.content.setText(item.getContent());
        holder.flag.setImageResource(item.getIcon());

        if (item.shouldDrawBackground())
            holder.flag.setBackground(context.getResources().getDrawable(R.drawable.rounded_corners_background));

        if (item.getSubContent() == null)
            holder.broadcastTime.setVisibility(View.GONE);
        else
            holder.broadcastTime.setText(item.getSubContent());

        if (item.getSubsubContent() == null)
            holder.userStats.setVisibility(View.GONE);
        else
            holder.userStats.setText(item.getSubsubContent());

        if (callback != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onCallback(holder.getAdapterPosition());
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public View getView() {
        return view;
    }

    class CardViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView title, content, broadcastTime, userStats;
        ImageView flag;

        CardViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.old_push_notification_card);
            title = (TextView) itemView.findViewById(R.id.card_title);
            content = (TextView) itemView.findViewById(R.id.card_content);

            broadcastTime = (TextView) itemView.findViewById(R.id.card_broadcast_time);
            userStats = (TextView) itemView.findViewById(R.id.card_user_stats);

            flag = (ImageView) itemView.findViewById(R.id.card_icon);
        }
    }
}
