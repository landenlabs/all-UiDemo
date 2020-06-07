package com.landenlabs.all_UiDemo.frag;

/*
 * Copyright (c) 2019 Dennis Lang (LanDen Labs) landenlabs@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the
 *  following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all copies or substantial
 *  portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN
 * NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *  @author Dennis Lang  (3/21/2015)
 *  @see http://landenlabs.com
 *
 */

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.landenlabs.all_UiDemo.R;
import com.landenlabs.all_UiDemo.Ui;
import com.landenlabs.all_UiDemo.Util.SysUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Demonstrate Expandable List
 *
 * @author Dennis Lang (LanDen Labs)
 * @see <a href="http://landenlabs.com/android"> author's web-site </a>
 */

public class ExpandListFrag  extends UiFragment {

    private View mRootView;

    private final LinkedHashMap<String, GroupItemsInfo> groupGroupList = new LinkedHashMap<>();
    private final ArrayList<GroupItemsInfo> grouplist = new ArrayList<>();
    private MyExpandableListAdapter  expandableListAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.layout_expandable_list, container, false);
        setup();
        return mRootView;
    }

    @Override
    public int getFragId() {
        return R.id.layout_expand_list_id;
    }

    @Override
    public String getName() {
        return "ExpandList";
    }

    @Override
    public String getDescription() {
        return "??";
    }

    private void setup() {
        ExpandableListView expListView = Ui.viewById(mRootView, R.id.expand_list);

        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getContext(), " Expanded", Toast.LENGTH_SHORT).show();
            }
        });

        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getContext(), " Collapsed", Toast.LENGTH_SHORT).show();
            }
        });


        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                    int groupPosition, long id) {
                Toast.makeText(getContext(), " onGroupClick", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View view,
                    int groupPosition, int childPosition, long id) {
                int cp = (int) expandableListAdapter.getChildId(groupPosition, childPosition);
                Toast.makeText(getContext(), " onClickChild grp="
                        + groupPosition
                        + " child="
                        + childPosition
                        + " cp="
                        + cp
                        , Toast.LENGTH_SHORT).show();
                return false;

            }
        });

        // I think this only works for ListView not ExpandableListView
        expListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), " onItemClick never called", Toast.LENGTH_SHORT).show();
            }
        });

        loadData();
        expandableListAdapter = new MyExpandableListAdapter(getContext(), grouplist);
        expListView.setAdapter(expandableListAdapter);
    }

    // Create some test data for ExpandableList
    private void loadData() {
        addGroup("Group #0")
                .addChild("child #0")
                .addChild("child #1")
                .addChild("child #2");

        addGroup("Group #1")
                .addChild("child #0")
                .addChild("child #1");

        addGroup("Group #2")
                .addChild("child #0")
                .addChild("child #1")
                .addChild("child #2");
    }

    // Add group and child to group.
    private GroupItemsInfo addGroup(String groupName) {

        // Check the hashmap if the group already exists
        GroupItemsInfo groupItemsInfo = groupGroupList.get(groupName);

        if (groupItemsInfo == null) {
            // Add the group if doesn't exists
            groupItemsInfo = new GroupItemsInfo(groupName);
            groupGroupList.put(groupName, groupItemsInfo);
            grouplist.add(groupItemsInfo);
        }
        return groupItemsInfo;
    }

    // =============================================================================================
    static class ChildItemsInfo {
        private final String name;
        ChildItemsInfo(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }
    }

    // =============================================================================================
    static class GroupItemsInfo {

        private final String name;
        private final ArrayList<ChildItemsInfo> children = new ArrayList<>();

        GroupItemsInfo(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }

        @NonNull
        ArrayList<ChildItemsInfo> getChildren() {
            return children;
        }

        GroupItemsInfo addChild(String childName) {
            getChildren().add(new ChildItemsInfo(name + " " + childName));
            return this;
        }
    }


    // =============================================================================================

    /**
     * Created by Gourav for AbhiAndroid on 19-03-2016.
     */
    static class MyExpandableListAdapter extends BaseExpandableListAdapter {

        // private Context context;
        private final ArrayList<GroupItemsInfo> groupList;

        @SuppressWarnings("unused")
        MyExpandableListAdapter(Context context, ArrayList<GroupItemsInfo> groupList) {
            // this.context = context;
            this.groupList = groupList;
        }

        /*
        @Override
        public void registerDataSetObserver(DataSetObserver observer) {
        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {
        }
        */

        @Override
        public int getGroupCount() {
            return groupList.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            ArrayList<ChildItemsInfo> productList = groupList.get(groupPosition).getChildren();
            return productList.size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return groupList.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            ArrayList<ChildItemsInfo> productList = groupList.get(groupPosition).getChildren();
            return productList.get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                ViewGroup parent) {
            GroupItemsInfo groupItemsInfo = (GroupItemsInfo) getGroup(groupPosition);
            if (convertView == null) {
                Context context = parent.getContext();
                LayoutInflater inf = SysUtils.getServiceSafe(context, Context.LAYOUT_INFLATER_SERVICE);
                convertView = inf.inflate(R.layout.layout_expandable_list_group, null);
            }

            TextView heading = Ui.viewById(convertView, R.id.exp_group1);
            heading.setText(groupItemsInfo.getName().trim());
            return convertView;

        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                View convertView, ViewGroup parent) {
            ChildItemsInfo detailInfo = (ChildItemsInfo) getChild(groupPosition, childPosition);
            if (convertView == null) {
                Context context = parent.getContext();
                LayoutInflater infalInflater =SysUtils.getServiceSafe(context, Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.layout_expandable_list_child, null);
            }
            TextView childItem = Ui.viewById(convertView, R.id.exp_child1);
            childItem.setText(detailInfo.getName().trim());

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        /*
        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public void onGroupExpanded(int groupPosition) {
        }

        @Override
        public void onGroupCollapsed(int groupPosition) {
        }

        @Override
        public long getCombinedChildId(long groupId, long childId) {
            return 0;
        }

        @Override
        public long getCombinedGroupId(long groupId) {
            // return super.getCombinedGroupId(groupId);
            return 0;
        }
        */

    }
}