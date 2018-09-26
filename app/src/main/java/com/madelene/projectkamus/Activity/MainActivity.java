package com.madelene.projectkamus.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.madelene.projectkamus.Adapter.KamusAdapter;
import com.madelene.projectkamus.Entity.Kamus;
import com.madelene.projectkamus.R;
import com.madelene.projectkamus.Util.DatabaseContract;
import com.madelene.projectkamus.Util.KamusHelper;
import com.madelene.projectkamus.Util.KamusPreference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, KamusAdapter.ItemClickListener {

    public boolean isEng;

    @BindView(R.id.etSearch)
    TextView txtSearch;
    @BindView(R.id.rvKata)
    RecyclerView rvKata;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        rvKata.setLayoutManager(new LinearLayoutManager(this));

        rvKata.setAdapter(getKamusAdapter());

        changeAdapterData(true, null);
        isEng = true;
        toolbar.setTitle("English - Indonesia");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.en_in) {
            isEng = true;

            toolbar.setTitle("English - Indonesia");

        } else if (id == R.id.in_en) {
            isEng = false;
            toolbar.setTitle("Indonesia - English");

        }
        changeAdapterData(isEng,null);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private KamusAdapter kamusAdapter;
    public KamusAdapter getKamusAdapter() {
        if (kamusAdapter == null) {
            kamusAdapter = new KamusAdapter();
            kamusAdapter.setItemClickListener(this);
        }
        return kamusAdapter;
    }

    private KamusHelper kamusHelper;
    public KamusHelper getKamusHelper() {
        if (kamusHelper == null) {
            kamusHelper = new KamusHelper(this);
        }
        return kamusHelper;
    }

    private KamusPreference kamusPreference;
    public KamusPreference getKamusPreference() {
        if (kamusPreference == null) {
            kamusPreference = new KamusPreference(this);
        }
        return kamusPreference;
    }

    @OnTextChanged(value = R.id.etSearch, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void searchTextChange() {

        if (txtSearch.getText().toString().trim().isEmpty()) {
            changeAdapterData(isEng, null);
        } else {
            changeAdapterData(isEng, txtSearch.getText().toString().trim().toLowerCase());
        }
    }

    private void changeAdapterData(boolean isEng, String kataCari) {
        String tabel = null;
        if(isEng){
            tabel = DatabaseContract.getTableEng();

        }else{
            tabel = DatabaseContract.getTableInd();

        }

        if (kataCari == null || kataCari.trim().isEmpty()) {
            getKamusAdapter().setListKamuss(getKamusPreference().getKamus(tabel));
        } else {
            getKamusHelper().open();
            getKamusAdapter().setListKamuss(getKamusHelper().query(isEng, kataCari));
            getKamusHelper().close();
        }

    }

    @Override
    public void onItemClicked(Kamus kamus) {
        Intent intent = new Intent(this, detailKata.class);
        intent.putExtra("EXTRA_KAMUS", kamus);
        startActivity(intent);
    }
}
