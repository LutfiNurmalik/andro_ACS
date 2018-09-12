package com.regulasiudara.aircargoshippingguidelines;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter{

    private int number_tabs;

    public PagerAdapter(FragmentManager fm, int number_tabs) {
        super(fm);
        this.number_tabs = number_tabs;
    }

    //Mengembalikan Fragment yang terkait dengan posisi tertentu
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Frag_Class1();
            case 1:
                return new Frag_Class2();
            case 2:
                return new Frag_Class3();
            case 3:
                return new Frag_Class4();
            case 4:
                return new Frag_Class5();
            case 5:
                return new Frag_Class6();
            case 6:
                return new Frag_Class7();
            case 7:
                return new Frag_Class8();
            case 8:
                return new Frag_Class9();
            case 9:
                return new Frag_Marking();
            default:
                return null;
        }
    }

    //Mengembalikan jumlah tampilan yang tersedia.
    @Override
    public int getCount() {
        return number_tabs;
    }
}