/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system.listener;

/**
 * スターシーカーシステムのリスナ.<br/>
 * 
 * @author jun
 * 
 */
public interface IStarSeekerListener {

    /**
     * 例外が発生したことを通知します.<br/>
     * 
     * @param e 例外
     */
    void onException(Exception e);
}
