/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system.exception;

/**
 * スターシーカーシステムの例外.<br/>
 * 
 * @author jun
 * 
 */
public class StarSeekerException extends Exception {

    public StarSeekerException() {
        super();
    }

    public StarSeekerException(String detailMessage) {
        super(detailMessage);
    }

    public StarSeekerException(Throwable throwable) {
        super(throwable);
    }

    public StarSeekerException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
