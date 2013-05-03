/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.ui.dto;

import jp.gr.java_conf.dhun.starseeker.system.persistence.entity.ConstellationData;
import jp.gr.java_conf.dhun.starseeker.system.persistence.entity.StarData;

/**
 * @author jun
 * 
 */
public abstract class SeekTarget<T> {

    private final SeekTargetType seekTargetType;
    private final T seekTarget;

    private SeekTarget(SeekTargetType type, T seekTarget) {
        this.seekTargetType = type;
        this.seekTarget = seekTarget;
    }

    public SeekTargetType getSeekTargetType() {
        return seekTargetType;
    }

    public T getSeekTarget() {
        return seekTarget;
    }

    public abstract String getSeekTargetId();

    public abstract String getName();

    public abstract String getKana();

    public static enum SeekTargetType {
        STAR, CONSTELLATION;
    }

    public static class SeekStarData extends SeekTarget<StarData> {
        public SeekStarData(StarData data) {
            super(SeekTargetType.STAR, data);
        }

        @Override
        public String getSeekTargetId() {
            return getSeekTarget().getHipNumber().toString();
        }

        @Override
        public String getName() {
            return getSeekTarget().getName();
        }

        @Override
        public String getKana() {
            return getSeekTarget().getName();
        }
    }

    public static class SeekConstellationData extends SeekTarget<ConstellationData> {
        public SeekConstellationData(ConstellationData data) {
            super(SeekTargetType.CONSTELLATION, data);
        }

        @Override
        public String getSeekTargetId() {
            return getSeekTarget().getConstellationCode();
        }

        @Override
        public String getName() {
            return getSeekTarget().getJapaneseName();
        }

        @Override
        public String getKana() {
            return getSeekTarget().getJapaneseKana();
        }
    }
}
