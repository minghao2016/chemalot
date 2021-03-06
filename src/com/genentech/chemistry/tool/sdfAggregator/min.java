/*
   Copyright 2008-2015 Genentech Inc.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

*/
package com.genentech.chemistry.tool.sdfAggregator;

/**
 * This class implements the min function.
 * @author AG 2013
 *
 */

import com.aestel.utility.DataFormat;

public class min extends AggFunction
{  public static final String DESCRIPTION
         = "outName = min(Tag): returns the minimum of a numeric field 'Tag'.\n";



   public min(String outTag, String resultFormat, String funcName, String funcArg)
   {  super(outTag, resultFormat, funcName, funcArg);
   }



   private String getResult(String fmtStr)
   {  if( valueContainer.size() == 0 )
         return "";

      boolean isLong = true;
      for(String v : valueContainer)
      {  if( v.indexOf('.') >= 0)
         {  isLong = false;
            break;
         }
      }

      if( isLong )
         return longMin();

      String min = doubleMin();
      if( fmtStr != null && fmtStr.length() > 0 )
         return DataFormat.formatNumber(min, fmtStr);

      return min;
   }

   private String doubleMin()
   {  double dmin = Double.MAX_VALUE;
      String min = "";

      for(String v : valueContainer)
      {  double dv;
         try
         {  dv = Double.parseDouble(v);
         }catch(NumberFormatException e)
         {  return "Invalid value: " + v;
         }

         if( dv < dmin )
         {  dmin = dv;
            min = v;
         }
      }

      return min;
   }

   String longMin()
   {  long min = Long.MAX_VALUE;
      for(String v : valueContainer)
      {  long lv;
         try
         {  lv = Long.parseLong(v);
         }catch(NumberFormatException e)
         {  return "Invalid value: " + v;
         }

         min = Math.min(min, lv);
      }

      return Long.toString(min);
   }

   @Override
   public String getResult(int indxInGrp)
   {  return getResult(resultFormat);
   }
}
