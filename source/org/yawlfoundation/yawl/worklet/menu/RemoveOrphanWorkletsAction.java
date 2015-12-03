package org.yawlfoundation.yawl.worklet.menu;

import org.yawlfoundation.yawl.editor.ui.actions.YAWLBaseAction;
import org.yawlfoundation.yawl.worklet.dialog.WorkletLoadDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

class RemoveOrphanWorkletsAction extends YAWLBaseAction {

    {
        putValue(Action.SHORT_DESCRIPTION, "Remove Orphan Worklets");
        putValue(Action.NAME, "Remove Orphans");
        putValue(Action.LONG_DESCRIPTION, "Remove Worklets not referred to by any rule set");
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
    }


    public void actionPerformed(ActionEvent event) {
        new WorkletLoadDialog().setVisible(true);
    }

}
