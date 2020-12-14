/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.framework.core.j2se.engine;

import java.awt.Dimension;
import java.awt.Toolkit;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.byteobjects.src4.ctx.IBOTypesBOC;
import pasa.cbentley.byteobjects.src4.interfaces.StatorWriterBO;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.stator.IStatorable;
import pasa.cbentley.core.src4.stator.StatorReader;
import pasa.cbentley.core.src4.stator.StatorWriter;
import pasa.cbentley.core.src4.structs.IntToObjects;
import pasa.cbentley.framework.core.j2se.ctx.CoreFrameworkJ2seCtx;
import pasa.cbentley.framework.core.src4.app.IAppli;
import pasa.cbentley.framework.core.src4.engine.LaunchValues;
import pasa.cbentley.framework.core.src4.interfaces.ITechFeaturesHost;
import pasa.cbentley.framework.coreui.j2se.ctx.IConfigCoreUiJ2se;
import pasa.cbentley.framework.coreui.src4.ctx.CoreUiCtx;
import pasa.cbentley.framework.coreui.src4.engine.CanvasHostAbstract;
import pasa.cbentley.framework.coreui.src4.interfaces.ICanvasAppli;
import pasa.cbentley.framework.coreui.src4.interfaces.ICanvasHost;
import pasa.cbentley.framework.coreui.src4.tech.ITechCanvasHost;
import pasa.cbentley.framework.coreui.src4.tech.ITechFramePos;

/**
 * Saves the states of the UI that the app is not aware of because part of the host implementation.
 * 
 * Screen config acts as a key.
 * 
 * Model data depends on the screen configuration.
 * 
 * User may decide to load a Model State from another configuration if the current one is not desirable
 * 
 * @author Charles Bentley
 *
 */
public class StateLoaderFrameworkUI implements IStringable, IStatorable {

   /**
    * 
    */
   protected ByteObject        configLoaded;

   protected final CoreFrameworkJ2seCtx cucj;

   public StateLoaderFrameworkUI(CoreFrameworkJ2seCtx cjc) {
      this.cucj = cjc;
   }

   private ByteObject getScreenConfigLoaded() {
      if (configLoaded == null) {
         subConfigLoad();
      }
      return configLoaded;
   }

   /**
    * Load the previous state of Canvases for the application
    */
   public boolean loadLastState() {
      IAppliState appli = null;
      try {
         ByteObject configLoaded = getScreenConfigLoaded();
         ByteObject[] bos = configLoaded.getSubs();
         if (bos != null) {
            IntToObjects ito = new IntToObjects(cucj.getUCtx(), bos.length);
            ICanvasAppli root = appli.getCanvas();
            //at least add the root
            ito.add(root);
            //we have to find the canvas tech using the ID
            //TODO. when data is corrupted.. could launch many frames.. so we have to deal
            // settings data is outside data that can be corrupted
            // static data which says the maximum number of canvas of each type
            for (int i = 0; i < bos.length; i++) {
               ByteObject bo = bos[i];
               //when we have a TechCanvas, create the canvas associated with it
               if (bo.getType() == ITechCanvasHost.TCANVAS_TYPE) {
                  int id = bo.get2(ITechCanvasHost.TCANVAS_OFFSET_03_ID2);
                  //tech canvas appli?
                  ICanvasAppli canvas = appli.getCanvas(id, bo);
                  if (canvas != null) {
                     //whats the ICanvashost of a CanvasAppli?
                     ito.add(canvas);
                  } else {
                     //#debug
                     toDLog().pInit("Null Canvas", bo, CoreFrameworkJ2seCtx.class, "loadLastState");
                  }
               }
            }
            //show each canvas found in the screen config last state.
            for (int i = 0; i < ito.nextempty; i++) {
               ((ICanvasAppli) ito.objects[i]).getCanvasHost().canvasShow();
            }
            return true;
         } else {
            //load root canvas
            ICanvasAppli root = appli.getCanvas();
            root.getCanvasHost().canvasShow();
            return true;
         }
      } catch (Exception e) {
         e.printStackTrace();
         return false;
      } finally {

      }
   }

   /**
    * Makes sure the {@link ByteObject} is valid with a {@link ITechFramePos}
    * When {@link ITechCanvasHost#TCANVAS_OFFSET_03_ID2} is zero.
    * This is ignored.
    * <br>
    * The xywh values defined in {@link LaunchValues} are used CanvasTech is null or FramePos is null.
    * 
    * @param tech
    * @return
    */
   protected ByteObject loadValidNonNullTech(ByteObject tech) {
      CoreUiCtx cuc = cucj.getCUC();
      //load up the tech param
      if (tech == null) {
         //create the default tech with default ID. it will load ICON and Title from LaunchValues ?
         tech = cuc.createTechCanvasHostDefault();
      } else {
         //look up tech id in config
         int idInput = tech.get2(ITechCanvasHost.TCANVAS_OFFSET_03_ID2);
         if (idInput != 0) {
            //id was set previously
            if (configLoaded != null) {
               //#debug
               toDLog().pInit("Reading Config settings", configLoaded, this.getClass(), "loadValidNonNullTech");
               //read all the screen configuration for the CanvasTech matching the ID.
               ByteObject[] bos = configLoaded.getSubs();
               if (bos != null) {
                  //we have to find the canvas tech using the ID
                  for (int i = 0; i < bos.length; i++) {
                     if (bos[i].getType() == tech.getType()) {
                        int ids = bos[i].get2(ITechCanvasHost.TCANVAS_OFFSET_03_ID2);
                        if (ids == idInput) {
                           tech = bos[i]; //assign the found tech
                        }
                     }
                  }
               }
            }
         }
      }
      //set starting values for non nulls
      ByteObject framePos = tech.getSubIndexed2(ITechCanvasHost.TCANVAS_OFFSET_14_FRAMEPOS2);
      if (framePos == null) {
         cuc.createTechFrameDefault();
         //set default size
         setDefaultXYWH(framePos);
         //set which ID? none. which means the canvas is not saved
         tech.setSubIndexed2(framePos, ITechCanvasHost.TCANVAS_OFFSET_14_FRAMEPOS2);
      } else {
         IConfigCoreUiJ2se cfgUI = cucj.getCoreUiJ2seCtx().getConfigUIJ2se();
         int w = cfgUI.getDefaultCanvasW();
         int h = cfgUI.getDefaultCanvasH();
         framePos.set2IfSmallerOrEqual(0, ITechFramePos.FPOS_OFFSET_04_W2, w);
         framePos.set2IfSmallerOrEqual(0, ITechFramePos.FPOS_OFFSET_05_H2, h);
      }
      return tech;
   }

   /**
    * Save the current Canvas configuration for the screen current setup.
    * <br>
    * makes sure the origin of each inside area is inside a container.
    * 
    * each canvas host has a unique ID to identify it
    * @param bo {@link ByteObject} of type {@link ITypesBentleyFw#TY}
    */
   public void saveSettings(ByteObject bo) {
      //#debug
      bo.checkType(IBOTypesBOC.TYPE_012_CTX_SETTINGS);

      ByteObject screenConfig = cucj.getHostJ().getScreenConfigLive();
      //#debug
      toDLog().pInit("Screen Config ", screenConfig, StateLoaderFrameworkUI.class, "saveSettings");

      CanvasHostAbstract[] canvases = cucj.getCUC().getCanvases();

      for (int i = 0; i < canvases.length; i++) {
         CanvasHostAbstract ch = canvases[i];
         ByteObject canvasTech = ch.getTech();
         ByteObject framePosOld = canvasTech.getSubIndexed2(ITechCanvasHost.TCANVAS_OFFSET_14_FRAMEPOS2);
         ByteObject framePos = cucj.getCUC().createTechFrameDefault();
         //sets current pos. This is the absolute position
         int cx = ch.getICX(); // what is this? the wrapper position or the component position which is 0
         framePos.set2(ITechFramePos.FPOS_OFFSET_02_X2, cx);
         framePos.set2(ITechFramePos.FPOS_OFFSET_03_Y2, ch.getICY());
         framePos.set2(ITechFramePos.FPOS_OFFSET_04_W2, ch.getICWidth());
         framePos.set2(ITechFramePos.FPOS_OFFSET_05_H2, ch.getICHeight());
         boolean isFullScreen = ch.isCanvasFeatureEnabled(ITechFeaturesHost.SUP_ID_27_FULLSCREEN);
         framePos.setFlag(ITechFramePos.FPOS_OFFSET_01_FLAG, ITechFramePos.FPOS_FLAG_1_FULLSCREEN, isFullScreen);

         //get current screen?

         canvasTech.setSubIndexed2(framePos, ITechCanvasHost.TCANVAS_OFFSET_14_FRAMEPOS2);
         //ByteObject techCanvasAppli = ch.getCanvasAppli().getTechCanvasAppli();
         canvasTech.setSubIndexed2(framePos, ITechCanvasHost.TCANVAS_OFFSET_14_FRAMEPOS2);

         //state of the canvas content?

         //#debug
         toDLog().pInit("CanvasTech #" + i, canvasTech, StateLoaderFrameworkUI.class, "saveSettings");

         screenConfig.addByteObject(canvasTech);
      }
      //save current screen config
      ByteObject[] bos = bo.getSubs();
      boolean wasAdded = false;
      if (bos != null) {
         for (int i = 0; i < bos.length; i++) {
            ByteObject sb = bos[i];
            if (sb.equalsContent(screenConfig)) {
               bos[i] = screenConfig;
               wasAdded = true;
               break;
            }
         }
      }
      if (!wasAdded) {
         //add it
         bo.addSub(screenConfig);
      }

      //#debug
      toDLog().pInit("Settings of J2SE Module", bo, StateLoaderFrameworkUI.class, "saveSettings");

   }

   /**
    * 
    * @param framePos
    */
   private void setDefaultXYWH(ByteObject framePos) {
      framePos.checkType(ITechFramePos.FPOS_TYPE);
      IConfigCoreUiJ2se cfgUI = cucj.getCoreUiJ2seCtx().getConfigUIJ2se();
      int w = cfgUI.getDefaultCanvasW();
      int h = cfgUI.getDefaultCanvasH();
      int frameX = 10;
      int frameY = 10;
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      if (w > screenSize.width) {
         w = screenSize.width;
      }
      if (h > screenSize.height) {
         h = screenSize.height;
      }
      frameX = screenSize.width / 2 - w / 2;
      frameY = screenSize.height / 2 - h / 2;
      framePos.set2(ITechFramePos.FPOS_OFFSET_02_X2, frameX);
      framePos.set2(ITechFramePos.FPOS_OFFSET_03_Y2, frameY);
      framePos.set2(ITechFramePos.FPOS_OFFSET_04_W2, w);
      framePos.set2(ITechFramePos.FPOS_OFFSET_05_H2, h);

   }

   public void stateReadFrom(StatorReader state) {

      StatorReaderFramework stator = (StatorReaderFramework) state;

      ByteObject screenConfig = cucj.getHostJ().getScreenConfigLive();

      //

      StatorReaderFramework statorOfScreen = stator.getKeyedStatorView(screenConfig);
      
      //ui presentation state depends on screen configuration
      //the screen config is the key to ui state

      //load J2SE perspective

      int numFrames = statorOfScreen.getDataReader().readInt();
      if (numFrames <= 0) {
         //assume no valid state, start with fresh view state
      }

      for (int i = 0; i < numFrames; i++) {
         ByteObject techCanvasHost = stator.readByteObject();

         //id allows to put stator reading position
         ICanvasHost canvasHost = cucj.getCoreUiJ2seCtx().createCanvas(techCanvasHost);
         //flag stator has only view.. so no data for model.. no canvasAppli
         canvasHost.stateReadFrom(statorOfScreen);

      }
      //
   }

   public void stateWriteTo(StatorWriter state) {
      StatorWriterFramework stator = (StatorWriterFramework) state;

      //this is out key
      ByteObject screenConfig = cucj.getHostJ().getScreenConfigLive();

      StatorWriterBO statorOfScreen = stator.createKeyedStatorView(screenConfig);

      CanvasHostAbstract[] canvases = cucj.getCUC().getCanvases();
      int numCanvases = canvases.length;
      statorOfScreen.getDataWriter().writeInt(numCanvases);
      for (int i = 0; i < numCanvases; i++) {
         CanvasHostAbstract ch = canvases[i];
         ch.stateWriteTo(statorOfScreen); //write canvas appli state or not?
      }
   }

   /**
    */
   public void subConfigLoad() {

      ByteObject bo = cucj.getSettingsBO();

      ByteObject liveScreenConfig = cucj.getHostJ().getScreenConfigLive();
      //get the configuration that matches the screens setup
      ByteObject[] bos = bo.getSubs();
      if (bos != null) {
         for (int i = 0; i < bos.length; i++) {
            ByteObject sb = bos[i];
            if (sb.equalsContent(liveScreenConfig)) {
               configLoaded = bos[i];
               break;
            }
         }
      }
      if (configLoaded == null) {
         configLoaded = liveScreenConfig; //live config without canvas parameters parameters
      }
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "StateLoader");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "StateLoader");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return cucj.getUCtx();
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
