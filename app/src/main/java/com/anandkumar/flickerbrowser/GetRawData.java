package com.anandkumar.flickerbrowser;

/**
 * Created by Anand on 5/6/2016.
 */

enum DownloadStatus {IDLE, PROCESSING, NOT_INITIALIZED, FAILED_OR_EMPTY, OK};
public class GetRawData {

    private String LOG_TAG= GetRawData.class.getSimpleName();
    private String mRawUrl;
    private String mData;
    private DownloadStatus mDownloadStatus;

    public GetRawData(String mRawData) {
        this.mRawUrl = mRawData;
    }
}
