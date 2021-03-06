package com.anandkumar.flickerbrowser;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Anand on 5/6/2016.
 */

enum DownloadStatus {
    IDLE, PROCESSING, NOT_INITIALIZED, FAILED_OR_EMPTY, OK
};

public class GetRawData {

    private String LOG_TAG = GetRawData.class.getSimpleName();
    private String mRawUrl;
    private String mData;
    private DownloadStatus mDownloadStatus;

    public void setmRawUrl(String mRawUrl) {
        this.mRawUrl = mRawUrl;
    }

    public String getmRawUrl() {
        return mRawUrl;
    }

    public GetRawData(String mRawData) {
        this.mRawUrl = mRawData;
        mDownloadStatus = DownloadStatus.IDLE;
    }

    public void reset() {
        this.mDownloadStatus = DownloadStatus.IDLE;
        this.mData = null;
        this.mRawUrl = null;
    }

    public String getmData() {
        return mData;
    }


    public DownloadStatus getmDownloadStatus() {
        return mDownloadStatus;
    }

    public void execute(){
        mDownloadStatus=DownloadStatus.PROCESSING;
        DownloadRawData downloadRawData=new DownloadRawData();
        downloadRawData.execute(mRawUrl);
    }

    public class DownloadRawData extends AsyncTask<String, Void, String> {

        protected void onPostExecute(String webData) {
            mData=webData;
            Log.v(LOG_TAG,"Data returned was : "+mData);
            if(mData==null){
                if(mRawUrl==null){
                    mDownloadStatus=DownloadStatus.NOT_INITIALIZED;
                }else {
                    mDownloadStatus=DownloadStatus.FAILED_OR_EMPTY;
                }
            }else {
                mDownloadStatus=DownloadStatus.OK;
            }
        }

        protected String doInBackground(String... parms) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            if (parms == null) {
                return null;
            }

            try {
                URL url = new URL(parms[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputSteam = urlConnection.getInputStream();
                if (inputSteam == null) {
                    return null;
                }
                StringBuffer buffer = new StringBuffer();
                reader = new BufferedReader(new InputStreamReader(inputSteam));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                return buffer.toString();

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error", e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e(LOG_TAG, "Error Close stream", e);
                    }
                }
            }

        }
    }
}
