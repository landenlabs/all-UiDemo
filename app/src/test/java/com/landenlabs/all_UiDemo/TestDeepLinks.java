package com.landenlabs.all_UiDemo;

import android.app.Activity;
import android.content.Context;

import com.landenlabs.all_UiDemo.Util.PageItem;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import static org.junit.Assert.assertTrue;

/**
 * Test Deep Links
 */
@RunWith(RobolectricTestRunner.class)
public class TestDeepLinks {

    // RoboElectric
    Activity getActivity() {
        return  Robolectric.setupActivity(MainActivity.class);
    }
    Context getAppContext() {
        Context appContext = RuntimeEnvironment.application.getApplicationContext();
        return appContext;
    }

    @Test
    public void listDeepLinks() {

        System.out.format("\n List Deep Link Pages \n");
        int pageIdx= 0;
        for (PageItem item : MainActivity.getPageItems()) {
            //  adb shell am start -W -a android.intent.action.VIEW -d "landenlabs://alluidemo/page1"
            System.out.format("\"landenlabs://alluidemo/page%d?%s\"\n", pageIdx, item.mTitle.replaceAll(" ", "_"));
            pageIdx++;
        }
        System.out.format("\n Done\n");

        assertTrue("done", true);
    }
}
