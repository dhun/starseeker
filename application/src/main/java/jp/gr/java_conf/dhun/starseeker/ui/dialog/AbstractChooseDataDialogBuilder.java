/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.ui.dialog;

import jp.gr.java_conf.dhun.starseeker.ui.dialog.listener.OnChooseDataListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 * 何かしらのデータを選択するダイアログのビルダ
 * 
 * @author jun
 * 
 * @param <T> 選択するデータのデータ型
 */
public abstract class AbstractChooseDataDialogBuilder<T> {

    protected final Activity activity;
    protected final AlertDialog.Builder builder;

    protected Integer dialogId = null;
    protected String dialogTitle;
    protected OnChooseDataListener<T> onChooseDataListener;

    /**
     * コンストラクタ
     */
    public AbstractChooseDataDialogBuilder(Activity activity) {
        this.activity = activity;
        this.builder = new AlertDialog.Builder(activity);
    }

    /**
     * ダイアログを生成します
     * 
     * @return ダイアログ
     */
    public AlertDialog create() {
        setupBuilder();
        return createDialog();
    }

    /**
     * ビルダをセットアップします
     */
    protected void setupBuilder() {
        if (null == dialogId) {
            throw new IllegalStateException("dialogId must be null.");
        }
        if (null == onChooseDataListener) {
            throw new IllegalStateException("onChooseDataListener must be null.");
        }

        builder.setTitle(dialogTitle);
        builder.setCancelable(true);
    }

    /**
     * ダイアログを生成する内部メソッド
     * 
     * @return ダイアログ
     */
    protected AlertDialog createDialog() {
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                activity.removeDialog(dialogId);
            }
        });
        return dialog;
    }

    /**
     * ダイアログのIDを設定します
     */
    public void setDialogId(int dialogId) {
        this.dialogId = dialogId;
    }

    /**
     * ダイアログのタイトルを設定します
     */
    public void setDialogTitle(String dialogTitle) {
        this.dialogTitle = dialogTitle;
    }

    /**
     * ダイアログで何かしらのデータが選択されたことを受け取るリスナを設定します
     */
    public void setOnChooseDataListener(OnChooseDataListener<T> listener) {
        this.onChooseDataListener = listener;
    }
}
