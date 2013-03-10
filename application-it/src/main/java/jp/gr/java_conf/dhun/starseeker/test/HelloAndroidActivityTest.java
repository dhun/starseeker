package jp.gr.java_conf.dhun.starseeker.test;

import android.test.ActivityInstrumentationTestCase2;
import jp.gr.java_conf.dhun.starseeker.*;
import jp.gr.java_conf.dhun.starseeker.ui.activity.ResolveTerminalStateActivity;

public class HelloAndroidActivityTest extends ActivityInstrumentationTestCase2<ResolveTerminalStateActivity> {

    public HelloAndroidActivityTest() {
        super(ResolveTerminalStateActivity.class);
    }

    public void testActivity() {
        ResolveTerminalStateActivity activity = getActivity();
        assertNotNull(activity);
    }
}

