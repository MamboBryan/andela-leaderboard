package com.andelaexample.leaderboard.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.andelaexample.leaderboard.data.models.SkillLearner;
import com.andelaexample.leaderboard.databinding.ItemSkillIqRecyclerBinding;

import java.util.List;

public class SkillerAdapter extends RecyclerView.Adapter<SkillerAdapter.SkillLearnerViewHolder> {

    private List<SkillLearner> learners;

    public SkillerAdapter(List<SkillLearner> learners) {
        this.learners = learners;
    }

    @NonNull
    @Override
    public SkillLearnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemSkillIqRecyclerBinding itemBinding = ItemSkillIqRecyclerBinding.inflate(layoutInflater, parent, false);
        return new SkillLearnerViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SkillLearnerViewHolder holder, int position) {
        SkillLearner learner = learners.get(position);
        holder.bind(learner);
    }

    @Override
    public int getItemCount() {
        return learners != null ? learners.size() : 0;
    }

    class SkillLearnerViewHolder extends RecyclerView.ViewHolder {
        private ItemSkillIqRecyclerBinding binding;

        public SkillLearnerViewHolder(ItemSkillIqRecyclerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        /**
         * We will use this function to bind instance of Workout
         */
        public void bind(SkillLearner skillLearner) {
            binding.setMSkiller(skillLearner);
            binding.executePendingBindings();
        }
    }

}
