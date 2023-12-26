package org.fujion.event;

import java.beans.PropertyChangeListener;

/**
 * Classes that can listen for property change events should implement this.
 */
public interface IPropertyChangeObservable {

    public void addPropertyChangeListener(PropertyChangeListener pcl);

    public void removePropertyChangeListener(PropertyChangeListener pcl);

}
