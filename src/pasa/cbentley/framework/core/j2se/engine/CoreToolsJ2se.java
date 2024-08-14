/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.framework.core.j2se.engine;

import java.awt.AWTException;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;

import pasa.cbentley.framework.core.framework.src4.engine.CoreToolsAbstract;
import pasa.cbentley.framework.core.j2se.ctx.CoreFrameworkJ2seCtx;

public abstract class CoreToolsJ2se extends CoreToolsAbstract {

   protected final CoreFrameworkJ2seCtx cjc;

   public CoreToolsJ2se(CoreFrameworkJ2seCtx cjc) {
      super(cjc);
      this.cjc = cjc;
   }


   public void moveMouse(Point p) {
      GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      GraphicsDevice[] gs = ge.getScreenDevices();

      // Search the devices for the one that draws the specified point.
      for (GraphicsDevice device : gs) {
         GraphicsConfiguration[] configurations = device.getConfigurations();
         for (GraphicsConfiguration config : configurations) {
            Rectangle bounds = config.getBounds();
            if (bounds.contains(p)) {
               // Set point to screen coordinates.
               Point b = bounds.getLocation();
               Point s = new Point(p.x - b.x, p.y - b.y);

               try {
                  Robot r = new Robot(device);
                  r.mouseMove(s.x, s.y);
               } catch (AWTException e) {
                  e.printStackTrace();
               }

               return;
            }
         }
      }
      // Couldn't move to the point, it may be off screen.
      return;
   }

}
