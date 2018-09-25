package com.madelene.projectkamus.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.madelene.projectkamus.Entity.Kamus;
import com.madelene.projectkamus.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class detailKata extends AppCompatActivity {

    @BindView(R.id.tvKata)
    TextView txtKata;

    @BindView(R.id.tvDeskripsi)
    TextView txtDeskripsi;

    public static Kamus EXTRA_KAMUS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kata);

        ButterKnife.bind(this);

        Kamus kamus = getIntent().getParcelableExtra("EXTRA_KAMUS");
        txtKata.setText(kamus.getKata());
        txtDeskripsi.setText(kamus.getDeskripsi());
    }
}
