package com.anandkumar.flickerbrowser;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by Anand on 5/17/2016.
 */
public class BaseActivity extends ActionBarActivity {

    private Toolbar toolbar;

    protected Toolbar activateToolbar(){
        if(toolbar==null){
            toolbar=(Toolbar)findViewById(R.id.app_bar);
            setSupportActionBar(toolbar);
        }
        return toolbar;
    }
}
