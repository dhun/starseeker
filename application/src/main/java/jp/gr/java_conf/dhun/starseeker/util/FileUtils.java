/**
 * 
 */
package jp.gr.java_conf.dhun.starseeker.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import jp.gr.java_conf.dhun.starseeker.system.persistence.dao.sql.DatabaseHelper;
import android.content.Context;

/**
 * @author j_hosoya
 * 
 */
public final class FileUtils {

    private FileUtils() {
    }

    public static void copyStream(Context context, InputStream srcStream, OutputStream dstStream) throws IOException {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            bis = new BufferedInputStream(srcStream);
            bos = new BufferedOutputStream(dstStream);

            byte[] buffer = new byte[1024 * 10];
            int length = 0;
            while (-1 != (length = bis.read(buffer))) {
                bos.write(buffer, 0, length);
            }
            bos.flush();

        } finally {
            closeStreamIgnoreIOException(bis);
            closeStreamIgnoreIOException(bos);
        }
    }

    public static void closeStreamIgnoreIOException(InputStream is) {
        if (null != is) {
            try {
                is.close();
            } catch (IOException e) {
                LogUtils.w(DatabaseHelper.class, "入力ストリームのクローズに失敗した. 処理は継続する", e);
            }
        }
    }

    public static void closeStreamIgnoreIOException(OutputStream os) {
        if (null != os) {
            try {
                os.close();
            } catch (IOException e) {
                LogUtils.w(DatabaseHelper.class, "出力ストリームのクローズに失敗した. 処理は継続する", e);
            }
        }
    }
}
