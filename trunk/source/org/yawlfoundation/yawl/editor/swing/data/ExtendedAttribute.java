package org.yawlfoundation.yawl.editor.swing.data;

import org.yawlfoundation.yawl.editor.data.DataVariable;
import org.yawlfoundation.yawl.editor.data.Decomposition;
import org.yawlfoundation.yawl.editor.net.NetGraph;

import javax.swing.*;
import java.awt.*;

/**
 * @author Mike Fowler
 *         Date: Oct 28, 2005
 * @author Michael Adams updated for 2.1
 */
public class ExtendedAttribute implements Comparable<ExtendedAttribute> {

//    public static final int DEFAULT_ATTRIBUTE = 0;
    public static final int USER_ATTRIBUTE = 1;
    public static final int SYSTEM_ATTRIBUTE = 2;

    private DataVariable variable;
    private Decomposition decomposition;
    private NetGraph graph;
    private String name;
    private String type;
    private String value;
    private JComponent component;
    private ExtendedAttributeGroup group;
    private int attributeType = USER_ATTRIBUTE;

    /**
     * Constructor for variable level attributes
     *
     * @param variable
     * @param name
     * @param type
     * @param value    Passed in separately as ExtendedAttribute is not responsible for
     * keeping the DataVariable consistent.
     */
    public ExtendedAttribute(DataVariable variable, Decomposition decomposition,
                             String name, String type, String value) {
        this.variable = variable;
        this.decomposition = decomposition;
        this.name = name;
        this.type = type;
        this.value = value;
        getComponent();
    }


    public ExtendedAttribute(DataVariable variable, Decomposition decomposition,
                             String name, String type, String value,
                             ExtendedAttributeGroup group) {
        this(variable, decomposition, name, type, value);
        this.group = group;
    }


    /**
     * Constructor for decomposition level attributes
     *
     * @param decomposition
     * @param name
     * @param type
     * @param value Passed in separately as ExtendedAttribute is not responsible for
     * keeping the Decomposition consistent.
     */
    public ExtendedAttribute(NetGraph graph, Decomposition decomposition, String name,
                             String type, String value) {
        this.graph = graph;
        this.decomposition = decomposition;
        this.name = name;
        this.type = type;
        this.value = value;
        getComponent();
    }

    public ExtendedAttribute(NetGraph graph, Decomposition decomposition, String name,
                             String type, String value, ExtendedAttributeGroup group) {
        this(graph, decomposition, name, type, value);
        this.group = group;
    }


    public JComponent getComponent() {
        if (component != null) return component;

        type = type.trim();
        if (type.equalsIgnoreCase("colour")) type = "color";
        if (type.equalsIgnoreCase("boolean")) {
            JCheckBox box = new JCheckBox();
            box.setSelected(value.trim().equalsIgnoreCase("true"));
            box.setHorizontalAlignment(SwingConstants.CENTER);
            component = box;
        }
        else if (type.matches("enumeration\\s*\\{.*\\}")) {
            String[] values = type.split("\\{|\\}|,");
            JComboBox box = new JComboBox();
            for (int i = 1; i < values.length; i++) {             // 0 = "enumeration"
                String valueAdded = values[i].trim();
                box.addItem(valueAdded);
            }
            if (value.trim().length() > 0) {
                box.setSelectedItem(value.trim());
            }
            else {
                box.setSelectedIndex(0);
            }
            component = box;
        }
        else if (type.matches("attribute\\s*\\{.*\\}")) {
            String property = type.substring(type.indexOf("${") + 2, type.indexOf("}"));
            attributeType = SYSTEM_ATTRIBUTE;
            if (variable != null) {
                variable.setAttribute(name, new DynamicValue(property, variable));
            }
            else {
                decomposition.setAttribute(name, new DynamicValue(property, decomposition));
            }
        }
//        else if (type.equalsIgnoreCase("text")) {
//            component = new ExtendedTextField(this);
//        }
//        else if (type.equalsIgnoreCase("xquery")) {
//            component = new ExtendedXQueryField(this);
//        }
////        else if (type.equalsIgnoreCase("font")) {
//        else if (name.startsWith("font")) {
//            component = new ExtendedField(this);
//        }
//        else if (type.equalsIgnoreCase("color")) {
//            component = new ExtendedColorField(this);
//        }
        else {
            component = new JTextField(value);
        }
        return component;
    }

    public String toString() {
        return getValue();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAttributeType() {
        return attributeType;
    }

    public ExtendedAttributeGroup getGroup() {
        return group;
    }

    public void setGroup(ExtendedAttributeGroup group) {
        this.group = group;
    }

    public String getValue() {
        if (component != null) {
            if (component instanceof JTextField) {
                value = ((JTextField) component).getText();
            }
            else if (component instanceof JCheckBox) {
                value = String.valueOf(((JCheckBox) component).isSelected());
            }
            else if (component instanceof JComboBox) {
                value = ((JComboBox) component).getSelectedItem().toString();
            }
        }
        return value;
    }

    public void setValue(Color color) {
        if (component instanceof ColorButton) {
            component.setBackground(color);
            value = colourToHex(color);
            ((ColorButton) component).setText(colourToHex(color));
        }
    }

    public void setValue(String value) {
        this.value = value;
        if (component != null) {
            if (component instanceof JTextField) {
                ((JTextField) component).setText(value);
            }
            else if (component instanceof JCheckBox) {
                ((JCheckBox) component).setSelected(value.equalsIgnoreCase("true"));
            }
            else if (component instanceof JComboBox) {
                ((JComboBox) component).setSelectedItem(value);
            }
        }
    }

    public Decomposition getDecomposition() {
        if (graph != null) {
            return graph.getNetModel().getDecomposition();
        }
        else {
            return decomposition;
        }
    }

    public int compareTo(ExtendedAttribute other) {
        return name.compareTo(other.getName());
    }

    public Color hexToColour(String hexStr) {

        // expects the format #123456
        if ((hexStr == null) || (hexStr.length() < 7)) {
            return Color.WHITE;
        }

        try {
            int r = Integer.valueOf(hexStr.substring(1, 3), 16);
            int g = Integer.valueOf(hexStr.substring(3, 5), 16);
            int b = Integer.valueOf(hexStr.substring(5, 7), 16);
            return new Color(r, g, b);
        }
        catch (NumberFormatException nfe) {
            return Color.WHITE;
        }
    }

    public String colourToHex(Color color) {
        String hex = "#";
        hex += intToHex(color.getRed());
        hex += intToHex(color.getGreen());
        hex += intToHex(color.getBlue());
        return hex;
    }

    private String intToHex(int i) {
        String hex = Integer.toHexString(i).toUpperCase();
        if (hex.length() == 1) hex = "0" + hex;
        return hex;
    }
}
