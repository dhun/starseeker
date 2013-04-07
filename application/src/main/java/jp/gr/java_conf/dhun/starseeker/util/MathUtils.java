/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.util;

/**
 * {@link java.lang.Math}の拡張ユーティリティ
 * 
 * @author jun
 * 
 */
public final class MathUtils {

    private MathUtils() {
    }

    /**
     * 指定された数値を切り上げます.<br/>
     * 
     * @param value 数値
     * @return <table border="1">
     *         <tr>
     *         <th>$value</th>
     *         <th>戻り値</th>
     *         <th>備考</th>
     *         </tr>
     *         <tr>
     *         <td align="right">0.0</td>
     *         <td align="right">0.0</td>
     *         <td>&nbsp;</td>
     *         </tr>
     *         <tr>
     *         <td align="right">+1.5</td>
     *         <td align="right">+2.0</td>
     *         <td>&nbsp;</td>
     *         </tr>
     *         <tr>
     *         <td align="right">-1.5</td>
     *         <td align="right">-2.0</td>
     *         <td>{@link Math#floor(double)}だと-1.0
     *         </tr>
     *         </table>
     */
    public static final double ceil(double value) {
        return (value >= 0) ? Math.ceil(value) : Math.floor(value);
    }

    /**
     * 指定された数値を切り捨てます.<br/>
     * 
     * @param value 数値
     * @return <table border="1">
     *         <tr>
     *         <th>$value</th>
     *         <th>戻り値</th>
     *         <th>備考</th>
     *         </tr>
     *         <tr>
     *         <td align="right">0.0</td>
     *         <td align="right">0.0</td>
     *         <td>&nbsp;</td>
     *         </tr>
     *         <tr>
     *         <td align="right">+1.5</td>
     *         <td align="right">+1.0</td>
     *         <td>&nbsp;</td>
     *         </tr>
     *         <tr>
     *         <td align="right">-1.5</td>
     *         <td align="right">-1.0</td>
     *         <td>{@link Math#floor(double)}だと-2.0
     *         </tr>
     *         </table>
     */
    public static final double floor(double value) {
        return (value >= 0) ? Math.floor(value) : Math.ceil(value);
    }

    /**
     * 指定された数値を四捨五入します.<br/>
     * 
     * @param value 数値
     * @return <table border="1">
     *         <tr>
     *         <th>$value</th>
     *         <th>戻り値</th>
     *         <th>備考</th>
     *         </tr>
     *         <tr>
     *         <td align="right">0.0</td>
     *         <td align="right">0.0</td>
     *         <td>&nbsp;</td>
     *         </tr>
     *         <tr>
     *         <td align="right">+1.5</td>
     *         <td align="right">+2.0</td>
     *         <td>&nbsp;</td>
     *         </tr>
     *         <tr>
     *         <td align="right">-1.5</td>
     *         <td align="right">-2.0</td>
     *         <td>{@link Math#floor(double)}だと-1.0
     *         </tr>
     *         </table>
     */
    public static final double round(double value) {
        return floor(value + ((value >= 0) ? +0.5 : -0.5));
    }
}
