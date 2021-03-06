package FlowDesigner.diagram.navigator;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class FlowDesignerNavigatorItem extends
        FlowDesigner.diagram.navigator.FlowDesignerAbstractNavigatorItem {

    /**
     * @generated
     */
    static {
        final Class[] supportedTypes = new Class[] { View.class, EObject.class };
        Platform.getAdapterManager().registerAdapters(new IAdapterFactory() {

            public Object getAdapter(Object adaptableObject, Class adapterType) {
                if (adaptableObject instanceof FlowDesigner.diagram.navigator.FlowDesignerNavigatorItem
                        && (adapterType == View.class || adapterType == EObject.class)) {
                    return ((FlowDesigner.diagram.navigator.FlowDesignerNavigatorItem) adaptableObject)
                            .getView();
                }
                return null;
            }

            public Class[] getAdapterList() {
                return supportedTypes;
            }
        }, FlowDesigner.diagram.navigator.FlowDesignerNavigatorItem.class);
    }

    /**
     * @generated
     */
    private View myView;

    /**
     * @generated
     */
    private boolean myLeaf = false;

    /**
     * @generated
     */
    public FlowDesignerNavigatorItem(View view, Object parent, boolean isLeaf) {
        super(parent);
        myView = view;
        myLeaf = isLeaf;
    }

    /**
     * @generated
     */
    public View getView() {
        return myView;
    }

    /**
     * @generated
     */
    public boolean isLeaf() {
        return myLeaf;
    }

    /**
     * @generated
     */
    public boolean equals(Object obj) {
        if (obj instanceof FlowDesigner.diagram.navigator.FlowDesignerNavigatorItem) {
            return EcoreUtil
                    .getURI(getView())
                    .equals(
                            EcoreUtil
                                    .getURI(((FlowDesigner.diagram.navigator.FlowDesignerNavigatorItem) obj)
                                            .getView()));
        }
        return super.equals(obj);
    }

    /**
     * @generated
     */
    public int hashCode() {
        return EcoreUtil.getURI(getView()).hashCode();
    }

}
