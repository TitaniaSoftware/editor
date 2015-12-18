package org.yawlfoundation.yawl.worklet.menu;

import org.yawlfoundation.yawl.editor.ui.actions.net.YAWLSelectedNetAction;
import org.yawlfoundation.yawl.editor.ui.swing.MessageDialog;
import org.yawlfoundation.yawl.worklet.client.WorkletClient;
import org.yawlfoundation.yawl.worklet.dialog.ReplaceWorkletDialog;
import org.yawlfoundation.yawl.worklet.selection.WorkletRunner;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Collections;

class ReplaceWorkletAction extends YAWLSelectedNetAction {

    {
        putValue(Action.SHORT_DESCRIPTION, "Replace Worklet");
        putValue(Action.NAME, "Add Rule");
        putValue(Action.LONG_DESCRIPTION, "Replace a Worklet Selection");
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_R);
    }


    public void actionPerformed(ActionEvent event) {
        java.util.List<WorkletRunner> runners = getRunners();
        if (! (runners == null || runners.isEmpty())) {
            new ReplaceWorkletDialog(runners).setVisible(true);
        }
    }


    private java.util.List<WorkletRunner> getRunners() {
        try {
            java.util.List<WorkletRunner> runners =
                    WorkletClient.getInstance().getRunningWorkletList();
            if (runners == null || runners.isEmpty()) {
                MessageDialog.info("There are no currently executing worklets to replace.",
                        "Service Information");
            }
            return runners;
        }
        catch (IOException ioe) {
            MessageDialog.error(ioe.getMessage(), "Service Error");
            return Collections.emptyList();
        }
    }

}
