/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.framework.core.j2se.engine;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;

import pasa.cbentley.core.src4.interfaces.IAPIService;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.core.j2se.ctx.CoreFrameworkJ2seCtx;
import pasa.cbentley.framework.core.src4.ctx.ToStringStaticCoreFramework;
import pasa.cbentley.framework.core.src4.engine.CoreHostAbstract;
import pasa.cbentley.framework.core.src4.interfaces.ITechDataHost;
import pasa.cbentley.framework.core.src4.interfaces.ITechHostCore;
import pasa.cbentley.framework.coreui.src4.event.GestureArea;
import pasa.cbentley.framework.coreui.src4.interfaces.IHostUI;
import pasa.cbentley.framework.coreui.src4.tech.IBOCanvasHost;

/**
 * Compared to {@link CoreHostAbstract} we provide concrete information about the host capabilities.
 * 
 * <li> {@link HostCoreJ2SE#getNumOfScreens()}
 * 
 * 
 * @author Charles Bentley
 *
 */
public abstract class HostCoreJ2SE extends CoreHostAbstract implements ITechHostCore {

   protected final CoreFrameworkJ2seCtx cfcj2se;

   public HostCoreJ2SE(CoreFrameworkJ2seCtx cfcj2se) {
      super(cfcj2se);
      this.cfcj2se = cfcj2se;
   }

   public IHostUI getHostUI() {
      return cfcj2se.getCUC().getHostUI();
   }
   /**
    * Most will use the Bentley framework values which correspond to a
    * 1200*800 screen
    */
   public int getHostInt(int dataID) {
      int uiint = getHostUI().getHostInt(dataID);
      if(uiint != Integer.MIN_VALUE) {
         return uiint;
      }
      switch (dataID) {
         default:
            break;
      }
      return Integer.MIN_VALUE;
   }

   public boolean hasFeatureSupport(int supportID) {
      boolean hasSupport = getHostUI().hasFeatureSupport(supportID);
      if(hasSupport) {
         return true;
      } else {
         switch (supportID) {
            default:
               break;
         }
      }
      return false;
   }

   public boolean enableFeature(int featureID, boolean b) {
      boolean hasSupport = getHostUI().enableFeature(featureID,b);
      if(hasSupport) {
         return true;
      } else {
         switch (featureID) {
            default:
               break;
         }
      }
      return false;
   }

   public boolean isFeatureEnabled(int featureID) {
      boolean hasSupport = getHostUI().isFeatureEnabled(featureID);
      if(hasSupport) {
         return true;
      } else {
         switch (featureID) {
            default:
               break;
         }
      }
      return false;
   }

   public float getHostFloat(int dataID) {
      float uiint = getHostUI().getHostFloat(dataID);
      if(uiint != Float.MIN_VALUE) {
         return uiint;
      }
      switch (dataID) {
         default:
            break;
      }
      return Float.MIN_VALUE;
   }

   public boolean enableFeatureFactory(int featureID, boolean b) {
      boolean hasSupport = getHostUI().enableFeatureFactory(featureID,b);
      if(hasSupport) {
         return true;
      } else {
         switch (featureID) {
            default:
               break;
         }
      }
      return false;
   }

   public Object getHostObject(int id) {
      Object uiint = getHostUI().getHostFloat(id);
      if(uiint != null) {
         return uiint;
      }
      switch (id) {
         default:
            break;
      }
      return null;
   }

   /**
    * 
    */
   public String getHostString(int dataID) {
      String uiint = getHostUI().getHostString(dataID);
      if(uiint != null) {
         return uiint;
      }
      switch (dataID) {
         default:
            break;
      }
      return null;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, HostCoreJ2SE.class, 213);
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, HostCoreJ2SE.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug

}
