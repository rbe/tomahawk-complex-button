package mypackage;

import java.io.Serializable;

public class RadioButtonData implements Serializable {

    private String label;

    private String value;

    private String description;

    private boolean disabled;

    public RadioButtonData(String label, String value, String description, boolean disabled) {
        this.label = label;
        this.value = value;
        this.description = description;
        this.disabled = disabled;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDisabled() {
        return disabled;
    }

}
