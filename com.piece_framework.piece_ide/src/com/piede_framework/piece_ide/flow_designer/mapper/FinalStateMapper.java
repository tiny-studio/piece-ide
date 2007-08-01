package com.piede_framework.piece_ide.flow_designer.mapper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.piece_framework.piece_ide.flow_designer.model.State;

/**
 * ファイナルステートマッパークラス.
 * 
 * @author MATSUFUJI Hideharu
 * @version 0.1.0
 * @since 0.1.0
 * 
 */
public class FinalStateMapper extends AbstractStateMapper {

    /**
     * ファイナルステートを返す.
     * ファイナルステートはフロー内にひとつしかないはずなので、
     * ひとつ見つかった時点で処理は終了する。
     * 
     * @return ファイナルステートを含むリスト
     * @see com.piede_framework.piece_ide.flow_designer.mapper
     *          .AbstractStateMapper#getStateList()
     */
    @Override
    protected List<State> getStateList() {
        List<State> stateList = new ArrayList<State>();
        for (State state : getFlow().getStateList()) {
            if (state.getType() == State.FINAL_STATE) {
                stateList.add(state);
                break;
            }
        }
        return stateList;
    }
    
    /**
     * ファイナルステートに遷移しているステートをMapオブジェクト
     * にセットして返す.
     * 
     * @param stateList ファイナルステートを含むリスト
     * @return YAML出力のためのMapオブジェクト
     * @see com.piede_framework.piece_ide.flow_designer.mapper
     *          .AbstractStateMapper#getMapForYAML(java.util.List)
     */
    @Override
    protected Map<String, Object> getMapForYAML(List<State> stateList) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        if (stateList.size() != 1) {
            return map;
        }
        
        State finalState = stateList.get(0);
        List<Map> stateListForYaml = new ArrayList<Map>();
        for (State state : getFlow().getStateListToOwnState(finalState)) {
            Map<String, Object> stateMap = new LinkedHashMap<String, Object>();
            addStateInformationToMap(state, stateMap);
            addBuiltinEventToMap(state, stateMap);
            addTransitionAndInternalEventToMap(state, stateMap);
            
            stateListForYaml.add(stateMap);
        }
        
        if (stateListForYaml.size() > 1) {
            map.put("lastState", stateListForYaml);
        } else if (stateListForYaml.size() == 1) {
            Map stateMap = (Map) stateListForYaml.get(0);
            map.put("lastState", stateMap);
        }

        return map;
    }
}
