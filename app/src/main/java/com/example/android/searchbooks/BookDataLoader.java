package com.example.android.searchbooks;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by jiwanpokharel89 on 10/24/2017.
 */

public class BookDataLoader extends AsyncTaskLoader<List<BookData>> {

    private String url;

    public BookDataLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    public List<BookData> loadInBackground() {
        if(this.url == null)
            return null;
        return Utils.fetchBookData(url);
    }

    @Override
    public void onStartLoading(){
        forceLoad();
    }
}
