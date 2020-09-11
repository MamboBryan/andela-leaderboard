package com.andelaexample.leaderboard.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.andelaexample.leaderboard.ui.TemplateFragment;
import com.andelaexample.leaderboard.utils.ConstantsUtils;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        TemplateFragment fragment = null;
        switch (position) {
            case 0:
                fragment = TemplateFragment.newInstance(ConstantsUtils.CODE_LEARNER);
                break;
            case 1:
                fragment = TemplateFragment.newInstance(ConstantsUtils.CODE_SKILL_LEADER);
                break;
            default:
                break;
        }
        assert fragment != null;
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        switch (position) {
            case 0:
                title = "Learning Leaders";
                break;
            case 1:
                title = "Skill IQ Leaders";
                break;
            default:
                break;
        }
        return title;
    }
}
