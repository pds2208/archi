/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package com.archimatetool.editor.ui.factory.elements;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

import com.archimatetool.editor.diagram.editparts.ArchimateElementEditPart;
import com.archimatetool.editor.diagram.figures.elements.ConstraintFigure;
import com.archimatetool.editor.ui.ColorFactory;
import com.archimatetool.editor.ui.IArchiImages;
import com.archimatetool.model.IArchimatePackage;



/**
 * Constraint UI Provider
 * 
 * @author Phillip Beauvoir
 */
public class ConstraintUIProvider extends AbstractArchimateElementUIProvider {

    public EClass providerFor() {
        return IArchimatePackage.eINSTANCE.getConstraint();
    }
    
    @Override
    public EditPart createEditPart() {
        return new ArchimateElementEditPart(ConstraintFigure.class);
    }

    @Override
    public String getDefaultName() {
        return Messages.ConstraintUIProvider_0;
    }

    @Override
    public Image getImage() {
        return getImageWithUserFillColor(IArchiImages.ICON_CONSTRAINT);
    }

    @Override
    public ImageDescriptor getImageDescriptor() {
        return getImageDescriptorWithUserFillColor(IArchiImages.ICON_CONSTRAINT);
    }
    
    @Override
    public Color getDefaultColor() {
        return ColorFactory.get(204, 204, 255);
    }
    
    @Override
    public boolean hasAlternateFigure() {
        return true;
    }

}
