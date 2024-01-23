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
import pasa.cbentley.framework.coredata.src4.stator.StatorCoreData;
import pasa.cbentley.framework.coredata.src5.ctx.CoreData5Ctx;
import pasa.cbentley.framework.coredata.src5.ctx.IConfigCoreData5;
import pasa.cbentley.framework.coreio.src5.ctx.CoreIO5Ctx;
import pasa.cbentley.framework.coreio.src5.ctx.IConfigCoreIO5;

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
 * @author Charles Bentley
 *
 */
public abstract class LaunchJ2SE implements ILauncherHost {

   protected final BOCtx          boc;

   protected final C5Ctx          c5;

   protected final CoreIO5Ctx     cio5c;

   protected CoreFrameworkJ2seCtx cjc;

   protected final IConfigApp     configApp;

   protected final CoreData5Ctx   coreDataCtx;

   protected final J2seCtx        j2c;

   protected final UCtx           uc;

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

      uc = new UCtx(configu); //constructor deals smoothly with a null
      c5 = new C5Ctx(uc);
      boc = new BOCtx(uc);

      //get hardcoded app configuration that will decides the location of ctx settings
      configApp = createConfigApp(uc, c5, boc);

      //#debug
      toDLog().pInit("ConfigApp Created", configApp, LaunchJ2SE.class, "LaunchJ2SE", LVL_05_FINE, false);

      //gives the data for the host code contexts
      IConfigCoreData5 configData = createConfigCoreData5(uc);
      coreDataCtx = new CoreData5Ctx(configData, boc);

      j2c = createJ2seCtx(uc, c5, boc);

      //configuration decide what directory to use
      IConfigCoreIO5 configIO = createConfigCoreIO(uc);
      cio5c = new CoreIO5Ctx(configIO, uc);
   }

   public UCtx getUCtx() {
      return uc;
   }

   /**
    * 
    */
   public void addStatorFactories(StatorCoreData stator) {
      //sub may have some Must be called
   }

   /**
    * Override if you want to give a special config
    * @param uc
    * @return
    */
   protected IConfigU createConfigU() {
      return new ConfigUDef(null);
   }

   public void appExit() {
      //ask others to write their state
   }

   public IDependencies getDependencies() {
      if (this instanceof IDependencies) {
         return (IDependencies) this;
      }
      throw new RuntimeException("Dependencings have not been defined");
   }

   public void appPause() {
   }

   public IConfigApp createConfigApp(UCtx uc) {
      return createConfigApp(uc, c5, boc);
   }

   /**
    * Creates a hardcoded config. It will be used to read from disk for a saved config
    * @param uc
    * @return
    */
   public abstract IConfigApp createConfigApp(UCtx uc, C5Ctx c5, BOCtx bo);

   public abstract IConfigCoreData5 createConfigCoreData5(UCtx uc);

   public abstract IConfigCoreIO5 createConfigCoreIO(UCtx uc);

   public abstract J2seCtx createJ2seCtx(UCtx uc, C5Ctx c5, BOCtx boc);

   /**
    * Creates the {@link ILauncherAppli} for the {@link IAppli}.
    * The launcher will have the host configuration and create its specific
    * {@link IConfigApp}
    * @return
    */
   protected abstract ILauncherAppli createLauncher(UCtx uc);

   public void initConfiguration() {

   }

   public void launch() {
      //appli launcher 2nd class
      ILauncherAppli launcherAppli = createLauncher(uc);
      //shake hands with Host
      this.startAppli(launcherAppli);
   }

   public abstract CoordinatorAbstract getCoordinator();

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
      dc.root(this, LaunchJ2SE.class);
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
    * Returns the logging configurator for the logger
    */
   public ILogConfigurator toStringGetLoggingConfig() {
      return new LogConfiguratorJ2SE();
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   /**
    * Called 
    */
   public void toStringEnableFullDebug() {
      getCoordinator().toStringEnableFullDebug();
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
