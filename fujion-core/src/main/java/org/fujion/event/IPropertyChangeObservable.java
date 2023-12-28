package org.fujion.event;

import java.beans.PropertyChangeListener;

/**
 * Classes that can listen for property change events should implement this.
 */
public interface IPropertyChangeObservable {

    void addPropertyChangeListener(PropertyChangeListener pcl);

    void removePropertyChangeListener(PropertyChangeListener pcl);

}
