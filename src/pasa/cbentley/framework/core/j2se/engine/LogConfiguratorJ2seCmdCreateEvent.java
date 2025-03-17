package pasa.cbentley.framework.core.j2se.engine;

import pasa.cbentley.byteobjects.src4.ctx.BOCtx;
import pasa.cbentley.byteobjects.src4.ctx.IToStringFlagsBO;
import pasa.cbentley.core.src4.ctx.ACtx;
import pasa.cbentley.core.src4.ctx.IToStringFlagsUC;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.IDLogConfig;
import pasa.cbentley.core.src4.logging.ILogConfigurator;
import pasa.cbentley.core.src4.logging.ILogConfiguratorCtx;
import pasa.cbentley.core.src4.logging.ITechLvl;

public class LogConfiguratorJ2seCmdCreateEvent implements ILogConfiguratorCtx {

   public LogConfiguratorJ2seCmdCreateEvent() {
   }

   public void apply(IDLogConfig log) {

      log.setLevelGlobal(ITechLvl.LVL_03_FINEST);

      log.setFlagMaster(MASTER_FLAG_11_IGNORES_BIGS, true);
      log.setFlagMaster(MASTER_FLAG_12_LEVEL, true);

      log.setFlagTag(FLAG_01_PRINT_ALWAYS, true);
      log.setFlagTag(FLAG_02_PRINT_NULL, true);

      log.setFlagTag(FLAG_25_PRINT_CREATE, true);
      log.setFlagTag(FLAG_11_PRINT_COMMANDS, true);
      log.setFlagTag(FLAG_07_PRINT_EVENT, true);

   }

   public void configureCtx(ACtx ac) {
      if (ac instanceof BOCtx) {
         configureBOC((BOCtx) ac);
      }

   }

   public void configureUCtx(UCtx uc) {
      uc.toStringSetToStringFlag(IToStringFlagsUC.FLAG_UC_01_SUCCINT, true);
   }

   /**
    * {@link IToStringFlagsBO}
    * @param boc
    */
   public void configureBOC(BOCtx boc) {

      boc.toStringSetToStringFlag(IToStringFlagsBO.TOSTRING_FLAG_2_IGNORE_PARAMS, false);
   }

}
