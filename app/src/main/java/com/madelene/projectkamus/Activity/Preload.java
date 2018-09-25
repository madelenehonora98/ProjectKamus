package com.madelene.projectkamus.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.madelene.projectkamus.Entity.Kamus;
import com.madelene.projectkamus.R;
import com.madelene.projectkamus.Util.DatabaseContract;
import com.madelene.projectkamus.Util.KamusHelper;
import com.madelene.projectkamus.Util.KamusPreference;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Preload extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Object> {

    private static final int LOADER_ID = 1;
    private KamusHelper kamusHelper;
    private KamusPreference kamusPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preload);
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @NonNull
    @Override
    public Loader onCreateLoader(int id, @Nullable Bundle args) {
        return new KamusLoader(this, getDictionaryHelper(), getKamusPreference());
    }

    @Override
    public void onLoadFinished(@NonNull Loader loader, Object data) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {

    }

    public KamusHelper getDictionaryHelper() {
        if (kamusHelper == null) {
            kamusHelper = new KamusHelper(this);
        }
        return kamusHelper;
    }

    public KamusPreference getKamusPreference() {
        if (kamusPreference == null) {
            kamusPreference = new KamusPreference(this);
        }
        return kamusPreference;
    }

    private static class KamusLoader extends AsyncTaskLoader<Void> {

        private KamusHelper kamusHelper;
        private KamusPreference kamusPreference;

        public KamusLoader(@NonNull Context context, KamusHelper kamusHelper, KamusPreference kamusPreference) {
            super(context);
            this.kamusHelper = kamusHelper;
            this.kamusPreference = kamusPreference;
        }

        @Nullable
        @Override
        public Void loadInBackground() {
            kamusHelper.open();
            ArrayList<Kamus> engDictionary = loadDictionaryDataFromFile("english_indonesia");
            ArrayList<Kamus> indDictionary = loadDictionaryDataFromFile("indonesia_english");
            System.out.println(engDictionary);
            System.out.println(indDictionary);
            if (!kamusPreference.isPreloadDataAvailable()) {
                kamusHelper.insertBulk(true, engDictionary);
                kamusHelper.insertBulk(false, indDictionary);
                kamusPreference.setPreloadDataSuccess();
            }
            kamusPreference.setKamus(DatabaseContract.getTableEng(), engDictionary);
            kamusPreference.setKamus(DatabaseContract.getTableInd(), indDictionary);
            kamusHelper.close();
            return null;
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            forceLoad();
        }

        private ArrayList<Kamus> loadDictionaryDataFromFile(String fileName) {
            ArrayList<Kamus> kamuss = new ArrayList<Kamus>();
            BufferedReader reader;
            try {
                InputStream inputStream = getContext().getAssets().open(fileName);
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] splitString = line.split("\t");
                    Kamus kamus= new Kamus();
                    kamus.setKata(splitString[0]);
                    kamus.setDeskripsi(splitString[1]);
                    kamuss.add(kamus);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return kamuss;
        }
    }
}
