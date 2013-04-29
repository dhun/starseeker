/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.ui.dialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jp.gr.java_conf.dhun.starseeker.system.model.star.Star;
import jp.gr.java_conf.dhun.starseeker.system.persistence.dao.sql.ConstellationDataDao;
import jp.gr.java_conf.dhun.starseeker.system.persistence.dao.sql.DatabaseHelper;
import jp.gr.java_conf.dhun.starseeker.system.persistence.dao.sql.StarDataDao;
import jp.gr.java_conf.dhun.starseeker.system.persistence.entity.ConstellationData;
import jp.gr.java_conf.dhun.starseeker.system.persistence.entity.StarData;
import jp.gr.java_conf.dhun.starseeker.ui.dto.SeekTarget;
import jp.gr.java_conf.dhun.starseeker.ui.dto.SeekTarget.SeekConstellationData;
import jp.gr.java_conf.dhun.starseeker.ui.dto.SeekTarget.SeekStarData;
import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;

/**
 * @author jun
 * 
 */
public class ChooseSeekTargetDialogBuilder extends AbstractChooseDataDialogBuilder<Star> {

    private final Context context;

    public ChooseSeekTargetDialogBuilder(Activity activity) {
        super(activity);

        this.context = activity;
    }

    @Override
    protected void setupBuilder() {
        super.setupBuilder();

        final String GROUP_LABEL1 = "g1";
        final String CHILD_LABEL1 = "c1";
        final String CHILD_LABEL2 = "c2";

        Map<String, List<SeekTarget<?>>> listDataMap = createExpandableListData();
        List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
        List<List<Map<String, Object>>> childData = new ArrayList<List<Map<String, Object>>>();

        for (Entry<String, List<SeekTarget<?>>> listDataEntry : listDataMap.entrySet()) {
            Map<String, String> map = new HashMap<String, String>();
            map.put(GROUP_LABEL1, listDataEntry.getKey());
            groupData.add(map);

            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            for (SeekTarget<?> seekTarget : listDataEntry.getValue()) {
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2.put(CHILD_LABEL1, seekTarget.getName());
                map2.put(CHILD_LABEL2, seekTarget);
                list.add(map2);
            }
            childData.add(list);
        }

        final ExpandableListAdapter listAdapter = new SimpleExpandableListAdapter( //
                context, //
                groupData, //
                android.R.layout.simple_expandable_list_item_1, // groupLayout
                new String[] { GROUP_LABEL1 }, // groupFrom
                new int[] { android.R.id.text1 }, // groupTo
                childData, //
                android.R.layout.simple_expandable_list_item_2, // childLayout
                new String[] { CHILD_LABEL1 }, // childFrom
                new int[] { android.R.id.text1 } // childTo
        );

        ExpandableListView listView = new ExpandableListView(context);
        listView.setAdapter(listAdapter);
        listView.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(context, listAdapter.getChild(groupPosition, childPosition).toString(), Toast.LENGTH_SHORT).show();
                ChooseSeekTargetDialogBuilder.this.dialog.dismiss();
                return true;
            }
        });
        builder.setView(listView);
    }

    private Map<String, List<SeekTarget<?>>> createExpandableListData() {
        // データ抽出
        List<SeekTarget<?>> seekTargets = new ArrayList<SeekTarget<?>>();

        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            for (StarData data : new StarDataDao(db).findByKanaNotNull()) {
                if (data.getMagnitude() <= StarData.MAGNITUDE_FOR_DISPLAY_UPPER) {
                    seekTargets.add(new SeekStarData(data));
                }
            }
            for (ConstellationData data : new ConstellationDataDao(db).findByKanaNotNull()) {
                seekTargets.add(new SeekConstellationData(data));
            }

        } finally {
            db.close();
        }

        Map<String, List<String>> kanaPrifixMap = new LinkedHashMap<String, List<String>>();
        kanaPrifixMap.put("ア", new ArrayList<String>(Arrays.asList(new String[] { "ア", "イ", "ウ", "エ", "オ" })));
        kanaPrifixMap.put("カ", new ArrayList<String>(Arrays.asList(new String[] { "カ", "キ", "ク", "ケ", "コ", "ガ", "ギ", "グ", "ゲ", "ゴ" })));
        kanaPrifixMap.put("サ", new ArrayList<String>(Arrays.asList(new String[] { "サ", "シ", "ス", "セ", "ソ", "ザ", "ジ", "ズ", "ゼ", "ゾ" })));
        kanaPrifixMap.put("タ", new ArrayList<String>(Arrays.asList(new String[] { "タ", "チ", "ツ", "テ", "ト", "ダ", "ヂ", "ヅ", "デ", "ド" })));
        kanaPrifixMap.put("ナ", new ArrayList<String>(Arrays.asList(new String[] { "ナ", "ニ", "ヌ", "ネ", "ノ" })));
        kanaPrifixMap.put("ハ", new ArrayList<String>(Arrays.asList(new String[] { "ハ", "ヒ", "フ", "ヘ", "ホ", "バ", "ビ", "ブ", "ベ", "ボ", "パ", "ピ", "プ", "ペ", "ポ" })));
        kanaPrifixMap.put("マ", new ArrayList<String>(Arrays.asList(new String[] { "マ", "ミ", "ム", "メ", "モ" })));
        kanaPrifixMap.put("ヤ", new ArrayList<String>(Arrays.asList(new String[] { "ヤ", "ユ", "ヨ" })));
        kanaPrifixMap.put("ラ", new ArrayList<String>(Arrays.asList(new String[] { "ラ", "リ", "ル", "レ", "ロ" })));
        kanaPrifixMap.put("ワ", new ArrayList<String>(Arrays.asList(new String[] { "ワ", "ヲ", "ン" })));

        // カナ毎に分類
        Map<String, List<SeekTarget<?>>> results = new LinkedHashMap<String, List<SeekTarget<?>>>();

        for (Entry<String, List<String>> kanaPrefixEntry : kanaPrifixMap.entrySet()) {
            List<String> kanaPrefixs = kanaPrefixEntry.getValue();
            List<SeekTarget<?>> result = new ArrayList<SeekTarget<?>>();

            int i = 0;
            while (i < seekTargets.size()) {
                SeekTarget<?> seekTarget = seekTargets.get(i);
                String kanaPrifix = seekTarget.getKana().substring(0, 1);
                if (kanaPrefixs.contains(kanaPrifix)) {
                    seekTargets.remove(i);
                    result.add(seekTarget);

                } else {
                    i++;
                }
            }
            results.put(kanaPrefixEntry.getKey(), result);
        }

        results.put("その他", new ArrayList<SeekTarget<?>>(seekTargets));

        // カナ順にソート
        Comparator<SeekTarget<?>> seekTargetcComparator = new Comparator<SeekTarget<?>>() {
            @Override
            public int compare(SeekTarget<?> lhs, SeekTarget<?> rhs) {
                return lhs.getKana().compareTo(rhs.getKana());
            }
        };
        for (List<SeekTarget<?>> list : results.values()) {
            Collections.sort(list, seekTargetcComparator);
        }

        return results;
    }
}
