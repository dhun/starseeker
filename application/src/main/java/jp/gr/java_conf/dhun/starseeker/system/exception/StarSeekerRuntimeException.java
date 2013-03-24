/**
 *
 */
package jp.gr.java_conf.dhun.starseeker.system.exception;

/**
 * スターシーカーシステムの実行時例外.<br/>
 * 
 * @author jun
 * 
 */
public class StarSeekerRuntimeException extends RuntimeException {

    public StarSeekerRuntimeException() {
        super();
    }

    public StarSeekerRuntimeException(String detailMessage) {
        super(detailMessage);
    }

    public StarSeekerRuntimeException(Throwable throwable) {
        super(throwable);
    }

    public StarSeekerRuntimeException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
