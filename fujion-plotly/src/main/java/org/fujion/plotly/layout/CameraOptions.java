package org.fujion.plotly.layout;

import org.fujion.ancillary.Options;

/**
 * Layout options for camera.
 */
public class CameraOptions extends Options {
    
    /**
     * The x component of the 'center' camera vector This vector determines the translation (x,y,z)
     * space about the center of this scene.
     * <p>
     * Default: 0
     */
    public Integer center_x;
    
    /**
     * The y component of the 'center' camera vector This vector determines the translation (x,y,z)
     * space about the center of this scene.
     * <p>
     * Default: 0
     */
    public Integer center_y;
    
    /**
     * The z component of the 'center' camera vector This vector determines the translation (x,y,z)
     * space about the center of this scene.
     * <p>
     * Default: 0
     */
    public Integer center_z;

    /**
     * The horizontal domain of this scene subplot (in plot fraction).
     * <p>
     * Default: [0,1]
     */
    public int[] domain_x;
    
    /**
     * The vertical domain of this scene subplot (in plot fraction).
     * <p>
     * Default: [0,1]
     */
    public int[] domain_y;
    
    /**
     * The x component of the 'eye' camera vector. This vector determines the view point about the
     * origin of this scene.
     * <p>
     * Default: 1.25
     */
    public Double eye_x;
    
    /**
     * The y component of the 'eye' camera vector. This vector determines the view point about the
     * origin of this scene.
     * <p>
     * Default: 1.25
     */
    public Double eye_y;
    
    /**
     * The z component of the 'eye' camera vector. This vector determines the view point about the
     * origin of this scene.
     * <p>
     * Default: 1.25
     */
    public Double eye_z;
    
    /**
     * The x component of the 'up' camera vector. This vector determines the up direction of this
     * scene with respect to the page.
     * <p>
     * Default: 0
     */
    public Integer up_x;
    
    /**
     * The y component of the 'up' camera vector. This vector determines the up direction of this
     * scene with respect to the page.
     * <p>
     * Default: 0
     */
    public Integer up_y;
    
    /**
     * The z component of the 'up' camera vector. This vector determines the up direction of this
     * scene with respect to the page.
     * <p>
     * Default: 1
     */
    public Integer up_z;
    
}
