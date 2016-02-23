package in.showoffs.showoffs.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import icepick.Icepick;
import icepick.State;

/**
 * Created by GRavi on 23-02-2016.
 */
public class BaseActivity extends AppCompatActivity {
    @State
    protected String baseMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }
}
