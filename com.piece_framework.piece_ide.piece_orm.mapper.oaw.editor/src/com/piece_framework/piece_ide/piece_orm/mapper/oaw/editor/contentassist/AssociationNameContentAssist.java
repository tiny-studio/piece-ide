// $Id$
package com.piece_framework.piece_ide.piece_orm.mapper.oaw.editor.contentassist;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.IEditorPart;
import org.openarchitectureware.xtext.editor.AbstractXtextEditor;
import org.openarchitectureware.xtext.editor.contentassist.codeassist.Proposal;
import org.openarchitectureware.xtext.editor.contentassist.codeassist.impl.CodeassistFactoryImpl;
import org.openarchitectureware.xtext.parser.parsetree.Node;

import com.piece_framework.piece_ide.piece_orm.mapper.oaw.Piece_ORM_MapperEditorPlugin;

/**
 * アソシエーション名補完クラス.
 *
 * @author MATSUFUJI Hideharu
 * @since 0.1.0
 *
 */
public class AssociationNameContentAssist {
    public static List<Proposal> getProposalList() {
        Node root = getRootNode();
        if (root == null) {
            return null;
        }

        List<Proposal> proposals = new ArrayList<Proposal>();
        for (String associationName : getAssociationNames(root)) {
            proposals.add(createProposal(associationName));
        }

        return proposals;
    }

    private static Node getRootNode() {
        IEditorPart editor = Piece_ORM_MapperEditorPlugin.getDefault()
                                   .getWorkbench()
                                   .getActiveWorkbenchWindow()
                                   .getActivePage()
                                   .getActiveEditor();
        if (!(editor instanceof AbstractXtextEditor)) {
            return null;
        }

        return ((AbstractXtextEditor) editor).getRootNode();
    }

    private static List<String> getAssociationNames(Node root) {
        List<String> associationNames = new ArrayList<String>();
        for (Object childNode : root.getChildren()) {
            EObject model = ((Node) childNode).getModelElement();
            if (model == null) {
                continue;
            }
            if (!model.eClass().getName().equals("Association")) {
                continue;
            }

            String associationName = null;
            for (EAttribute attribute : model.eClass().getEAllAttributes()) {
                if (attribute.getName().equals("name")) {
                    associationName = (String) model.eGet(attribute);
                    break;
                }
            }
            if (associationName != null) {
                associationNames.add(associationName);
            }
        }

        return associationNames;
    }

    private static Proposal createProposal(String associationName) {
        Proposal proposal = CodeassistFactoryImpl.init().createProposal();
        proposal.setLabel(associationName);
        proposal.setToInsert(associationName);
        proposal.setImage("association.gif");
        return proposal;
    }
}
