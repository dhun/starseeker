/**
 * 
 */
package jp.gr.java_conf.dhun.starseeker.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

import jp.gr.java_conf.dhun.starseeker.system.persistence.dao.sql.DatabaseHelper;

/**
 * ファイルユーティリティ
 * 
 * @author j_hosoya
 * 
 */
public final class FileUtils {

    private FileUtils() {
    }

    /**
     * ファイルをコピーします.<br/>
     * 
     * @param srcFile コピー元となるファイル
     * @param dstFile コピー先となるファイル
     * @throws IOException I/O例外
     */
    public static void copyFile(File srcFile, File dstFile) throws IOException {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            bis = new BufferedInputStream(new FileInputStream(srcFile));
            bos = new BufferedOutputStream(new FileOutputStream(dstFile));
            copyStream(bis, bos);

        } finally {
            closeIgnoreIOException(bis);
            closeIgnoreIOException(bos);
        }
    }

    /**
     * 入力ストリームの内容を出力ストリームへすべて出力します.<br/>
     * 
     * @param srcStream コピー元となる入力ストリーム
     * @param dstStream コピー先となる出力ストリーム
     * @throws IOException I/O例外
     */
    public static void copyStream(InputStream srcStream, OutputStream dstStream) throws IOException {
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
            closeIgnoreIOException(bis);
            closeIgnoreIOException(bos);
        }
    }

    /**
     * 指定されたファイルに、指定された文字列を出力します.<br/>
     * 
     * @param file ファイル
     * @param lines ファイルに出力する文字列のリスト
     * @throws IOException I/O例外
     */
    public static void writeLines(File file, List<String> lines) throws IOException {
        BufferedWriter br = null;

        try {
            br = new BufferedWriter(new FileWriter(file));
            for (String line : lines) {
                br.write(line);
                br.newLine();
            }
            br.flush();

        } finally {
            FileUtils.closeIgnoreIOException(br);
        }
    }

    /**
     * 一時ファイルを新規作成して、指定された文字列を出力します.<br/>
     * 一時ファイルが不要になったら、<b>必ず呼び出し側で削除</b>してください.<br/>
     * 
     * @param dir 一時ファイルの親ディレクトリ
     * @param lines 一時ファイルに出力する文字列のリスト
     * @return 一時ファイル
     * @throws IOException I/O例外
     */
    public static File createTempFileAndWriteLines(File dir, List<String> lines) throws IOException {
        File file = null;
        try {
            file = File.createTempFile("makeInitialDb", ".tmp", dir);
            file.deleteOnExit(); // 保険で呼び出してるけど、これで消える保証はない
            FileUtils.writeLines(file, lines);
            return file;

        } catch (IOException e) {
            if (file != null) {
                file.delete();
            }
            throw e;
        }
    }

    /**
     * 指定された入力ストリームをクローズします. このときI/O例外が発生しても無視します.
     */
    public static void closeIgnoreIOException(InputStream is) {
        if (null != is) {
            try {
                is.close();
            } catch (IOException e) {
                LogUtils.w(DatabaseHelper.class, "入力ストリームのクローズに失敗した. 処理は継続する", e);
            }
        }
    }

    /**
     * 指定された出力ストリームをクローズします. このときI/O例外が発生しても無視します.
     */
    public static void closeIgnoreIOException(OutputStream os) {
        if (null != os) {
            try {
                os.close();
            } catch (IOException e) {
                LogUtils.w(DatabaseHelper.class, "出力ストリームのクローズに失敗した. 処理は継続する", e);
            }
        }
    }

    /**
     * 指定されたリーダーをクローズします. このときI/O例外が発生しても無視します.
     */
    public static void closeIgnoreIOException(Reader reader) {
        if (null != reader) {
            try {
                reader.close();
            } catch (IOException e) {
                LogUtils.w(DatabaseHelper.class, "リーダーのクローズに失敗した. 処理は継続する", e);
            }
        }
    }

    /**
     * 指定されたライターをクローズします. このときI/O例外が発生しても無視します.
     */
    public static void closeIgnoreIOException(Writer writer) {
        if (null != writer) {
            try {
                writer.close();
            } catch (IOException e) {
                LogUtils.w(DatabaseHelper.class, "ライターのクローズに失敗した. 処理は継続する", e);
            }
        }
    }
}
