package com.example.android.searchbooks;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView infoTextView = (TextView) findViewById(R.id.info_text_view);
        final EditText searchTextView = (EditText) findViewById(R.id.search_text_view);
        Button searchButton = (Button) findViewById(R.id.search_button);

        //To check for network connectivity
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext()
                .getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String searchText = String.valueOf(searchTextView.getText());
                    Intent intent = new Intent(MainActivity.this, BooksActivity.class);
                    intent.putExtra("url_path", searchText);
                    startActivity(intent);
                }
            });
        } else {
            searchTextView.setVisibility(View.GONE);
            searchButton.setVisibility(View.GONE);
            infoTextView.setText("No Internet Connection!");
        }
    }
}
