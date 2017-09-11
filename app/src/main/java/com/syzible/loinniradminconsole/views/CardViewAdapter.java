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
        this(list, null);
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
        holder.icon.setImageResource(item.getIcon());

        if (item.shouldDrawBackground())
            holder.icon.setBackground(context.getResources().getDrawable(R.drawable.rounded_corners_background));

        if (item.getSubContent() == null)
            holder.subcontent.setVisibility(View.GONE);
        else
            holder.subcontent.setText(item.getSubContent());

        if (item.getSubsubContent() == null)
            holder.subsubcontent.setVisibility(View.GONE);
        else
            holder.subsubcontent.setText(item.getSubsubContent());

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
        TextView title, content, subcontent, subsubcontent;
        ImageView icon;

        CardViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.old_push_notification_card);
            title = (TextView) itemView.findViewById(R.id.card_title);
            content = (TextView) itemView.findViewById(R.id.card_content);
            subcontent = (TextView) itemView.findViewById(R.id.card_sub_content);
            subsubcontent = (TextView) itemView.findViewById(R.id.card_sub_sub_content);
            icon = (ImageView) itemView.findViewById(R.id.card_icon);
        }
    }
}
