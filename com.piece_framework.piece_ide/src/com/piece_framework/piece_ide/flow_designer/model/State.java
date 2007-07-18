// $Id$
package com.piece_framework.piece_ide.flow_designer.model;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

/**
 * ステートクラス.
 * 
 * @author MATSUFUJI Hideharu
 * @version 0.1.0
 * @since 0.1.0
 *
 */
public class State extends AbstractModel implements IPropertySource {
    
    private static final long serialVersionUID = -7778980617394743980L;
    
    /** ステート定数：イニシャル・ステート. */
    public static final int INITIAL_STATE = 1;
    /** ステート定数：ファイナル・ステート. */
    public static final int FINAL_STATE = 2;
    /** ステート定数：アクション・ステート. */
    public static final int ACTION_STATE = 3;
    /** ステート定数：ビュー・ステート. */
    public static final int VIEW_STATE = 4;
    
    private int fStateType;
    private String fName;
    private String fSummary;
    private String fView;
    private List<Event> fEvents = new ArrayList<Event>();
    
    private int fX;
    private int fY;
    
    private List<Transition> fIncomings = new ArrayList<Transition>();
    private List<Transition> fOutgoings = new ArrayList<Transition>();
    
    /**
     * コンストラクタ.
     * 
     * @param stateType ステートタイプ
     */
    public State(int stateType) {
        fStateType = stateType;
        createBuiltinEventForStateType();
    }
    
    /**
     * ステートタイプごとに必要なビルトインイベントを作成する.
     * 
     */
    private void createBuiltinEventForStateType() {
        if (fStateType == INITIAL_STATE) {
            fEvents.add(createBuiltinEvent("Initialize", null, "initialize"));
        } else if (fStateType == ACTION_STATE
                    || fStateType == VIEW_STATE) {
            fEvents.add(createBuiltinEvent("Entry", null, "entry"));
            fEvents.add(createBuiltinEvent("Exit", null, "exit"));
            fEvents.add(createBuiltinEvent("Activity", null, "activity"));
        } else if (fStateType == FINAL_STATE) {
            fEvents.add(createBuiltinEvent("Finalize", null, "finalize"));
        }
    }
    
    /**
     * ビルトインイベントオブジェクトを作成する.
     * 
     * @param eventName ビルトインイベント名
     * @param className クラス名
     * @param methodName メソッド名
     * @return ビルトインイベント
     */
    private Event createBuiltinEvent(
                    String eventName, 
                    String className, 
                    String methodName) {
        Event event = new Event();
        event.setName(eventName);
        event.setBuiltinEvent(true);
        event.setEventHandler(className, methodName);
        
        return event;
    }

    /**
     * リスナー追加時、イベントにも同様のリスナーを登録する.
     * 
     * @param listener 登録するリスナー
     * @see com.piece_framework.piece_ide.flow_designer.model.AbstractModel
     *          #addPropertyChangeListener(java.beans.PropertyChangeListener)
     */
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        for (Event event : fEvents) {
            event.addPropertyChangeListener(listener);
        }
    }

    /**
     * リスナー削除時、イベントからも同様にリスナーを削除する.
     * 
     * @param listener 削除するリスナー
     * @see com.piece_framework.piece_ide.flow_designer.model.AbstractModel
     *          #removePropertyChangeListener(java.beans.PropertyChangeListener)
     */
    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        super.removePropertyChangeListener(listener);
        for (Event event : fEvents) {
            event.removePropertyChangeListener(listener);
        }
    }

    /**
     * ステートタイプを返す.
     * 
     * @return ステートタイプ
     */
    public int getStateType() {
        return fStateType;
    }

    /**
     * ステート名を返す.
     * 
     * @return ステート名
     */
    public String getName() {
        return fName;
    }

    /**
     * ステート名を設定する.
     * 
     * @param name ステート名
     */
    public void setName(String name) {
        String old = fName;
        fName = name;
        firePropertyChange("name", old, fName);
    }

    /**
     * 概要を取得する.
     * 
     * @return 概要
     */
    public String getSummary() {
        return fSummary;
    }

    /**
     * 概要を設定する.
     * 
     * @param summary 概要
     */
    public void setSummary(String summary) {
        String old = fSummary;
        fSummary = summary;
        firePropertyChange("summary", old, fSummary);
    }
    
    /**
     * ビュー名を返す.
     * ステートタイプが VIEW_STATE でなければ、NULL を返す。
     * 
     * @return ビュー名
     */
    public String getView() {
        if (fStateType != VIEW_STATE) {
            return null;
        }
        return fView;
    }

    /**
     * ビュー名を設定する.
     * ステートタイプが VIEW_STATE でなければなにもしない。
     * 
     * @param view ビュー名
     */
    public void setView(String view) {
        if (fStateType == VIEW_STATE) {
            String old = fView;
            fView = view;
            firePropertyChange("view", old, fView);
        }
    }

    /**
     * イベント一覧を返す.
     * 
     * @return イベント一覧
     */
    public List<Event> getEventList() {
        return fEvents;
    }
    
    /**
     * 遷移イベント一覧を返す.
     * 
     * @return 遷移イベント一覧
     */
    public List<Event> getTransitionEventList() {
        List<Event> transitionEventList = new ArrayList<Event>();
        for (Event event : getEventList()) {
            if (event.isTransitionEvent()) {
                transitionEventList.add(event);
            }
        }
        return transitionEventList;
    }
    
    /**
     * イベントを追加する.
     * イベントを追加する前にステートに登録されているリスナーをセットする。<br>
     * また遷移イベントの場合は遷移先のステートに変更を通知する。
     * 
     * @param event イベント
     */
    public void addEvent(Event event) {
        for (PropertyChangeListener listener : getPropertyChangeListener()) {
            event.addPropertyChangeListener(listener);
        }
        fEvents.add(event);
        firePropertyChange("event", null, (Object) fEvents);
        
        if (event.isTransitionEvent() && event.getNextState() != null) {
            event.getNextState().firePropertyChange(
                    "target", null, null);
        }
    }
    
    /**
     * イベントを削除する.
     * イベントが保持しているリスナーを削除する。<br>
     * また遷移イベントの場合は遷移先のステートに変更を通知する。
     * 
     * @param event イベント
     */
    public void removeEvent(Event event) {
        List<PropertyChangeListener> removeListenerList = 
                new ArrayList<PropertyChangeListener>();
        removeListenerList.addAll(
                Arrays.asList(event.getPropertyChangeListener()));
        for (PropertyChangeListener listener : removeListenerList) {
            event.removePropertyChangeListener(listener);
        }
        
        fEvents.remove(event);
        firePropertyChange("event", null, (Object) fEvents);
        
        if (event.isTransitionEvent() && event.getNextState() != null) {
            event.getNextState().firePropertyChange(
                    "target", null, null);
        }
    }
    
    /**
     * イベント名を生成する.
     * イベント名は、以下の規則で生成される。<br>
     * "go" + 遷移先ステート名 + "From" + 遷移元ステート名 
     * 
     * @param nextStateName 遷移先ステート名
     * @return イベント名
     */
    public String generateEventName(String nextStateName) {
        if (nextStateName == null) {
            return null;
        }
        if (getName() == null) {
            return null;
        }
        
        String baseEventName = "go" + nextStateName + "From" + getName();
        String eventName = baseEventName;
        int eventSequenceNo = 2;
        while (!checkUsableEventName(eventName)) {
            eventName = baseEventName + Integer.toString(eventSequenceNo);
            eventSequenceNo++;
        }
        return eventName;
    }
    
    /**
     * 指定されたイベント名が使用可能かチェックする.
     * 
     * @param eventName イベント名
     * @return 使用可能なイベント名の場合はtrue
     */
    public boolean checkUsableEventName(String eventName) {
        if (eventName == null) {
            return false;
        }
        for (Event event : getEventList()) {
            if (event.getName() == null) {
                continue;
            }
            if (event.getName().equals(eventName)) {
                return false;
            }
        }
        return true;
    }
    
    
    /**
     * ステートのX座標を返す.
     * 
     * @return X座標
     */
    public int getX() {
        return fX;
    }
    
    /**
     * ステートのX座標を設定する.
     * 
     * @param x X座標
     */
    public void setX(int x) {
        int old = fX;
        fX = x;
        firePropertyChagen("x", old, fX);
    }
    
    /**
     * ステートのY座標を返す.
     * 
     * @return Y座標
     */
    public int getY() {
        return fY;
    }

    /**
     * ステートのY座標を設定する.
     * 
     * @param y Y座標
     */
    public void setY(int y) {
        int old = fY;
        fY = y;
        firePropertyChagen("y", old, fY);
    }
    
    /**
     * 自身が遷移先となる遷移リストを返す.
     * 
     * @return 自身が遷移先となる遷移リスト
     */
    public List getIncomings() {
        return fIncomings;
    }
    
    /**
     * 自身が遷移先となる遷移を追加する.
     * 
     * @param transition 自身が遷移先となる遷移 
     */
    public void addIncoming(Transition transition) {
        fIncomings.add(transition);
        firePropertyChange("incoming", null, null);
    }
    
    /**
     * 自身が遷移先となる遷移を削除する.
     * 
     * @param transition 自身が遷移先となる遷移 
     */
    public void removeIncoming(Transition transition) {
        fIncomings.remove(transition);
        firePropertyChange("incoming", null, null);
    }
    
    /**
     * 自身が遷移元となる遷移リストを返す.
     * 
     * @return 自身が遷移元となる遷移リスト
     */
    public List getOutgoings() {
        return fOutgoings;
    }
    
    /**
     * 自身が遷移元となる遷移を追加する.
     * 
     * @param transition 自身が遷移元となる遷移 
     */
    public void addOutgoing(Transition transition) {
        fOutgoings.add(transition);
        firePropertyChange("outgoing", null, null);
    }
    
    /**
     * 自身が遷移元となる遷移を削除する.
     * 
     * @param transition 自身が遷移元となる遷移 
     */
    public void removeOutgoing(Transition transition) {
        fOutgoings.remove(transition);
        firePropertyChange("outgoing", null, null);
    }

    /**
     * エディット可能なオブジェクトを返す.
     * 
     * @return 自身 
     * @see org.eclipse.ui.views.properties.IPropertySource#getEditableValue()
     */
    public Object getEditableValue() {
        return this;
    }
    
    /**
     * プロパティ・ディスクリプタ一覧を返す.
     * 
     * @return 編集可能なプロパティ・ディスクリプタ一覧
     * @see org.eclipse.ui.views.properties.IPropertySource
     *          #getPropertyDescriptors()
     */
    public IPropertyDescriptor[] getPropertyDescriptors() {
        List<IPropertyDescriptor> list = new ArrayList<IPropertyDescriptor>();
        
        list.add(new TextPropertyDescriptor("name", "ステート名"));
        if (fStateType == VIEW_STATE) {
            list.add(new TextPropertyDescriptor("view", "ビュー"));
        }
        list.add(new TextPropertyDescriptor("summary", "概要"));
        // TODO:Eventに対するプロパティ処理
        // list.add(new TextPropertyDescriptor("evnet", "イベント"));
        
        return list.toArray(new IPropertyDescriptor[0]);
    }

    /**
     * プロパティの値を返す.
     * 
     * @param id プロパティ名
     * @return プロパティ
     * @see org.eclipse.ui.views.properties.IPropertySource
     *          #getPropertyValue(java.lang.Object)
     */
    public Object getPropertyValue(Object id) {
        Object ret = null;
        
        if (id.equals("name")) {
            if (fName != null) {
                ret = fName;
            } else {
                ret = "";
            }
        }
        
        if (fStateType == VIEW_STATE && id.equals("view")) {
            if (fView != null) {
                ret = fView;
            } else {
                ret = "";
            }
        }
        
        if (id.equals("summary")) {
            if (fSummary != null) {
                ret = fSummary;
            } else {
                ret = "";
            }
        }
        // TODO:Eventに対するプロパティ処理
        
        return ret;
    }

    /**
     * プロパティの存在チェック.
     * 
     * @param id プロパティ名
     * @return チェック結果
     * @see org.eclipse.ui.views.properties.IPropertySource
     *          #isPropertySet(java.lang.Object)
     */
    public boolean isPropertySet(Object id) {
        // TODO:Eventに対するプロパティ処理
        return id.equals("name") 
                || (fStateType == VIEW_STATE && id.equals("view"))
                || id.equals("summary");
    }

    /**
     * プロパティの値をリセットする.
     * 
     * @param id プロパティ名
     * @see org.eclipse.ui.views.properties.IPropertySource
     *          #resetPropertyValue(java.lang.Object)
     */
    public void resetPropertyValue(Object id) {
    }

    /**
     * プロパティの値を設定する.
     * 
     * @param id プロパティ名
     * @param value プロパティ値
     * @see org.eclipse.ui.views.properties.IPropertySource
     *          #setPropertyValue(java.lang.Object, java.lang.Object)
     */
    public void setPropertyValue(Object id, Object value) {
        if (id.equals("name")) {
            setName(String.valueOf(value));
        }
        
        if (fStateType == VIEW_STATE && id.equals("view")) {
            setView(String.valueOf(value));
        }
        
        if (id.equals("summary")) {
            setSummary(String.valueOf(value));
        }
        // TODO:Eventに対するプロパティ処理
    }
    
}
