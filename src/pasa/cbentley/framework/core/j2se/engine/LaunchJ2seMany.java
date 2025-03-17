/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.framework.core.j2se.engine;

import pasa.cbentley.byteobjects.src4.ctx.BOCtx;
import pasa.cbentley.core.j2se.ctx.J2seCoreCtx;
import pasa.cbentley.core.src4.ctx.ConfigUDef;
import pasa.cbentley.core.src4.ctx.IConfigU;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.ILogConfigurator;
import pasa.cbentley.core.src5.ctx.C5Ctx;
import pasa.cbentley.framework.core.data.src5.ctx.CoreData5Ctx;
import pasa.cbentley.framework.core.data.src5.ctx.IConfigCoreData5;
import pasa.cbentley.framework.core.draw.j2se.ctx.CoreDrawJ2seCtx;
import pasa.cbentley.framework.core.framework.src4.app.IAppli;
import pasa.cbentley.framework.core.framework.src4.app.IConfigApp;
import pasa.cbentley.framework.core.framework.src4.ctx.CoreFrameworkCtx;
import pasa.cbentley.framework.core.framework.src4.engine.CoordinatorAbstract;
import pasa.cbentley.framework.core.framework.src4.interfaces.ICreatorAppli;
import pasa.cbentley.framework.core.framework.src4.interfaces.IDependencies;
import pasa.cbentley.framework.core.framework.src4.interfaces.ILauncherHost;
import pasa.cbentley.framework.core.io.src5.ctx.CoreIO5Ctx;
import pasa.cbentley.framework.core.io.src5.ctx.IConfigCoreIO5;
import pasa.cbentley.framework.core.j2se.ctx.CoreFrameworkJ2seCtx;
import pasa.cbentley.framework.core.ui.j2se.ctx.CoreUiJ2seCtx;
import pasa.cbentley.framework.core.ui.src4.interfaces.IWrapperManager;

/**
 * Launcher for a Many Appli
 * @author Charles Bentley
 *
 */
public abstract class LaunchJ2seMany implements ILauncherHost {

   protected final BOCtx                boc;

   protected final C5Ctx                c5;

   protected final CoreFrameworkJ2seCtx cfc;

   protected CoordinatorJ2se            coordinator;

   private ICreatorAppli                creatorAppli;

   protected final J2seCoreCtx          j2c;

   protected final UCtx                 uc;

   /**
    * The constructor job is 
    * <li>init the logger, we may want to log code context constuctors.
    * <li>to create the code context with their state as soon as possible.
    * <li>
    */
   public LaunchJ2seMany() {

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

      //#debug
      toDLog().pCreate("", this, LaunchJ2seMany.class, "Created@103", LVL_04_FINER, true);

   }

   public LaunchJ2seMany(CoreFrameworkJ2seCtx cfc) {
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
    * Override if you want to give a special config that is not {@link ConfigUDef}.
    * @param uc
    * @return
    */
   protected IConfigU createConfigU() {
      return new ConfigUDef();
   }

   public abstract CoordinatorJ2se createCoodinator(CoreFrameworkJ2seCtx cfc);

   public abstract CoreDrawJ2seCtx createCoreDrawJ2seCtx(J2seCoreCtx j2c, BOCtx boc);

   public abstract CoreFrameworkJ2seCtx createCoreFrameworkJ2seCtx(CoreUiJ2seCtx cuc, CoreData5Ctx cdc, CoreIO5Ctx cio5c);

   public abstract CoreUiJ2seCtx createCoreUiJ2seCtx(CoreDrawJ2seCtx cdc, CoreIO5Ctx cio5c);

   /**
    * Creates the {@link ICreatorAppli} for the {@link IAppli}.
    * The launcher will have the host configuration and create its specific {@link IConfigApp}
    * @return
    */
   protected abstract ICreatorAppli createCreator(UCtx uc);

   public abstract J2seCoreCtx createJ2seCtx(UCtx uc, C5Ctx c5, BOCtx boc);

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

   public BOCtx getBOC() {
      return boc;
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

   public UCtx getUC() {
      return uc;
   }

   public UCtx getUCtx() {
      return uc;
   }

   public void initConfiguration() {

   }

   public void launch() {
      //appli launcher 2nd class
      creatorAppli = createCreator(uc);

      toDLog().pInit("CreatorAppli created : ", creatorAppli, LaunchJ2seMany.class, "launch@213", LVL_05_FINE, true);

      //shake hands with Host
      this.startAppli(creatorAppli);
   }

   /**
    * The {@link ICreatorAppli} is known. Start it for starting the application.
    * <br>
    * <li> Swing needs special way to start appli in the GUI thread.
    * <li> JavaFx ...
    * <li>
    */
   public void startAppli(ICreatorAppli creatorAppli) {
      CoordinatorAbstract coordinator = getCoordinator();

      toDLog().pInit("CreatorAppli Created", creatorAppli, LaunchJ2seMany.class, "startAppli@213", LVL_05_FINE, DEV_X_ONELINE_THREAD);
      coordinator.frameworkStart(creatorAppli);

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
      dc.root(this, LaunchJ2seMany.class, 250);
      toStringPrivate(dc);
      dc.nlLvl(coordinator, "coordinator");
      dc.nlLvl(creatorAppli, "creatorAppli");

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
      dc.root1Line(this, LaunchJ2seMany.class);
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
      return new LogConfiguratorJ2se();
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
