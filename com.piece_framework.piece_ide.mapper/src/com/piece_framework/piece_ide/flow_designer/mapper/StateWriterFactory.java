package com.piece_framework.piece_ide.flow_designer.mapper;


import com.piece_framework.piece_ide.flow_designer.model.State;

public class StateWriterFactory {
    
    public IStateWriter getWriter(State state) {
        
        if (state==null) {
            return null;
        }
        
        switch (state.getStateType()) {
            case State.INITIAL_STATE:
                return new InitialStateWriter();                
            case State.FINAL_STATE:
                return new FinalStateWriter();
            case State.ACTION_STATE:
                return new ActionStateWriter();
            case State.VIEW_STATE:
                return new ViewStateWriter();
            default :
                break;
        }
        
        return null;
    }
    

}
