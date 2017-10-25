package com.example.android.searchbooks;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<BookData>> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //API: https://www.googleapis.com/books/v1/volumes?q=android
    }

    @Override
    public Loader<List<BookData>> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<BookData>> loader, List<BookData> data) {

    }

    @Override
    public void onLoaderReset(Loader<List<BookData>> loader) {

    }
}
