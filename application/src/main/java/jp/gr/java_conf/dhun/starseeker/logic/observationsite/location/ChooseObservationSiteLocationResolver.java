/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.logic.observationsite.location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

import jp.gr.java_conf.dhun.starseeker.model.ObservationSiteLocation;
import jp.gr.java_conf.dhun.starseeker.util.StarLocationUtil;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

/**
 * 観測地点をリスト選択で指定する観測地点の位置のリゾルバ.<br/>
 * 
 * @author jun
 * 
 */
public class ChooseObservationSiteLocationResolver implements IObservationSiteLocationResolver {

    // ********************************************************************************
    // == class

    /** 観測地点の選択肢 */
    private static final List<ObservationSiteLocation> locations;
    private static int nextId = 0;

    /**
     * @see http://www.gsi.go.jp/KOKUJYOHO/center.htm
     * @see http://世界地図.biz/006_1/cat3/
     */
    static {
        locations = new ArrayList<ObservationSiteLocation>();
        locations.add(newObservationSiteLocation("+139°41'", "+35°41'", "0°0'", "Asia/Tokyo", "東京－都庁")); // XXX strings.xml
        locations.add(newObservationSiteLocation("+135°45'", "+35°01'", "0°0'", "Asia/Tokyo", "京都－府庁"));
        locations.add(newObservationSiteLocation("+138°43'", "+35°21'", "0°0'", "Asia/Tokyo", "山梨－富士山"));
        locations.add(newObservationSiteLocation("+141°56'", "+45°31'", "0°0'", "Asia/Tokyo", "北端－北海道－宗谷岬"));
        locations.add(newObservationSiteLocation("+136°04'", "+20°25'", "0°0'", "Asia/Tokyo", "南端－東京－沖ノ鳥島"));
        locations.add(newObservationSiteLocation("+153°59'", "+24°16'", "0°0'", "Asia/Tokyo", "東端－東京－南鳥島"));
        locations.add(newObservationSiteLocation("+122°56'", "+24°26'", "0°0'", "Asia/Tokyo", "西端－沖縄－与那国島"));
        locations.add(newObservationSiteLocation("+105°83'", "+21°03'", "0°0'", "Asia/Ho_Chi_Minh", "ベトナム－ホーチミン"));
        locations.add(newObservationSiteLocation("-000°12'", "+51°49'", "0°0'", "Europe/London", "イギリス－ロンドン"));
        locations.add(newObservationSiteLocation("-154°40'", "+18°55'", "0°0'", "US/Hawaii", "アメリカ－ハワイ"));
        locations.add(newObservationSiteLocation("+059°33'", "+18°07'", "0°0'", "Europe/Stockholm", "スウェーデン－ストックホルム"));
    }

    private static ObservationSiteLocation newObservationSiteLocation(String latitude, String longitude, String altitude, String timezoneName, String name) {
        ObservationSiteLocation result = new ObservationSiteLocation(  //
                StarLocationUtil.convertAngleStringToFloat(latitude),  // 緯度
                StarLocationUtil.convertAngleStringToFloat(longitude), // 経度
                StarLocationUtil.convertAngleStringToFloat(altitude)); // 高度
        result.setId(nextId++);
        result.setName(name);
        result.setTimeZone(TimeZone.getTimeZone(timezoneName));
        return result;
    }

    public static List<ObservationSiteLocation> getObservationSiteLocations() {
        return Collections.unmodifiableList(locations);
    }

    // ********************************************************************************
    // == instance

    /** 観測地点の位置を解決できたことの通知を受け取るリスナ */
    private ObservationSiteLocationResolverListener onResolveObservationSiteLocationListener;

    private final Context context;
    private Dialog dialog;

    /**
     * コンストラクタ.<br/>
     * 
     * @param context Androidコンテキスト
     */
    public ChooseObservationSiteLocationResolver(Context context) {
        this.context = context;
    }

    // ********************************************************************************
    // IObservationSiteLocationResolver

    @Override
    public void resume() {
        ListAdapter adapter = new ArrayAdapter<ObservationSiteLocation>(context, android.R.layout.simple_list_item_1, locations) {
            private final LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = layoutInflater.inflate(android.R.layout.simple_list_item_1, null);
                }

                ObservationSiteLocation location = this.getItem(position);

                TextView textView = ((TextView) convertView.findViewById(android.R.id.text1));
                textView.setText(location.getName());
                textView.setTextColor(Color.BLACK);

                return convertView;
            }

        };

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setObservationSiteLocation(locations.get(which));
                ChooseObservationSiteLocationResolver.this.dialog = null;
            }
        };

        Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("観測地点の選択"); // XXX strings.xml
        builder.setAdapter(adapter, listener);
        builder.setCancelable(true);
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                ChooseObservationSiteLocationResolver.this.dialog = null;
            }
        });

        dialog = builder.create();
        dialog.show();
    }

    @Override
    public void pause() {
        if (null != dialog && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    public void setObservationSiteLocationResolverListener(ObservationSiteLocationResolverListener listener) {
        this.onResolveObservationSiteLocationListener = listener;
    }

    @Override
    public void setObservationSiteLocation(ObservationSiteLocation location) {
        if (null != onResolveObservationSiteLocationListener) {
            onResolveObservationSiteLocationListener.onResolveObservationSiteLocation(location);
        }
    }
}
