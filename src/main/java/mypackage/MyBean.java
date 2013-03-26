package mypackage;

import com.google.common.base.Joiner;
import org.apache.myfaces.custom.radio.HtmlRadio;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import java.io.Serializable;

@ManagedBean(name = "my")
@ViewScoped
public class MyBean implements Serializable {

    private String stage1Selected;
    private int stage1SelectedIndex;
    private RadioButtonData[] stage1Values;

    private boolean[] stage2Render;
    private RadioButtonData[][] stage2Values;
    private String stage2Selected;

    private String message;

    private Joiner joiner = Joiner.on(',').skipNulls();

    @PostConstruct
    private void setupDatamodel() {
        stage1Values = new RadioButtonData[]{
                new RadioButtonData("Falscher Artikel geliefert", "falscherArtikel", "", false),
                new RadioButtonData("Ware wurde nicht bestellt / falsch bestellt", "wareNichtBestellt", "", false),
                new RadioButtonData("Transportschaden", "transportschaden", "", false),
                new RadioButtonData("Lieferung unvollständig", "lieferungUnvollstandig", "", false),
                new RadioButtonData("Ware defekt", "wareDefekt", "", false),
                new RadioButtonData("Ware gefällt nicht / entspricht nicht den Erwartungen", "wareGefalltNicht", "", false)
        };
        stage2Values = new RadioButtonData[stage1Values.length][];
        stage2Values[0] = new RadioButtonData[]{
                new RadioButtonData("Falsche Größe", "falscheGrosse", "", false),
                new RadioButtonData("Falsche Farbe", "falscheFarbe", "", false),
                new RadioButtonData("Komplett falscher Artikel", "komplettFalsch", "", false)
        };
        stage2Values[1] = new RadioButtonData[]{
                new RadioButtonData("Falsche Liefermenge", "falscheLiefermenge", "", false),
                new RadioButtonData("Doppelte Lieferung", "doppelteLieferung", "", false)
        };
        stage2Values[2] = new RadioButtonData[]{
                new RadioButtonData("Zu lange Lieferzeit / Ware zu spät geliefert", "langeLieferzeit", "", false)
        };
        stage2Render = new boolean[stage1Values.length];
    }

    public String getStage1Selected() {
        return stage1Selected;
    }

    public void setStage1Selected(String stage1Selected) {
        this.stage1Selected = stage1Selected;
    }

    public RadioButtonData[] getStage1Values() {
        return stage1Values;
    }

    public void stage1ChangeListener(AjaxBehaviorEvent ajaxBehaviorEvent) {
        // Reset selected index (previous request)
        stage2Render[stage1SelectedIndex] = false;
        // Set selected index
        HtmlRadio source = (HtmlRadio) ajaxBehaviorEvent.getSource();
        stage1SelectedIndex = source.getIndex();
        stage2Render[stage1SelectedIndex] = true;
    }

    public boolean[] getStage2Render() {
        return stage2Render;
    }

    public String getStage2Selected() {
        if (null != stage2Selected) {
            System.out.println("getStage2Selected=" + stage2Selected);
        }
        return stage2Selected;
    }

    public void setStage2Selected(String stage2Selected) {
        this.stage2Selected = stage2Selected;
    }

    public RadioButtonData[][] getStage2Values() {
        return stage2Values;
    }

    /**
     * Phase 5
     * @param ajaxBehaviorEvent The event.
     */
    public void stage2AjaxListener(AjaxBehaviorEvent ajaxBehaviorEvent) {
        System.out.println(ajaxBehaviorEvent);
    }

    /**
     * Phase 3
     * @param valueChangeEvent The event.
     */
    public void stage2ValueChangeListener(ValueChangeEvent valueChangeEvent) {
        Object oldValue = (Object) valueChangeEvent.getOldValue();
        Object[] newValue = (Object[]) valueChangeEvent.getNewValue();
        // Case 1 initial setting of value: oldValue is not set and newValue is an array with length > 0
        // Case 2 oldValue is set, allow newValue is not set
        if ((null == oldValue && newValue.length > 0) || null != oldValue) {
            stage2Selected = joiner.join(newValue);
        }
        System.out.println(valueChangeEvent);
    }

    public boolean isTextareaMandatory() {
        return false;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    /**
     * Phase 5
     * @param ajaxBehaviorEvent The event.
     */
    public void textareaAjaxListener(AjaxBehaviorEvent ajaxBehaviorEvent) {
        HtmlInputTextarea source = (HtmlInputTextarea) ajaxBehaviorEvent.getSource();
        String value = (String) source.getValue();
        System.out.println(value);
    }

    /**
     * Phase 3
     * @param valueChangeEvent The event.
     */
    public void textareaValueChangeListener(ValueChangeEvent valueChangeEvent) {
        System.out.println(valueChangeEvent);
    }

    public void commandButton() {
        System.out.println("commandButton: stage1Selected=" + stage1Selected + " stage2Selected=" + stage2Selected + " message=" + message);
    }

}
