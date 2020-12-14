/*
 * (c) 2018-2020 Charles-Philip Bentley
 * This code is licensed under MIT license (see LICENSE.txt for details)
 */
package pasa.cbentley.framework.core.j2se.engine;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.byteobjects.src4.ctx.BOCtx;
import pasa.cbentley.byteobjects.src4.interfaces.ITechStateBO;
import pasa.cbentley.byteobjects.src4.interfaces.StatorReaderBO;
import pasa.cbentley.byteobjects.src4.interfaces.StatorWriterBO;
import pasa.cbentley.core.src4.stator.IStatorable;
import pasa.cbentley.core.src4.stator.Stator;

public class StatorWriterFramework extends StatorWriterBO {

   public StatorWriterFramework(BOCtx boc) {
      super(boc);
   }


}
