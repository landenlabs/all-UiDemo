package com.landenlabs.all_UiDemo.frag;

import android.animation.AnimatorInflater;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.landenlabs.all_UiDemo.R;
import com.landenlabs.all_UiDemo.Ui;

import java.util.Arrays;
import java.util.List;

/**
 * Demonstrate Image Scale modes.
 *
 * @author Dennis Lang (LanDen Labs)
 * @see <a href="http://landenlabs.com/android"> author's web-site </a>
 */

public class FullScreenFrag  extends UiFragment implements View.OnClickListener  {

    private final List<String> mListStrings = Arrays.asList(
            "FullScreen",
            "Hide Navigation",
            "Immersive",
            "Layout FullScreen",
            "Layout Hide Nav",
            "Layout Stable",
            "Light Status Bar",
            "Low Profile",
            "Visible",

            "Add Bar Bg",    //  getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            "Add Bar Clear"  //                           .addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            );

    // ---- Local Data ----
    private View mRootView;
    private ListView mList1View;
    private ListView mList2View;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fullscreen, container, false);

        setup();
        return mRootView;
    }

    @Override
    public int getFragId() {
        return R.id.fullscreen_id;
    }

    @Override
    public String getName() {
        return "FullScreen";
    }

    @Override
    public String getDescription() {
        return "??";
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setVisualBtn1:
                setSystemUiVisibility(mList1View);
                break;
            case R.id.setVisualBtn2:
                setSystemUiVisibility(mList2View);
                break;
        }
    }

    private void setup() {
        mList1View = setupList(R.id.sysuilist1);
        mList2View = setupList(R.id.sysuilist2);

        Ui.viewById(mRootView, R.id.setVisualBtn1).setOnClickListener(this);
        Ui.viewById(mRootView, R.id.setVisualBtn2).setOnClickListener(this);
    }

    private ListView setupList(int listResId) {
        ListView listView = Ui.viewById(mRootView, listResId);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // String itemStr = listView.getItemAtPosition(position).toString();
                // title.setText(itemStr);
                if(Build.VERSION.SDK_INT >= 21) {
                    view.setStateListAnimator(AnimatorInflater.loadStateListAnimator(view.getContext(), R.animator.press));
                }
                view.setPressed(true);
            }
        });

        int mRowLayoutRes = android.R.layout.simple_list_item_multiple_choice;
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(new ArrayAdapter<>(mRootView.getContext(), mRowLayoutRes, mListStrings));

        int originalFlags = getWindow().getAttributes().flags;

        /*
        // TODO - set toggle state based on initial flag state.
        int pos = mListStrings.lastIndexOf("Add Bar Bg");

        This does not work because listAdapter will remake ui view objects, need a custom adapter.

        Object item = listView.getAdapter().getView(pos, null, listView);
        if (item instanceof Checkable) {
            boolean on = isBitSet(originalFlags, WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            ((Checkable)listView.getAdapter().getItem(pos)).setChecked(on);
        }

        if (isBitSet(originalFlags, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)) {
        }
        */
        return listView;
    }

    private static boolean isBitSet(int val, int bits) {
        return (val & bits) == bits;
    }

    private void setSystemUiVisibility(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();

        int visualFlags = 0;
        int cnt = listAdapter.getCount();
        for (int idx = 0; idx != cnt; idx++) {
            View view = listView.getChildAt(idx);
            String str = (String)listAdapter.getItem(idx);
            if (view instanceof AppCompatCheckedTextView) {
                AppCompatCheckedTextView cb = (AppCompatCheckedTextView)view;
                if (cb.isChecked())
                switch (str) {
                    case "FullScreen":
                        visualFlags |= View.SYSTEM_UI_FLAG_FULLSCREEN;
                        break;
                    case "Hide Navigation":
                        visualFlags |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
                        break;
                    case "Immersive":
                        visualFlags |= View.SYSTEM_UI_FLAG_IMMERSIVE;
                        break;
                    case "Layout FullScreen":
                        visualFlags |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
                        break;
                    case "Layout Hide Nav":
                        visualFlags |= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
                        break;
                    case "Layout Stable":
                        visualFlags |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                        break;
                    case "Light Status Bar":
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            visualFlags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                        }
                        break;
                    case "Low Profile":
                        visualFlags |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
                        break;
                    case "Visible":
                        visualFlags |= View.SYSTEM_UI_FLAG_VISIBLE;
                        break;
                }
                switch (str) {
                    case "Add Bar Bg":
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                            if (cb.isChecked()) {
                                getWindowSafe().addFlags(
                                        WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                            } else {
                                getWindowSafe().clearFlags(
                                        WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                            }
                        break;
                    case "Add Bar Clear":
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                            if (cb.isChecked()) {
                                getWindowSafe().addFlags(
                                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                            } else {
                                getWindowSafe().clearFlags(
                                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                            }
                        break;
                }
            }
        }

        mRootView.setSystemUiVisibility(visualFlags);
    }
}
