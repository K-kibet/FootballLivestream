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
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CompetitionsAdapter extends RecyclerView.Adapter<CompetitionsAdapter.ViewHolder> {
    private  boolean bool = true;
    private final Context context;
    private final List<Competition> competitionList;
    private InterstitialAd mInterstitialAd;

    public CompetitionsAdapter(Context context, List<Competition> list) {
        this.context = context;
        this.competitionList = list;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_competition, parent, false);
        if(this.bool) {
            MobileAds.initialize(this.context);
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
                            CompetitionsAdapter.this.mInterstitialAd = interstitialAd;
                        }
                    });
            this.bool = false;
        }
        return new ViewHolder(view);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        final Competition competition = this.competitionList.get(position);
        holder.competitionName.setText(competition.getCompetitionName());
        holder.competitionArena.setText(competition.getCompetitionArena());
        Picasso.get().load(competition.getCompetitionImage()).placeholder(R.drawable.ic_epl_banner).error(R.drawable.ic_epl_banner).into(holder.competitionImage);
        holder.itemView.setOnClickListener(view -> {
            if(CompetitionsAdapter.this.mInterstitialAd != null) {
                CompetitionsAdapter.this.mInterstitialAd.show((Activity) CompetitionsAdapter.this.context);
            }
            Intent intent = new Intent(CompetitionsAdapter.this.context, LivestreamActivity.class);
            CompetitionsAdapter.this.context.startActivity(intent);
        });

    }

    public int getItemCount() {
        return this.competitionList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView competitionName;
        private final TextView competitionArena;
        private final ImageView competitionImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.competitionName = itemView.findViewById(R.id.competitionName);
            this.competitionArena = itemView.findViewById(R.id.competitionArena);
            this.competitionImage = itemView.findViewById(R.id.competitionImage);
        }
    }
}