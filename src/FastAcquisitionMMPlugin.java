
import mmcorej.CMMCore;
import org.micromanager.MenuPlugin;
import org.micromanager.Studio;
import org.micromanager.data.Datastore;
import org.micromanager.internal.MMStudio;
import org.scijava.plugin.Plugin;
import org.scijava.plugin.SciJavaPlugin;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nsh167
 */
@Plugin(type = MenuPlugin.class)
public class FastAcquisitionMMPlugin  implements MenuPlugin, SciJavaPlugin {
   public static final String MENU_NAME = "FastAcquisition";
   public static final String TOOL_TIP_DESCRIPTION =
      "Fast acquisition directly to RAM, no metadata";
   private CMMCore core_;
   private MMStudio studio_;
   // TODO: assign this name to the viewer window once the api has that ability
   private final String ACQ_NAME = "Fast Acquisition";
   private Datastore store_;

   FastAcqDialog dlg_=null;
   
    @Override
    public void setContext(Studio studio) {
         studio_ = (MMStudio) studio;
         core_ = studio.getCMMCore();

    }
    
   @Override
    public void onPluginSelected() {
        if (dlg_==null) {
          dlg_ = new FastAcqDialog(studio_,false,  this);
        }
        dlg_.setVisible(true);
    }

    @Override
    public String getSubMenu() {
        return "";
    }

 

    @Override
    public String getName() {
         return MENU_NAME;
    }

    @Override
    public String getHelpText() {
        return "Fast acquisition directly to RAM, no metadata";
    }

    @Override
    public String getVersion() {
     return "V0.1";
    }

    @Override
    public String getCopyright() {
        return "Seamus Holden, University of Newcastle 2021";
    }
    
}
