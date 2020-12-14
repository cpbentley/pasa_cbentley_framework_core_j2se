/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.framework.core.j2se.engine;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.framework.core.j2se.ctx.CoreFrameworkJ2seCtx;
import pasa.cbentley.framework.core.src4.ctx.ToStringStaticFrameworkCore;
import pasa.cbentley.framework.core.src4.engine.CoreHostAbstract;
import pasa.cbentley.framework.core.src4.interfaces.IAPIService;
import pasa.cbentley.framework.core.src4.interfaces.ITechDataHost;
import pasa.cbentley.framework.core.src4.interfaces.ITechFeaturesHost;
import pasa.cbentley.framework.core.src4.interfaces.ITechHost;
import pasa.cbentley.framework.coreui.src4.event.GestureArea;
import pasa.cbentley.framework.coreui.src4.tech.ITechCanvasHost;

public abstract class CoreJ2SEHost extends CoreHostAbstract implements ITechHost {

   public CoreJ2SEHost(CoreFrameworkJ2seCtx driver) {
      super(driver);
      techHost.set1(HOST_OFFSET_07_KEYBOARD_TYPE1, KB_TYPE_1_FULL);
   }

   /**
    * Most will use the Bentley framework values which correspond to a
    * 1200*800 screen
    */
   public int getHostInt(int dataID) {
      switch (dataID) {
         case ITechDataHost.DATA_ID_04_POINTER_DRAG_SLOP:
            return 50;
         case ITechDataHost.DATA_ID_02_POINTER_NUPLE_SLOP:
            return 50;
         case ITechDataHost.DATA_ID_09_SLIDE_MIN_AMPLITUDE:
            return 30;
         case ITechDataHost.DATA_ID_11_FLING_SPEED_MAX:
            return 500;
         case ITechDataHost.DATA_ID_12_FLING_SPEED_MIN:
            return 5;
         case ITechDataHost.DATA_ID_17_NUMBER_OF_SCREENS:
            return getNumOfScreens();
         case ITechDataHost.DATA_ID_18_SCREEN_ORIENTATION:
            //we only have one screen orientation in desktop
            return ITechCanvasHost.SCREEN_0_TOP_NORMAL;
         default:
            break;
      }
      return 0;
   }

   public int getNumOfScreens() {
      GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      GraphicsDevice[] screens = ge.getScreenDevices();
      int n = screens.length;
      return n;
   }

   public int getSlideMinAmp() {
      Toolkit tk = Toolkit.getDefaultToolkit();

      return 40;
   }

   public boolean hasFeatureSupport(int supportID) {
      switch (supportID) {
         case ITechFeaturesHost.SUP_ID_01_KEYBOARD:
            return true;
         case ITechFeaturesHost.SUP_ID_02_POINTERS:
            return true;
         case ITechFeaturesHost.SUP_ID_03_OPEN_GL:
            return true;
         case ITechFeaturesHost.SUP_ID_05_SCREEN_ROTATIONS:
            return false;
         case ITechFeaturesHost.SUP_ID_24_MULTIPLE_WINDOWS:
            return true;
         case ITechFeaturesHost.SUP_ID_37_JINPUT:
            //true if platform dependant api is available
            return cfc.getAPIService(ITechFeaturesHost.SUP_ID_37_JINPUT) != null;
         case ITechFeaturesHost.SUP_ID_38_GAMEPADS:
            //true if platform dependant api is available
            return cfc.getAPIService(ITechFeaturesHost.SUP_ID_38_GAMEPADS) != null;
         default:
            break;
      }
      return false;
   }

   public boolean enableFeature(int featureID, boolean b) {
      if (featureID == ITechFeaturesHost.SUP_ID_37_JINPUT) {
         IAPIService isa = cfc.getAPIService(ITechFeaturesHost.SUP_ID_37_JINPUT);
         if (isa != null) {
            if (b) {
               return isa.startService(0);
            } else {
               isa.stopService(0);
               return true;
            }
         }
      } else if (featureID == ITechFeaturesHost.SUP_ID_38_GAMEPADS) {
         return enableFeatureGamepads(b);
      }
      return false;
   }

   private boolean enableFeatureGamepads(boolean b) {
      IAPIService isa = cfc.getAPIService(ITechFeaturesHost.SUP_ID_38_GAMEPADS);
      if (isa != null) {
         if (b) {
            boolean isStarted = false;
            try {
               isStarted = isa.startService(0);
            } catch (Exception e) {
               e.printStackTrace();
            } catch (Error er) {
               er.printStackTrace();
            }
            //#debug
            toDLog().pBridge("isStarted=" + isStarted, isa, CoreJ2SEHost.class, "enableFeature " + ToStringStaticFrameworkCore.toStringFeature(ITechFeaturesHost.SUP_ID_38_GAMEPADS) + " " + b);
            return isStarted;
         } else {
            boolean isStopped = isa.stopService(0);
            //#debug
            toDLog().pBridge("isStopped=" + isStopped, isa, CoreJ2SEHost.class, "enableFeature " + ToStringStaticFrameworkCore.toStringFeature(ITechFeaturesHost.SUP_ID_38_GAMEPADS) + " " + b);
            return true;
         }
      }
      return false;
   }

   public boolean isFeatureEnabled(int featureID) {
      if (featureID == ITechFeaturesHost.SUP_ID_37_JINPUT) {
         IAPIService isa = cfc.getAPIService(ITechFeaturesHost.SUP_ID_37_JINPUT);
         if (isa != null) {
            return isa.isServiceRunning(0);
         }
      } else if (featureID == ITechFeaturesHost.SUP_ID_38_GAMEPADS) {
         //OS specific services for J2SE? they are set by launcher. only launchers knows which os the run on
         IAPIService isa = cfc.getAPIService(ITechFeaturesHost.SUP_ID_38_GAMEPADS);
         if (isa != null) {
            return isa.isServiceRunning(0);
         }
      }
      return false;
   }

   public float getHostFloat(int dataID) {
      // TODO Auto-generated method stub
      return 0;
   }

   public boolean enableFeatureFactory(int featureID, boolean b) {
      // TODO Auto-generated method stub
      return false;
   }

   public Object getHostObject(int id) {
      if (id == ITechDataHost.DATA_ID_OBJ_01_SCREENS) {
         GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
         GraphicsDevice[] gds = ge.getScreenDevices();
         GestureArea[] gas = new GestureArea[gds.length];
         for (int i = 0; i < gds.length; i++) {
            Rectangle r = gds[i].getDefaultConfiguration().getBounds();
            gas[i] = new GestureArea(r.x, r.y, r.width, r.height);
         }
         return gas;
      } else if (id == ITechDataHost.DATA_ID_OBJ_02_SCREENCONFIG) {
         return getScreenConfigLive();
      }
      return null;
   }

   /**
    * 
    */
   public String getHostString(int dataID) {
      switch (dataID) {
         case ITechDataHost.DATA_ID_STR_01_MANUFACTURER:
            return "";
         case ITechDataHost.DATA_ID_STR_02_IMEI:
            //depends
            return "";
         case ITechDataHost.DATA_ID_STR_03_DEVICE_MODEL:
            return System.getProperty("device.model");
         case ITechDataHost.DATA_ID_STR_04_PLATFORM:
            return "j2se";
         case ITechDataHost.DATA_ID_STR_05_HOSTNAME:
            return System.getProperty("device.model");
         case ITechDataHost.DATA_ID_STR_06_ENCODING:
            return System.getProperty("file.encoding");
         default:
            break;
      }
      return "";
   }

}
