package com.kibet.footballlivestream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

public class FixturesAdapter extends RecyclerView.Adapter<FixturesAdapter.ViewHolder> {
    private  boolean bool = true;
    private final Context context;
    private final List<Match> matchList;
    private InterstitialAd mInterstitialAd;
    public FixturesAdapter(Context context, List<Match> list) {
        this.context = context;
        this.matchList = list;
    }
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fixture, parent, false);
        if(this.bool) {
            AdRequest adRequest = new AdRequest.Builder().build();
            Context context = this.context;
            InterstitialAd.load(context, context.getString(R.string.Interstitial_Ad_Unit), adRequest,
                    new InterstitialAdLoadCallback() {
                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            super.onAdFailedToLoad(loadAdError);
                            mInterstitialAd = null;
                        }
                        @Override
                        public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                            super.onAdLoaded(interstitialAd);
                            FixturesAdapter.this.mInterstitialAd = interstitialAd;
                        }
                    });
            this.bool = false;
        }
        return new ViewHolder(view);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        final Match match = this.matchList.get(position);
        holder.textHome.setText(match.getHomeTeam());
        holder.textAway.setText(match.getAwayTeam());
        holder.textResults.setText(match.getScore());
        holder.duration.setText(match.getDate().split("T")[1].split(":00Z")[0]);
        if(!Objects.equals(match.getScore(), "?-?")) {
            holder.duration.setText("FT");
        }
        Picasso.get().load(match.getCupImage()).placeholder(R.drawable.ic_football).error(R.drawable.ic_football).into(holder.imageCup);
        holder.itemView.setOnClickListener(view -> {
            if(FixturesAdapter.this.mInterstitialAd != null) {
                FixturesAdapter.this.mInterstitialAd.show((Activity) FixturesAdapter.this.context);
            }
            Intent intent = new Intent(FixturesAdapter.this.context, LivestreamActivity.class);
            FixturesAdapter.this.context.startActivity(intent);
        });

    }

    public int getItemCount() {
        return this.matchList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textHome;
        private final TextView textAway;
        private final TextView textResults;
        private final TextView duration;
        private final ImageView imageCup;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textHome = itemView.findViewById(R.id.textHome);
            this.textAway = itemView.findViewById(R.id.textAway);
            this.textResults = itemView.findViewById(R.id.textResults);
            this.imageCup = itemView.findViewById(R.id.imageCup);
            this.duration = itemView.findViewById(R.id.duration);
        }
    }
}
