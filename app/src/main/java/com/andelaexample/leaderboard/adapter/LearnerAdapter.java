package com.andelaexample.leaderboard.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andelaexample.leaderboard.data.models.Learner;
import com.andelaexample.leaderboard.databinding.ItemLearnerRecyclerBinding;

import java.util.List;

public class LearnerAdapter extends RecyclerView.Adapter<LearnerAdapter.LearnerViewHolder> {
    private List<Learner> learners;

    public LearnerAdapter(List<Learner> learners) {
        this.learners = learners;
    }

    @NonNull
    @Override
    public LearnerAdapter.LearnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemLearnerRecyclerBinding itemBinding = ItemLearnerRecyclerBinding.inflate(layoutInflater, parent, false);
        return new LearnerAdapter.LearnerViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull LearnerAdapter.LearnerViewHolder holder, int position) {
        Learner learner = learners.get(position);
        holder.bind(learner);
    }

    @Override
    public int getItemCount() {
        return learners != null ? learners.size() : 0;
    }

    class LearnerViewHolder extends RecyclerView.ViewHolder {
        private ItemLearnerRecyclerBinding binding;

        public LearnerViewHolder(ItemLearnerRecyclerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        /**
         * We will use this function to bind instance of Workout
         */
        public void bind(Learner learner) {
            binding.setMLearner(learner);
            binding.executePendingBindings();
        }
    }
}
