package com.github.w_kamil.movieapp.Detail;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.w_kamil.movieapp.Detail.gallery.GalleryActivity;
import com.github.w_kamil.movieapp.R;
import com.github.w_kamil.movieapp.RetrfofitProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusAppCompatActivity;

@RequiresPresenter(DetailPresenter.class)
public class DetailActivity extends NucleusAppCompatActivity<DetailPresenter> {

    private static final String ID_KEY = "id_key";
    private Disposable subscribe;

    @BindView(R.id.poster_view)
    ImageView poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        String imdbID = getIntent().getStringExtra(ID_KEY);
        RetrfofitProvider retrfofitProvider = (RetrfofitProvider) getApplication();
        getPresenter().setRetrofit(retrfofitProvider.provideRetrofit());


        subscribe = getPresenter().loadDetail(imdbID).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this::success, this::error);


    }

    private void success(MovieItem movieItem) {
        Glide.with(this).load(movieItem.getPoster()).into(poster);
        poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GalleryActivity.startActivity(DetailActivity.this, movieItem.getPoster(), poster);
            }
        });
    }

    private void error(Throwable throwable) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscribe != null) {
            subscribe.dispose();
        }
    }

    public static Intent createIntent(Context context, String imdbID) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(ID_KEY, imdbID);
        return intent;
    }
}
