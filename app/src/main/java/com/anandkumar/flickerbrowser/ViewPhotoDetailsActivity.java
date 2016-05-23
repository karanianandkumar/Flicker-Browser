package com.anandkumar.flickerbrowser;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ViewPhotoDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_details);
        activateToolbarWithHomeEnabled();

        Intent intent=getIntent();
        Photo photo=(Photo)intent.getSerializableExtra(PHOTO_TRANSFER);

        TextView photTitle=(TextView)findViewById(R.id.photo_title);
        photTitle.setText("Title : "+ photo.getmTitle());

        TextView photoTags=(TextView)findViewById(R.id.photo_tags);
        photoTags.setText("Tags: "+photo.getmTags());

        TextView photoAuthor=(TextView)findViewById(R.id.photo_author);
        photoAuthor.setText("Author: "+photo.getmAuthor());

        ImageView photoImage=(ImageView)findViewById(R.id.photo_image);


        Picasso.with(this).load(photo.getmLink())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(photoImage);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
