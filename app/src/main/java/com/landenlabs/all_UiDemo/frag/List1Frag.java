package com.landenlabs.all_UiDemo.frag;

import android.animation.AnimatorInflater;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.landenlabs.all_UiDemo.R;
import com.landenlabs.all_UiDemo.Ui;

import java.util.Arrays;
import java.util.List;

/**
 * Demonstrate list of checkboxes (two different types).
 *   CheckBox  does not honor the single selection mode of ListView
 *   CheckedTextView honors the single selection mode of ListView
 * and most of the standard pre-built list row layouts.
 *
 * @author Dennis Lang (LanDen Labs)
 * @see <a href="http://landenlabs.com/android/index-m.html"> author's web-site </a>
 */
public class List1Frag  extends UiFragment implements CompoundButton.OnCheckedChangeListener {

    private final List<String> mListStrings = Arrays.asList("Apple", "Avocado", "Banana",
            "Blueberry", "Coconut", "Durian", "Guava", "Kiwifruit",
            "Jackfruit", "Mango", "Olive", "Pear", "Sugar-apple");

    // ---- Local Data ----
    private View mRootView;
    private TextView mTopTitle;
    private ListView mListView;
    @SuppressWarnings("FieldCanBeLocal")
    private int mRowLayoutRes = R.layout.list1_row_checkbox;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.list1, container, false);

        setup();
        return mRootView;
    }

    @Override
    public int getFragId() {
        return R.id.list1_id;
    }

    @Override
    public String getName() {
        return "List1";
    }

    @Override
    public String getDescription() {
        return "??";
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            setRowList(buttonView.getId());
        }
    }

    private void setRowList(int id) {
        switch (id) {
            case R.id.row_ckBoxRb:
                mTopTitle.setText("CheckBox (single)");
                mRowLayoutRes = R.layout.list1_row_checkbox;
                mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                mListView.setAdapter(
                        new ArrayAdapter<>(mRootView.getContext(), mRowLayoutRes, mListStrings));
                break;
            case R.id.row_ckTxBoxRb:
                mTopTitle.setText("CheckTextBox (single)");
                mRowLayoutRes = R.layout.list1_row_checktextbox;
                mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                mListView.setAdapter(
                        new ArrayAdapter<>(mRootView.getContext(), mRowLayoutRes, mListStrings));
                break;


            case R.id.row_simpleList1:
                mTopTitle.setText("Simple List");
                mRowLayoutRes = android.R.layout.simple_list_item_1;
                mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                mListView.setAdapter(
                        new ArrayAdapter<>(mRootView.getContext(), mRowLayoutRes, mListStrings));
                break;
            case R.id.row_simpleChk1:
                mTopTitle.setText("Simple Checked (single)");
                mRowLayoutRes = android.R.layout.simple_list_item_checked;
                mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                mListView.setAdapter(
                        new ArrayAdapter<>(mRootView.getContext(), mRowLayoutRes, mListStrings));
                break;

            case R.id.row_singleChoice1:
                mTopTitle.setText("Single Choice (single)");
                mRowLayoutRes = android.R.layout.simple_list_item_single_choice;
                mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                mListView.setAdapter(
                        new ArrayAdapter<>(mRootView.getContext(), mRowLayoutRes, mListStrings));
                break;
            case R.id.row_multiChoice1:
                mTopTitle.setText("Multi Choice (multi)");
                mRowLayoutRes = android.R.layout.simple_list_item_multiple_choice;
                mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                mListView.setAdapter(
                        new ArrayAdapter<>(mRootView.getContext(), mRowLayoutRes, mListStrings));
                break;

            case R.id.row_activatedList1:
                mTopTitle.setText("Activated (single)");
                mRowLayoutRes = android.R.layout.simple_list_item_activated_1;
                mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                mListView.setAdapter(
                        new ArrayAdapter<>(mRootView.getContext(), mRowLayoutRes, mListStrings));
                break;
            case R.id.row_selectable:
                mTopTitle.setText("Selectable (single)");
                mRowLayoutRes = android.R.layout.simple_selectable_list_item;
                mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                mListView.setAdapter(
                        new ArrayAdapter<>(mRootView.getContext(), mRowLayoutRes, mListStrings));
                break;

            case R.id.row_spinnerItem:
                mTopTitle.setText("SpinnerItem");
                mRowLayoutRes = android.R.layout.simple_spinner_item;
                mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                mListView.setAdapter(
                        new ArrayAdapter<>(mRootView.getContext(), mRowLayoutRes, mListStrings));
                break;

            case R.id.row_spinner_dropdown:
                mTopTitle.setText("SpinnerDropDown");
                mRowLayoutRes = android.R.layout.simple_spinner_dropdown_item;
                mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                mListView.setAdapter(
                        new ArrayAdapter<>(mRootView.getContext(), mRowLayoutRes, mListStrings));
                break;

            default:
                Toast.makeText(this.getActivity(), "Unknown UI press", Toast.LENGTH_LONG).show();
        }
    }

    private void setup() {
        mTopTitle = Ui.viewById(mRootView, R.id.topTitle);
        Ui.<RadioButton>viewById(mRootView, R.id.row_ckBoxRb).setOnCheckedChangeListener(this);
        Ui.<RadioButton>viewById(mRootView, R.id.row_ckTxBoxRb).setOnCheckedChangeListener(this);
        Ui.<RadioButton>viewById(mRootView, R.id.row_simpleList1).setOnCheckedChangeListener(this);
        Ui.<RadioButton>viewById(mRootView, R.id.row_simpleChk1).setOnCheckedChangeListener(this);
        Ui.<RadioButton>viewById(mRootView, R.id.row_singleChoice1).setOnCheckedChangeListener(this);
        Ui.<RadioButton>viewById(mRootView, R.id.row_multiChoice1).setOnCheckedChangeListener(this);
        Ui.<RadioButton>viewById(mRootView, R.id.row_activatedList1).setOnCheckedChangeListener(this);
        Ui.<RadioButton>viewById(mRootView, R.id.row_selectable).setOnCheckedChangeListener(this);
        Ui.<RadioButton>viewById(mRootView, R.id.row_spinnerItem).setOnCheckedChangeListener(this);
        Ui.<RadioButton>viewById(mRootView, R.id.row_spinner_dropdown).setOnCheckedChangeListener(this);

        mListView = Ui.viewById(mRootView, R.id.list1view);
        setRowList(R.id.row_ckBoxRb);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // String itemStr = listView.getItemAtPosition(position).toString();
                // title.setText(itemStr);
                mCurrentIdx = position;
                if(Build.VERSION.SDK_INT >= 21) {
                    view.setStateListAnimator(AnimatorInflater.loadStateListAnimator(view.getContext(), R.anim.press));
                }
                view.setPressed(true);
            }
        });
    }
}
