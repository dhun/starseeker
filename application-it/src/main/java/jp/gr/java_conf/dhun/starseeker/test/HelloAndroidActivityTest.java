package jp.gr.java_conf.dhun.starseeker.test;

import android.test.ActivityInstrumentationTestCase2;
import jp.gr.java_conf.dhun.starseeker.*;

public class HelloAndroidActivityTest extends ActivityInstrumentationTestCase2<HelloAndroidActivity> {

    public HelloAndroidActivityTest() {
        super(HelloAndroidActivity.class);
    }

    public void testActivity() {
        HelloAndroidActivity activity = getActivity();
        assertNotNull(activity);
    }
}

