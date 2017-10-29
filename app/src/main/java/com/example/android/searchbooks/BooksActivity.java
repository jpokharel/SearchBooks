package com.example.android.searchbooks;

import android.app.LoaderManager;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BooksActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<BookData>> {

    private static String BASE_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    BookDataAdapter bookDataAdapter;
    private TextView emptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        Bundle bundle = getIntent().getExtras();
        BASE_URL += bundle.getString("url_path");
        Log.i("URL_PATH: ", bundle.getString("url_path"));

        //To check for network connectivity
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext()
                .getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        bookDataAdapter = new BookDataAdapter(this, new ArrayList<BookData>());
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(bookDataAdapter);
        emptyTextView = (TextView) findViewById(R.id.empty_view);
        listView.setEmptyView(emptyTextView);

        if (networkInfo != null && networkInfo.isConnectedOrConnecting())
            getLoaderManager().initLoader(1, null, this);
        else {
            View progressBar = findViewById(R.id.progress_bar);
            progressBar.setVisibility(View.GONE);
            emptyTextView.setText("No Internet Connection!");
        }
    }

    @Override
    public Loader<List<BookData>> onCreateLoader(int id, Bundle args) {
        return new BookDataLoader(this, BASE_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<BookData>> loader, List<BookData> data) {
        View progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        emptyTextView.setText("No such book.");
        bookDataAdapter.clear();
        if (data != null && !data.isEmpty())
            bookDataAdapter.addAll(data);
    }

    @Override
    public void onLoaderReset(Loader<List<BookData>> loader) {
        bookDataAdapter.clear();
    }
}
