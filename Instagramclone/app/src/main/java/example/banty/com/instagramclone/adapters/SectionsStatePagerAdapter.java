package example.banty.com.instagramclone.adapters;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
* To be used when number of fragments are large
* */
public class SectionsStatePagerAdapter extends FragmentStatePagerAdapter{

    private static final String TAG = "SectionsStatePagerAdapt";

    private final List<Fragment> mFragmentList = new ArrayList<>();

    private final HashMap<Fragment, Integer> mFragments = new HashMap<>();
    private final HashMap<String, Integer> mFragmentNumbers = new HashMap<>();
    private final HashMap<Integer, String> mFragmentNames = new HashMap<>();

    public SectionsStatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String fragmentName) {
        mFragmentList.add(fragment);
        mFragments.put(fragment,mFragmentList.size()-1);
        mFragmentNumbers.put(fragmentName,mFragmentList.size()-1);
        mFragmentNames.put(mFragmentList.size()-1,fragmentName);
    }


    @Nullable
    public String getFragmentNameFromNumber(int number) {
        if(mFragmentNames.containsKey(number)) {
            mFragmentNames.get(number);
        }
        return null;
    }

    @Nullable
    public Integer getFragmentNumberFromName(String fragmentName) {
        if(mFragmentNumbers.containsKey(fragmentName)){
            return mFragmentNumbers.get(fragmentName);
        }
        return null;
    }

    public int getFragmentNumber(Fragment fragment) {
        if(mFragments.containsKey(fragment)){
            return mFragmentNumbers.get(fragment);
        }
        return -1;
    }
}
