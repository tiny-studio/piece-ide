// $Id: CreateEventCommand.java 180 2007-07-27 00:57:08Z matsufuji $
package com.piece_framework.piece_ide.flow_designer.command;

import org.eclipse.gef.commands.Command;

import com.piece_framework.piece_ide.flow_designer.model.Event;
import com.piece_framework.piece_ide.flow_designer.model.State;

/**
 * イベント作成コマンド.
 * 
 * @author MATSUFUJI Hideharu
 * @version 0.1.0
 * @since 0.1.0
 *
 */
public class CreateEventCommand extends Command {

    private State fState;
    private State fNextState;
    private Event fEvent;
    
    /**
     * コンストラクタ.
     * 
     * @param state ステート
     * @param nextState 遷移先ステート
     * @param event イベント
     */
    public CreateEventCommand(State state, State nextState, Event event) {
        super();
        fState = state;
        fNextState = nextState;
        fEvent = event;
    }

    /**
     * イベントを作成する.
     * 
     * @see org.eclipse.gef.commands.Command#execute()
     */
    @Override
    public void execute() {
        fEvent.setName(fState.generateEventName(fNextState.getName()));
        fEvent.setNextState(fNextState);
        fEvent.setEventHandler(
                null, 
                fEvent.generateEventHandlerMethodName());
        
        fState.addEvent(fEvent);
    }

    /**
     * 作成したイベントを元に戻す.
     * 
     * @see org.eclipse.gef.commands.Command#undo()
     */
    @Override
    public void undo() {
        fState.removeEvent(fEvent);
    }
}
