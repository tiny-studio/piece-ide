package com.piece_framework.piece_ide.flow_designer.model;

import java.io.Serializable;

/**
 * イベントハンドラクラス.
 * 
 * @author MATSUFUJI Hideharu
 * @version 0.1.0
 * @since 0.1.0
 *
 */
public class EventHandler implements Serializable {

    private static final long serialVersionUID = 8627376257429314590L;
    
    private String fClassName;
    private String fMethodName;
    
    /**
     * コンストラクタ.
     * 
     * @param className クラス名
     * @param methodName メソッド名
     */
    public EventHandler(String className, String methodName) {
        setClassName(className);
        setMethodName(methodName);
    }
    
    /**
     * クラス名を返す.
     * 
     * @return クラス名
     */
    public String getClassName() {
        return fClassName;
    }
    
    /**
     * クラス名を設定する.
     * 
     * @param className クラス名
     */
    public void setClassName(String className) {
        fClassName = className;
    }

    /**
     * メソッド名を返す.
     * 
     * @return メソッド名
     */
    public String getMethodName() {
        return fMethodName;
    }
    
    /**
     * メソッド名を設定する.
     * 
     * @param methodName メソッド名
     */
    public void setMethodName(String methodName) {
        fMethodName = methodName;
    }
}
