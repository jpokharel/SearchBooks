package com.example.android.searchbooks;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jiwanpokharel89 on 10/24/2017.
 */

public class BookDataAdapter  extends ArrayAdapter{

    public BookDataAdapter(@NonNull Context context, @NonNull List objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.book_list,parent,false);

        BookData bookData = (BookData) getItem(position);

        TextView bookTitle = (TextView) convertView.findViewById(R.id.book_title);
        bookTitle.setText(bookData.getTitle());
        TextView authorName = (TextView) convertView.findViewById(R.id.author_name);
        authorName.setText(bookData.getAuthor());
        TextView publishedDate = (TextView) convertView.findViewById(R.id.published_date);
        publishedDate.setText(bookData.getPublishedDate());
        TextView printType = (TextView) convertView.findViewById(R.id.print_type);
        printType.setText(bookData.getPrintType());


        return convertView;
    }
}
