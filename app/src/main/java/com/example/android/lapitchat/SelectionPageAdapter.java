package com.example.android.lapitchat;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SelectionPageAdapter extends FragmentPagerAdapter {


    public SelectionPageAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new RequestFragment();
            case 1:
                return new ChatsFragment();
            case 2:
                return new FriendsFragment();
                default:
                    return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "REQUESTS";
            case 1:
                return "CHATS";
            case 2:
                return "FRIENDS";
                default:
                    return null;
        }
    }
}
