/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Holden
 * Released under the BSD license, see LICENSE.txt
 */
public class FolderMaker {
   public FolderMaker(){};

   public static String createAcqFolder(String acqName){
      // check if "acqName" _1, _2, etc  exists. Create acqName_NN where NN 
      // is larger than existing folder increment

      File acqFolder = new File(acqName);

      //have to do this in a loop in case the replacement filename matches as well
      boolean acqNameIsOk = false;
      while (acqNameIsOk ==false){
         //if the folder already exists and contains files
         // parse the name of fileAcqFolder_ , think up a new one
         //find anything that looks like _(numbers) at the end of the file
         // if you dont find any, add _1 to the end
         // otherwise, increment the number

         if (!acqFolder.exists()){
            acqFolder.mkdirs();
            acqNameIsOk = true;
         } else{
            acqName = makeNewFolderName(acqName);
            acqFolder = new File(acqName);
            acqNameIsOk = false;//go round again til we get an empty folder
         }
      }

      return acqName;
   }
    
   public static String makeNewFolderName(String fname){
      String newFname;
      
      Pattern p = Pattern.compile("_\\d+$");// ie "_[some numbers][end of string]"
      Matcher m = p.matcher(fname);
      if (m.find()){
         String sMatch = m.group();
         //skip the _,convert it to a number, and increment it by 1
         int fNum = Integer.parseInt(sMatch.substring(1)) + 1;
         //get fname without the _ stuff
         fname = fname.substring(0,m.start());
         // increment the number by 1
         newFname = fname + "_" + fNum;
      }
      else{
         newFname = fname + "_" + 1;
      }

      return newFname;
   }



   
   
   

}
