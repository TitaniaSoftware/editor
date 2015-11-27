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

package org.yawlfoundation.yawl.editor.ui.swing;

import org.yawlfoundation.yawl.editor.core.YConnector;
import org.yawlfoundation.yawl.editor.ui.specification.io.LayoutRepository;
import org.yawlfoundation.yawl.editor.ui.specification.io.SpecificationReader;
import org.yawlfoundation.yawl.engine.YSpecificationID;
import org.yawlfoundation.yawl.engine.interfce.SpecificationData;
import org.yawlfoundation.yawl.util.StringUtil;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Michael Adams
 * @date 18/10/13
 */
public class SpecificationDownloadDialog extends AbstractDownloadDialog {

    public SpecificationDownloadDialog() {
        super();
        setTitle("Available Specifications");
    }


    @Override
    protected java.util.List<YSpecificationID> getLoadedSpecificationList() {
        java.util.List<YSpecificationID> list = new ArrayList<YSpecificationID>();
        try {
            for (SpecificationData specData : YConnector.getLoadedSpecificationList()) {
                YSpecificationID specID = specData.getID();
                list.add(specID);
            }
        }
        catch (IOException ioe) {
            showError("Failed to get list of specifications from the YAWL Engine: ", ioe);
        }
        return list;
    }


    @Override
    protected SpecificationReader getSpecificationReader(YSpecificationID specID,
                                                         String specXML) {
        return new SpecificationReader(specXML, getSpecificationLayout(specID));
    }

    @Override
    protected String getSelectedSpecification(YSpecificationID specID) throws IOException {
        String specXML = YConnector.getSpecification(specID);
        if (specXML.startsWith("<fail")) {
            throw new IOException(StringUtil.unwrap(specXML));
        }
        return specXML;
    }


    @Override
    protected String getSourceString() { return "YAWL Engine"; }


    private String getSpecificationLayout(YSpecificationID specID) {
            return LayoutRepository.getInstance().get(specID);
    }

}
