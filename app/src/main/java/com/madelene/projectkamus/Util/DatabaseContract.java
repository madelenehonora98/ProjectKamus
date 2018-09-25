package com.madelene.projectkamus.Util;

import android.provider.BaseColumns;

public class DatabaseContract {

    static String TABLE_KAMUS_ENG = "kamusEnglish";
    static String TABLE_KAMUS_IN = "kamusIndonesia";
    static final class KamusColumns implements BaseColumns {

        static final String KATAA = "kata";
        static final String DESKRIPSI = "deskripsi";
    }

    public static String getTableEng() {
        return TABLE_KAMUS_ENG;
    }

    public static String getTableInd() {
        return TABLE_KAMUS_IN;
    }
}