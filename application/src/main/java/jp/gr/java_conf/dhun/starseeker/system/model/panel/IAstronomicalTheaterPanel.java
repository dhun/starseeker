package jp.gr.java_conf.dhun.starseeker.system.model.panel;

import jp.gr.java_conf.dhun.starseeker.model.Star;
import android.graphics.PointF;

/**
 * 天体シアターのパネルのインターフェース.<br/>
 * 
 * @author jun
 * 
 */
public interface IAstronomicalTheaterPanel {

    /**
     * パネルの座標を検証します.
     */
    void verifyCoordinatesRect();

    /**
     * 指定された星の座標がパネルに含まれるかを判定します.<br/>
     * 
     * @param star 星
     * @return 含まれていればtrue.
     */
    boolean contains(Star star);

    /**
     * 指定された星の座標をディスプレイ座標に変換して、displayCoordinatesに割り当てます.<br/>
     * 
     * @param star 星
     * @param displayCoordinates 星のディスプレイ座標を割り当てる変数
     */
    void remapToDisplayCoordinates(Star star, PointF displayCoordinates);

    /**
     * 天体シアターパネルの種類
     */
    enum AstronomicalTheaterPanelType {
        EAST_FACE(true, true),   // 東側正面のパネル
        EAST_BACK(true, false),  // 東側背面のパネル
        WEST_FACE(false, true),  // 西側正面のパネル
        WEST_BACK(false, false); // 西側背面のパネル

        private final boolean east; // 東側ならtrue. 西側ならfalse.
        private final boolean face; // 正面ならtrue. 背面ならfalse.

        private AstronomicalTheaterPanelType(boolean east, boolean face) {
            this.east = east;
            this.face = face;
        }

        public boolean isEast() {
            return east;
        }

        public boolean isWest() {
            return !east;
        }

        public boolean isFace() {
            return face;
        }

        public boolean isBack() {
            return !face;
        }
    }
}
