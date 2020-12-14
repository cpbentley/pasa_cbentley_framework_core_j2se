/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.framework.core.j2se.engine;

import pasa.cbentley.core.src4.logging.IDLogConfig;
import pasa.cbentley.core.src4.logging.ILogConfigurator;
import pasa.cbentley.core.src4.logging.ITechConfig;
import pasa.cbentley.core.src4.logging.ITechLvl;
import pasa.cbentley.framework.core.j2se.ctx.CoreFrameworkJ2seCtx;

public class LogConfiguratorJ2SE implements ILogConfigurator {

   public LogConfiguratorJ2SE(CoreFrameworkJ2seCtx cac) {
   }

   public void apply(IDLogConfig log) {
      log.setLevelGlobal(ITechLvl.LVL_03_FINEST);
      log.setFlagPrint(ITechConfig.MASTER_FLAG_02_OPEN_ALL_PRINT, true);
      
      //log.setFlagTag(FLAG_09_PRINT_FLOW, true);
   }

}
