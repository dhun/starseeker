/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.ui.dialog.listener;

/**
 * 何かしらのデータが選択されたことを受け取るリスナ
 * 
 * @author j_hosoya
 * 
 * @param <T> 選択されたデータのデータ型
 */
public interface OnChooseDataListener<T> {

    /**
     * 何かしらのデータが選択されたときに呼び出されます
     * 
     * @param data 選択されたデータ
     */
    void onChooseData(T data);
}
