package FlowDesigner.diagram.edit.parts;

import org.eclipse.draw2d.Connection;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ConnectionNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITreeBranchEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.draw2d.ui.figures.PolylineConnectionEx;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class EventEditPart extends ConnectionNodeEditPart implements
        ITreeBranchEditPart {

    /**
     * @generated
     */
    public static final int VISUAL_ID = 4003;

    /**
     * @generated
     */
    public EventEditPart(View view) {
        super(view);
    }

    /**
     * @generated
     */
    protected void createDefaultEditPolicies() {
        super.createDefaultEditPolicies();
        installEditPolicy(
                EditPolicyRoles.SEMANTIC_ROLE,
                new FlowDesigner.diagram.edit.policies.EventItemSemanticEditPolicy());
    }

    /**
     * @generated
     */
    protected boolean addFixedChild(EditPart childEditPart) {
        if (childEditPart instanceof FlowDesigner.diagram.edit.parts.EventNameEditPart) {
            ((FlowDesigner.diagram.edit.parts.EventNameEditPart) childEditPart)
                    .setLabel(getPrimaryShape().getFigureEventNameFigure());
            return true;
        }
        return false;
    }

    /**
     * @generated
     */
    protected void addChildVisual(EditPart childEditPart, int index) {
        if (addFixedChild(childEditPart)) {
            return;
        }
        super.addChildVisual(childEditPart, -1);
    }

    /**
     * Creates figure for this edit part.
     * 
     * Body of this method does not depend on settings in generation model
     * so you may safely remove <i>generated</i> tag and modify it.
     * 
     * @generated
     */

    protected Connection createConnectionFigure() {
        return new EventFigure();
    }

    /**
     * @generated
     */
    public EventFigure getPrimaryShape() {
        return (EventFigure) getFigure();
    }

    /**
     * @generated
     */
    public class EventFigure extends PolylineConnectionEx {

        /**
         * @generated
         */
        private WrappingLabel fFigureEventNameFigure;

        /**
         * @generated
         */
        public EventFigure() {

            createContents();
        }

        /**
         * @generated
         */
        private void createContents() {

            fFigureEventNameFigure = new WrappingLabel();
            fFigureEventNameFigure.setText("<...>");

            this.add(fFigureEventNameFigure);

        }

        /**
         * @generated
         */
        public WrappingLabel getFigureEventNameFigure() {
            return fFigureEventNameFigure;
        }

    }

}
