/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.util;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import android.content.Context;

/**
 * Assetリソースに関するユーティリティ/<br/>
 * 
 * @author jun
 * 
 */
public final class AssetsUtils {

    /**
     * デフォルト・コンストラクタ(封殺).<br/>
     */
    private AssetsUtils() {
    }

    /**
     * 指定されたパスのAssetリソースが存在するかを判定します.<br/>
     */
    public static boolean exists(Context context, File file) {
        String[] files = null;
        try {
            files = context.getAssets().list(file.getParent());
        } catch (IOException e) {
            LogUtils.w(AssetsUtils.class, "ファイルの存在確認処理で例外が発生した. path=[" + file.getPath() + "]", e);
            return false;
        }

        if (Arrays.asList(files).contains(file.getName())) {
            LogUtils.d(AssetsUtils.class, "ファイルが存在した. path=[" + file.getPath() + "]");
            return true;

        } else {
            LogUtils.d(AssetsUtils.class, "ファイルは存在しない. path=[" + file.getPath() + "]");
            return true;
        }
    }
}
