/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package com.archimatetool.editor.ui;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.graphics.Image;

import com.archimatetool.editor.ui.factory.IDiagramModelUIProvider;
import com.archimatetool.editor.ui.factory.IObjectUIProvider;
import com.archimatetool.editor.ui.factory.ObjectUIFactory;
import com.archimatetool.editor.utils.StringUtils;
import com.archimatetool.model.IArchimatePackage;
import com.archimatetool.model.IArchimateRelationship;
import com.archimatetool.model.IDiagramModel;
import com.archimatetool.model.IDiagramModelArchimateComponent;
import com.archimatetool.model.INameable;



/**
 * Main Label Provider for Archi
 * 
 * @author Phillip Beauvoir
 */
public class ArchiLabelProvider {
    
    public static ArchiLabelProvider INSTANCE = new ArchiLabelProvider();

    /**
     * @return A name for an object.<br>
     *         This will be the element's name if of type INameable or a default name as specified in its ElementUIProvider.
     */
    public String getLabel(Object object) {
        if(object == null) {
            return ""; //$NON-NLS-1$
        }
        
        object = getWrappedElement(object);
        
        String name = null;
        
        // Get Name
        if(object instanceof INameable) {
            name = ((INameable)object).getName();
        }
        
        // It's blank. Get a default name from its eClass
        if(!StringUtils.isSet(name) && object instanceof EObject) {
            name = getDefaultName(((EObject)object).eClass());
        }
        
        return StringUtils.safeString(name);
    }

    /**
     * Get a default human-readable name for an EClass
     * @param eClass The Class
     * @return A name or null
     */
    public String getDefaultName(EClass eClass) {
        if(eClass == null) {
            return ""; //$NON-NLS-1$
        }
        
        IObjectUIProvider provider = ObjectUIFactory.INSTANCE.getProviderForClass(eClass);
        if(provider != null) {
            return provider.getDefaultName();
        }
        
        return ""; //$NON-NLS-1$
    }
    
    /**
     * @param element
     * @return An image for an object
     */
    public Image getImage(Object object) {
        if(object == null) {
            return null;
        }
        
        object = getWrappedElement(object);
        
        // This first, since EClass is an EObject
        if(object instanceof EClass) {
            IObjectUIProvider provider = ObjectUIFactory.INSTANCE.getProviderForClass((EClass)object);
            if(provider != null) {
                return provider.getImage();
            }
        }
        else if(object instanceof EObject) {
            IObjectUIProvider provider = ObjectUIFactory.INSTANCE.getProvider((EObject)object);
            if(provider != null) {
                return provider.getImage();
            }
        }
        
        return null;
    }
    
    /**
     * @param eClass
     * @return An ImageDescriptor for an EClass
     */
    public ImageDescriptor getImageDescriptor(EClass eClass) {
        if(eClass == null) {
            return null;
        }
        
        IObjectUIProvider provider = ObjectUIFactory.INSTANCE.getProviderForClass(eClass);
        if(provider != null) {
            return provider.getImageDescriptor();
        }
        
        return null;
    }
    
    /**
     * @param eClass
     * @return An ImageDescriptor for an EClass
     */
    public ImageDescriptor getImageDescriptor2x(EClass eClass) {
        if(eClass == null) {
            return null;
        }
        
        IObjectUIProvider provider = ObjectUIFactory.INSTANCE.getProviderForClass(eClass);
        if(provider != null) {
            return provider.getImageDescriptor2x();
        }
        
        return null;
    }
    
    /**
     * @param element
     * @return A IGraphicsIcon for a Diagram Model
     */
    public IGraphicsIcon getGraphicsIconForDiagramModel(IDiagramModel dm) {
        IObjectUIProvider provider = ObjectUIFactory.INSTANCE.getProvider(dm);
        if(provider instanceof IDiagramModelUIProvider) {
            return ((IDiagramModelUIProvider)provider).getGraphicsIcon();
        }
        
        return null;
    }
    
    /**
     * If the wrapper is an EditPart get the EditPart's model first
     * If then the wrapper is an IDiagramModelArchimateObject return the IArchimateElement
     * If then the wrapper is an IDiagramModelArchimateConnection return the IRelationship
     * Else return the object itself
     * @param object The wrapper objects
     * @return The actual model element in an object
     */
    public Object getWrappedElement(Object object) {
        if(object instanceof EditPart) {
            object = ((EditPart)object).getModel();
        }
        
        if(object instanceof IDiagramModelArchimateComponent) {
            return ((IDiagramModelArchimateComponent)object).getArchimateConcept();
        }
        
        return object;
    }
    
    /**
     * @param relation
     * @return A sentence that describes the relationship between the relation's source and target elements
     */
    public String getRelationshipSentence(IArchimateRelationship relation) {
        if(relation != null) {
            if(relation.getSource() != null && relation.getTarget() != null) {
                String nameSource = ArchiLabelProvider.INSTANCE.getLabel(relation.getSource());
                String nameTarget = ArchiLabelProvider.INSTANCE.getLabel(relation.getTarget());
                
                switch(relation.eClass().getClassifierID()) {
                    case IArchimatePackage.SPECIALIZATION_RELATIONSHIP:
                        return NLS.bind(Messages.ArchimateLabelProvider_3, nameSource, nameTarget);

                    case IArchimatePackage.COMPOSITION_RELATIONSHIP:
                        return NLS.bind(Messages.ArchimateLabelProvider_4, nameSource, nameTarget);

                    case IArchimatePackage.AGGREGATION_RELATIONSHIP:
                        return NLS.bind(Messages.ArchimateLabelProvider_5, nameSource, nameTarget);

                    case IArchimatePackage.TRIGGERING_RELATIONSHIP:
                        return NLS.bind(Messages.ArchimateLabelProvider_6, nameSource, nameTarget);

                    case IArchimatePackage.FLOW_RELATIONSHIP:
                        return NLS.bind(Messages.ArchimateLabelProvider_7, nameSource, nameTarget);

                    case IArchimatePackage.ACCESS_RELATIONSHIP:
                        return NLS.bind(Messages.ArchimateLabelProvider_8, nameSource, nameTarget);

                    case IArchimatePackage.ASSOCIATION_RELATIONSHIP:
                        return NLS.bind(Messages.ArchimateLabelProvider_9, nameSource, nameTarget);

                    case IArchimatePackage.ASSIGNMENT_RELATIONSHIP:
                        return NLS.bind(Messages.ArchimateLabelProvider_10, nameSource, nameTarget);

                    case IArchimatePackage.REALIZATION_RELATIONSHIP:
                        return NLS.bind(Messages.ArchimateLabelProvider_11, nameSource, nameTarget);

                    case IArchimatePackage.SERVING_RELATIONSHIP:
                        return NLS.bind(Messages.ArchimateLabelProvider_12, nameSource, nameTarget);

                    case IArchimatePackage.INFLUENCE_RELATIONSHIP:
                        return NLS.bind(Messages.ArchimateLabelProvider_13, nameSource, nameTarget);

                    default:
                        return ""; //$NON-NLS-1$
                }
            }
        }
        
        return ""; //$NON-NLS-1$
    }

    /**
     * @param eClass The Relationship class
     * @param reverseDirection If this is true then the phrase is from target to source
     * @return A phrase that describes the relationship, for example "Is Realised by", "Flows to"
     */
    public String getRelationshipPhrase(EClass eClass, boolean reverseDirection) {
        if(eClass == null) {
            return ""; //$NON-NLS-1$
        }
        
        switch(eClass.getClassifierID()) {
            case IArchimatePackage.SPECIALIZATION_RELATIONSHIP:
                return reverseDirection ? Messages.ArchimateLabelProvider_14 : Messages.ArchimateLabelProvider_15;

            case IArchimatePackage.COMPOSITION_RELATIONSHIP:
                return reverseDirection ? Messages.ArchimateLabelProvider_16 : Messages.ArchimateLabelProvider_17;

            case IArchimatePackage.AGGREGATION_RELATIONSHIP:
                return reverseDirection ? Messages.ArchimateLabelProvider_18 : Messages.ArchimateLabelProvider_19;

            case IArchimatePackage.TRIGGERING_RELATIONSHIP:
                return reverseDirection ? Messages.ArchimateLabelProvider_20 : Messages.ArchimateLabelProvider_21;

            case IArchimatePackage.FLOW_RELATIONSHIP:
                return reverseDirection ? Messages.ArchimateLabelProvider_22 : Messages.ArchimateLabelProvider_23;

            case IArchimatePackage.ACCESS_RELATIONSHIP:
                return reverseDirection ? Messages.ArchimateLabelProvider_24 : Messages.ArchimateLabelProvider_25;

            case IArchimatePackage.ASSOCIATION_RELATIONSHIP:
                return reverseDirection ? Messages.ArchimateLabelProvider_26 : Messages.ArchimateLabelProvider_27;

            case IArchimatePackage.ASSIGNMENT_RELATIONSHIP:
                return reverseDirection ? Messages.ArchimateLabelProvider_28 : Messages.ArchimateLabelProvider_29;

            case IArchimatePackage.REALIZATION_RELATIONSHIP:
                return reverseDirection ? Messages.ArchimateLabelProvider_30 : Messages.ArchimateLabelProvider_31;

            case IArchimatePackage.SERVING_RELATIONSHIP:
                return reverseDirection ? Messages.ArchimateLabelProvider_32 : Messages.ArchimateLabelProvider_33;

            case IArchimatePackage.INFLUENCE_RELATIONSHIP:
                return reverseDirection ? Messages.ArchimateLabelProvider_34 : Messages.ArchimateLabelProvider_35;

            default:
                return ""; //$NON-NLS-1$
        }
    }
}
