/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.framework.core.j2se.engine;

import pasa.cbentley.byteobjects.src4.ctx.BOCtx;
import pasa.cbentley.core.j2se.ctx.J2seCtx;
import pasa.cbentley.core.src4.ctx.ConfigUDef;
import pasa.cbentley.core.src4.ctx.IConfigU;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.ILogConfigurator;
import pasa.cbentley.core.src5.ctx.C5Ctx;
import pasa.cbentley.framework.core.j2se.ctx.CoreFrameworkJ2seCtx;
import pasa.cbentley.framework.core.src4.app.IAppli;
import pasa.cbentley.framework.core.src4.app.IConfigApp;
import pasa.cbentley.framework.core.src4.ctx.CoreFrameworkCtx;
import pasa.cbentley.framework.core.src4.engine.CoordinatorAbstract;
import pasa.cbentley.framework.core.src4.interfaces.IDependencies;
import pasa.cbentley.framework.core.src4.interfaces.ILauncherAppli;
import pasa.cbentley.framework.core.src4.interfaces.ILauncherHost;
import pasa.cbentley.framework.coredata.src5.ctx.CoreData5Ctx;
import pasa.cbentley.framework.coredata.src5.ctx.IConfigCoreData5;
import pasa.cbentley.framework.coredraw.j2se.ctx.CoreDrawJ2seCtx;
import pasa.cbentley.framework.coreio.src5.ctx.CoreIO5Ctx;
import pasa.cbentley.framework.coreio.src5.ctx.IConfigCoreIO5;
import pasa.cbentley.framework.coreui.j2se.ctx.CoreUiJ2seCtx;
import pasa.cbentley.framework.coreui.src4.interfaces.IWrapperManager;

/**
 * Abstract {@link IAppli} launcher for Java Desktop.
 * <br>
 * Represents the MIDlet or the android Activity for J2SE based hosts.
 * 
 * The {@link CoordinatorAbstract} does the actual work.
 * 
 * The launcher creates the {@link CoreFrameworkCtx} instance.
 * 
 * It creates the necessary dependencies for the constructor
 * 
 * and initialize the data access based on the cfg parameters.
 * 
 * <li> {@link CoordinatorAbstract} implementation
 * @author Charles Bentley
 *
 */
public abstract class LaunchJ2SE implements ILauncherHost {

   protected final BOCtx                boc;

   protected final C5Ctx                c5;

   protected final CoreFrameworkJ2seCtx cfc;

   protected CoordinatorJ2SE            coordinator;

   protected final J2seCtx              j2c;

   protected final UCtx                 uc;

   /**
    * The constructor job is 
    * <li>init the logger, we may want to log code context constuctors.
    * <li>to create the code context with their state as soon as possible.
    * <li>
    */
   public LaunchJ2SE() {

      //no logger yet at this stage
      IConfigU configu = createConfigU(); //configU fetches the ILogConfigurator
      //set the logconfigurator if none
      ILogConfigurator logConfigurator = this.toStringGetLoggingConfig();
      configu.toStringSetLogConfigurator(logConfigurator);

      uc = new UCtx(configu, "LaunchJ2SE"); //constructor deals smoothly with a null
      boc = new BOCtx(uc);
      c5 = new C5Ctx(uc);


      //gives the data for the host code contexts
      IConfigCoreData5 configData = createConfigCoreData5(uc);
      CoreData5Ctx coreDataCtx = new CoreData5Ctx(configData, boc);

      //configuration decide what directory to use
      IConfigCoreIO5 configIO = createConfigCoreIO(uc);
      CoreIO5Ctx cio5c = new CoreIO5Ctx(configIO, c5);

      j2c = createJ2seCtx(uc, c5, boc);
      CoreDrawJ2seCtx cdc = createCoreDrawJ2seCtx(j2c, boc);
      CoreUiJ2seCtx cuc = createCoreUiJ2seCtx(cdc, cio5c);

      cfc = createCoreFrameworkJ2seCtx(cuc, coreDataCtx, cio5c);

      coordinator = createCoodinator(cfc);

      IWrapperManager wrapperManager = createWrapperManager(cfc);
      cuc.setWrapperManager(wrapperManager);

   }
   
   public UCtx getUC() {
      return uc;
   }
   
   public BOCtx getBOC() {
      return boc;
   }

   public LaunchJ2SE(CoreFrameworkJ2seCtx cfc) {
      this.cfc = cfc;
      this.uc = cfc.getUC();
      this.boc = cfc.getBOC();
      this.c5 = cfc.getC5();
      j2c = createJ2seCtx(uc, c5, boc);

   }

   public void appExit() {
      //ask others to write their state
   }

   public void appPause() {
   }


   /**
    * Creates a hardcoded config. It will be used to read from disk for a saved config
    * @param uc
    * @return
    */
   public abstract IConfigApp createConfigApp(UCtx uc);

   public abstract IConfigCoreData5 createConfigCoreData5(UCtx uc);

   public abstract IConfigCoreIO5 createConfigCoreIO(UCtx uc);

   /**
    * Override if you want to give a special config
    * @param uc
    * @return
    */
   protected IConfigU createConfigU() {
      return new ConfigUDef();
   }

   public abstract CoordinatorJ2SE createCoodinator(CoreFrameworkJ2seCtx cfc);

   public abstract CoreDrawJ2seCtx createCoreDrawJ2seCtx(J2seCtx j2c, BOCtx boc);

   public abstract CoreFrameworkJ2seCtx createCoreFrameworkJ2seCtx(CoreUiJ2seCtx cuc, CoreData5Ctx cdc, CoreIO5Ctx cio5c);

   public abstract CoreUiJ2seCtx createCoreUiJ2seCtx(CoreDrawJ2seCtx cdc, CoreIO5Ctx cio5c);

   public abstract J2seCtx createJ2seCtx(UCtx uc, C5Ctx c5, BOCtx boc);

   /**
    * Creates the {@link ILauncherAppli} for the {@link IAppli}.
    * The launcher will have the host configuration and create its specific {@link IConfigApp}
    * @return
    */
   protected abstract ILauncherAppli createLauncher(UCtx uc);

   /**
    * Decides which swing wrapper to use when creating canvases.
    * 
    * <li> {@link CanvasOwnerDefaultSwing}
    * @param cfc
    * @return {@link IWrapperManager}
    */
   public abstract IWrapperManager createWrapperManager(CoreFrameworkJ2seCtx cfc);

   public IAppli getAppli() {
      return coordinator.getAppli();
   }

   /**
    * The {@link CoreFrameworkCtx} created in the constructor
    */
   public CoreFrameworkCtx getCFC() {
      return cfc;
   }

   public CoreFrameworkJ2seCtx getCFCJ2() {
      return cfc;
   }

   public CoordinatorAbstract getCoordinator() {
      return coordinator;
   }

   public IDependencies getDependencies() {
      if (this instanceof IDependencies) {
         return (IDependencies) this;
      }
      throw new RuntimeException("Dependencings have not been defined on this launcher");
   }

   public UCtx getUCtx() {
      return uc;
   }

   public void initConfiguration() {

   }

   public void launch() {
      //appli launcher 2nd class
      ILauncherAppli launcherAppli = createLauncher(uc);
      //shake hands with Host
      this.startAppli(launcherAppli);
   }

   /**
    * The {@link ILauncherAppli} is known. Start it for starting the application.
    * <br>
    * <li> Swing needs special way to start appli in the GUI thread.
    * <li> JavaFx ...
    * <li>
    */
   public void startAppli(ILauncherAppli launcherAppli) {
      CoordinatorAbstract coordinator = getCoordinator();
      coordinator.frameworkStart(launcherAppli);

      //this might never be called.. 
      //wait for UI thread to run?
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, LaunchJ2SE.class,250);
      toStringPrivate(dc);
      dc.nlLvl(getCoordinator());
      ILogConfigurator logConfigurator = toStringGetLoggingConfig();
      if (logConfigurator == null) {
         dc.append("logConfigurator is null");
      } else {
         dc.append("LogConfigurator = (" + logConfigurator.getClass() + ".java:50)");
      }
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, LaunchJ2SE.class);
      toStringPrivate(dc);
   }

   /**
    * Called 
    */
   public void toStringEnableFullDebug() {
      getCoordinator().toStringEnableFullDebug();
   }

   /**
    * Returns the logging configurator for the logger
    */
   public ILogConfigurator toStringGetLoggingConfig() {
      return new LogConfiguratorJ2SE();
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
