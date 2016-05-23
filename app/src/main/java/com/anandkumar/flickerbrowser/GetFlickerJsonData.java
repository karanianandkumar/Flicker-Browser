package com.anandkumar.flickerbrowser;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anand on 5/6/2016.
 */
public class GetFlickerJsonData extends GetRawData {
    private String LOG_TAG = GetFlickerJsonData.class.getSimpleName();
    private List<Photo> mPhotos;
    private Uri mDestinationUri;

    public GetFlickerJsonData(String searchCriteria, boolean matchAll) {
        super(null);
        createAndUpdateUri(searchCriteria, matchAll);
        mPhotos = new ArrayList<Photo>();
    }

    public void execute() {
        this.setmRawUrl(mDestinationUri.toString());
        DownloadJsonData jsonData = new DownloadJsonData();
        Log.v(LOG_TAG, "Build URI = " + mDestinationUri.toString());
        jsonData.execute(getmRawUrl());
    }

    private boolean createAndUpdateUri(String searchCriteria, boolean matchAll) {
        final String FICKER_API_BASE_URL = "https://api.flickr.com/services/feeds/photos_public.gne";
        final String TAGS_PARM = "tags";
        final String TAGMODE_PARM = "tagmode";
        final String FORMAT_PARM = "format";
        final String NO_JSON_CALLBACK_PARM = "nojsoncallback";

        mDestinationUri = Uri.parse(FICKER_API_BASE_URL).buildUpon()
                .appendQueryParameter(TAGMODE_PARM, searchCriteria)
                .appendQueryParameter(TAGMODE_PARM, matchAll ? "ALL" : "ANY")
                .appendQueryParameter(FORMAT_PARM, "json")
                .appendQueryParameter(NO_JSON_CALLBACK_PARM, "1")
                .build();

        return mDestinationUri != null;
    }

    public List<Photo> getmPhotos() {
        return mPhotos;
    }


    private void processResult() {

        if (getmDownloadStatus() != DownloadStatus.OK) {
            Log.e(LOG_TAG, "Error Downloading raw file");
            return;
        }

        final String FLICKER_ITEMS = "items";
        final String FLICKER_TITLE = "title";
        final String FLICKER_MEDIA = "media";
        final String FLICKER_PHOTO_URL = "m";
        final String FLICKER_AUTHOR = "author";
        final String FLICKER_AUTHOR_ID = "author_id";
        final String FLICKER_LINK = "link";
        final String FLICKER_TAGS = "tags";

        try {
            JSONObject jsonData = new JSONObject(getmData());
            JSONArray itemsArray = jsonData.getJSONArray(FLICKER_ITEMS);


            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject jsonPhoto = itemsArray.getJSONObject(i);
                String title = jsonPhoto.getString(FLICKER_TITLE);
                String author = jsonPhoto.getString(FLICKER_AUTHOR);
                String author_id = jsonPhoto.getString(FLICKER_AUTHOR_ID);
                //String link = jsonPhoto.getString(FLICKER_LINK);
                String tags = jsonPhoto.getString(FLICKER_TAGS);

                JSONObject jsonMedia = jsonPhoto.getJSONObject(FLICKER_MEDIA);
                String photoUrl = jsonMedia.getString(FLICKER_PHOTO_URL);

                String link=photoUrl.replace("_m.","_b.");
                Photo photoObject = new Photo(title, author, author_id, link, tags, photoUrl);
                this.mPhotos.add(photoObject);

            }

            for (Photo singlePhoto : mPhotos) {
                Log.v(LOG_TAG, singlePhoto.toString());
            }

        } catch (JSONException jsone) {
            jsone.printStackTrace();
            Log.e(LOG_TAG, "Error reading JSON Data");
        }
    }

    public class DownloadJsonData extends DownloadRawData {
        protected void onPostExecute(String webData) {
            super.onPostExecute(webData);
            processResult();
        }

        protected String doInBackground(String... parms) {
            String[] par={mDestinationUri.toString()};
            return super.doInBackground(par);
        }
    }


}
