/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.framework.core.j2se.engine;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.framework.core.framework.src4.app.IAppli;
import pasa.cbentley.framework.core.ui.src4.interfaces.ICanvasAppli;

/**
 * @author Charles-Philip
 *
 */
public interface IAppliState extends IStringable {


   /**
    * Returns the default {@link ICanvasAppli} of this {@link IAppli}.
    * <br>
    * The Default Canvas in case previous state loading does not show anything
    * <br>
    * The Manager calls this when no Canvas was shown on the display
    * Th
    * The Canvas to be loaded first by the AppModule display. 
    * <br>
    * Call this method after the {@link IAppli#amsAppStart()} has been called.
    * <br>
    * @return {@link ICanvasAppli}. maybe null in a headless application module
    */
   public ICanvasAppli getCanvas();

   /**
    * Create the App canvas for the id with the tech
    * @param id
    * @param tech
    * @return
    */
   public ICanvasAppli getCanvas(int id, ByteObject tech);


}
