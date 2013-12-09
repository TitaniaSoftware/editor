/*
 * Copyright (c) 2004-2013 The YAWL Foundation. All rights reserved.
 * The YAWL Foundation is a collaboration of individuals and
 * organisations who are committed to improving workflow technology.
 *
 * This file is part of YAWL. YAWL is free software: you can
 * redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation.
 *
 * YAWL is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General
 * Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with YAWL. If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * Created By Jingxin XU
 */

package org.yawlfoundation.yawl.editor.ui.configuration.actions;

import org.yawlfoundation.yawl.editor.ui.YAWLEditor;
import org.yawlfoundation.yawl.editor.ui.actions.YAWLBaseAction;
import org.yawlfoundation.yawl.editor.ui.net.NetGraph;
import org.yawlfoundation.yawl.editor.ui.specification.pubsub.FileState;
import org.yawlfoundation.yawl.editor.ui.specification.pubsub.FileStateListener;
import org.yawlfoundation.yawl.editor.ui.specification.pubsub.Publisher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class ConfigurationSettingsAction extends YAWLBaseAction
        implements FileStateListener {

    {
        Publisher.getInstance().subscribe(this);
    }

    {
        putValue(Action.SHORT_DESCRIPTION, "Process Configuration Settings");
        putValue(Action.NAME, "Preferences...");
        putValue(Action.LONG_DESCRIPTION, "Process Configuration Settings");
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
    }


    public void actionPerformed(ActionEvent event) {
        final NetGraph net = this.getGraph();
        net.getNetModel().beginUpdate();
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ConfigurationReferenceJDialog dialog =
                        new ConfigurationReferenceJDialog(new JFrame(), net);
                dialog.setLocationRelativeTo(YAWLEditor.getInstance());
                dialog.setVisible(true);
            }
        });
        net.getNetModel().endUpdate();
    }


    public void specificationFileStateChange(FileState state) {
        setEnabled(state == FileState.Open);
    }

    /**
     *
     * @author jingxin
     */
    private class ConfigurationReferenceJDialog extends javax.swing.JDialog {

        private final NetGraph net;

        /** Creates new form ConfigurationReferenceJDialog */
        public ConfigurationReferenceJDialog(Frame parent, NetGraph net) {
            super(parent, true);
            this.net = net;
            initComponents();
        }

        /** This method is called from within the constructor to
         * initialize the form.
         * WARNING: Do NOT modify this code. The content of this method is
         * always regenerated by the Form Editor.
         */
        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">
        private void initComponents() {

            newElementConfig = new java.awt.Checkbox();
            AotGreyOut = new java.awt.Checkbox();
            denyblocking = new java.awt.Checkbox();
            changDefault = new java.awt.Checkbox();
            okButton = new javax.swing.JButton();

            setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            setTitle("Process Configuration Settings");

            newElementConfig.setLabel("Set new elements configurable");

            AotGreyOut.setLabel("Preview process automatically");

            denyblocking.setLabel("Deny blocking input ports");

            changDefault.setLabel("Allow changing default configurations");

            okButton.setText("OK");
            okButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    okButtonActionPerformed(evt);
                }
            });

            GroupLayout layout = new GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                    layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                    .addContainerGap()
                                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                            .addComponent(denyblocking, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(newElementConfig, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(AotGreyOut, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(changDefault, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(layout.createSequentialGroup()
                                                    .addGap(105, 105, 105)
                                                    .addComponent(okButton, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)))
                                    .addContainerGap(19, Short.MAX_VALUE))
            );
            layout.setVerticalGroup(
                    layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(newElementConfig, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(AotGreyOut, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(denyblocking, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(changDefault, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                                    .addComponent(okButton)
                                    .addContainerGap())
            );

            if(!this.net.getConfigurationSettings().isAllowBlockingInputPorts()){
                this.denyblocking.setState(true);
            }

            if(this.net.getConfigurationSettings().isApplyAutoGreyOut()){
                this.AotGreyOut.setState(true);
            }

            if(this.net.getConfigurationSettings().isNewElementsConfigurable()){
                this.newElementConfig.setState(true);
            }

            if(this.net.getConfigurationSettings().isAllowChangingDefaultConfiguration()){
                this.changDefault.setState(true);
            }
            pack();
            setResizable(false);
        }// </editor-fold>

        private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {
            this.net.getConfigurationSettings().setApplyAutoGreyOut(this.AotGreyOut.getState());
            this.net.getConfigurationSettings().setAllowBlockingInputPorts(!this.denyblocking.getState());
            this.net.getConfigurationSettings().setNewElementsConfigurable(this.newElementConfig.getState());
            this.net.getConfigurationSettings().setAllowChangingDefaultConfiguration(this.changDefault.getState());
            this.setVisible(false);
        }


        // Variables declaration - do not modify
        private java.awt.Checkbox AotGreyOut;
        private java.awt.Checkbox changDefault;
        private java.awt.Checkbox denyblocking;
        private java.awt.Checkbox newElementConfig;
        private javax.swing.JButton okButton;
        // End of variables declaration

    }

}
