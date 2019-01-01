package com.trunghoang.flickrapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PhotoDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);

        activateToolbar(true);

        Intent intent = getIntent();
        Photo photo = (Photo) intent.getSerializableExtra(PHOTO_TRANSFER);

        ImageView imageView = findViewById(R.id.photo_view);
        Picasso.get().load(photo.getLink()).into(imageView);
        TextView photoTitle = findViewById(R.id.photo_title);
        photoTitle.setText(photo.getTitle());
        TextView photoAuthor = findViewById(R.id.photo_author);
        photoAuthor.setText(photo.getAuthor());
        TextView photoTags = findViewById(R.id.photo_tags);
        photoTags.setText(photo.getTags());
    }
}
