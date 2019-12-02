package pasa.cbentley.framework.core.j2se.engine;

import java.awt.event.KeyEvent;

import pasa.cbentley.bridge.j2se.ctx.BJ2SECtx;
import pasa.cbentley.bridge.j2se.ctx.J2SECanvasCtx;
import pasa.cbentley.core.src4.api.app.IAppli;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.core.j2se.ctx.CoreJ2seCtx;
import pasa.cbentley.framework.src4.host.tech.ITechHost;
import pasa.cbentley.framework.ui.ctx.CanvasCtx;
import pasa.cbentley.framework.ui.event.SenseEvent;
import pasa.cbentley.framework.ui.event.VoiceEvent;
import pasa.cbentley.framework.ui.interfaces.ISenses;
import pasa.cbentley.framework.ui.template.CanvasAbstract;

public abstract class CanvasJ2SE extends CanvasAbstract {

   private float               light     = 0.5f;

   /**
    * Modifies it to simulate several pointers
    */
   protected int               pointerID = 0;

   protected J2SECanvasWrapper wrapper;


   protected J2SECanvasCtx     jcc;

   public CanvasJ2SE(CoreJ2seCtx j2seCtx) {
      super(j2seCtx);
      this.jcc = j2seCtx;
   }

   @Override
   public boolean isCanvasFeatureEnabled(int feature) {
      if (feature == ITechHost.SUP_ID_04_ALIAS) {
         return true;
      }
      //ask the canvas implementation if the feature is supported
      return jcc.getHOC().getHost().hasFeatureSupport(feature);
   }

   /**
    * 
    * @param scw
    */
   public void setCanvasToWrapper(J2SECanvasWrapper scw) {
      wrapper = scw;
   }

   public void canvasShow() {
      wrapper.canvasShow();
   }

   public void canvasHide() {
      wrapper.canvasHide();
   }

   /**
    * Deals wih 
    * <li> {@link ITechHost#SUP_ID_27_FULLSCREEN}
    */
   public boolean isCanvasFeatureSupported(int feature) {
      if (feature == ITechHost.SUP_ID_27_FULLSCREEN) {
         return true;
      }
      return false;
   }

   public abstract void onAppModuleChange(IAppli app);

   @Override
   public boolean setCanvasFeature(int feature, boolean mode) {
      if (feature == ITechHost.SUP_ID_27_FULLSCREEN) {
         setFullScreenMode(mode);
         return true;
      } else if (feature == ITechHost.SUP_ID_04_ALIAS) {
         toggleAlias();
         return true;
      }
      return false;
   }

   protected abstract void setFullScreenMode(boolean mode);

   private void simulatePointer(int keyCode) {
      switch (keyCode) {
         case KeyEvent.VK_F1:
            pointerID = 0;
            break;
         case KeyEvent.VK_F2:
            pointerID = 1;
            break;
         case KeyEvent.VK_F3:
            pointerID = 2;
            break;
         case KeyEvent.VK_F4:
            pointerID = 3;
            break;
         case KeyEvent.VK_F5:
            pointerID = 4;
            break;
         default:
            break;
      }
   }

   private void simulateWord() {

   }

   //simulate pointer
   protected void simulationKeys(int bentleyKeyCode) {
      simulatePointer(bentleyKeyCode);

      final CanvasCtx cac = jcc;
      if (bentleyKeyCode == KeyEvent.VK_F12) {
         //simulate shake
         cac.runGUI(new Runnable() {
            public void run() {
               SenseEvent ge = new SenseEvent(cac, ISenses.GESTURE_TYPE_05_SHAKE);
               eventBridge(ge);
            }
         });
      } else if (bentleyKeyCode == KeyEvent.VK_F11) {
         cac.runGUI(new Runnable() {
            public void run() {
               VoiceEvent ge = new VoiceEvent(cac);
               ge.setMatches(new String[] { "SimulatedWord1", "SimulatedWord2" });
               eventBridge(ge);
            }
         });
      } else if (bentleyKeyCode == KeyEvent.VK_F10) {
         cac.runGUI(new Runnable() {
            public void run() {
               SenseEvent ge = new SenseEvent(cac, ISenses.GESTURE_TYPE_08_LIGHT);
               light += 0.1f;
               if (light > 1) {
                  light = 0;
               }
               ge.setValue(light);
               eventBridge(ge);
            }
         });
      } else if (bentleyKeyCode == KeyEvent.VK_F9) {
         cac.runGUI(new Runnable() {
            public void run() {
               SenseEvent ge = new SenseEvent(cac, ISenses.GESTURE_TYPE_07_MOVE);
               ge.setValues(new float[] { 2, 5, 6 });
               eventBridge(ge);
            }
         });
      }
   }

}
