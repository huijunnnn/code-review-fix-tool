package app.plugin.thoughtworkscodereviewfixtools.ui;

import com.intellij.openapi.ui.ComboBox;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import javax.swing.*;
import java.util.Arrays;
import java.util.List;



/**
 * @author huijunhong
 */
public class CodeReviewPanel extends JPanel {
    private static final int DEFAULT_COMBO_BOX_DISPLAY_COUNT = 5;

    private JTextField feedbackTextField;
    private ComboBox<String> labelComboBox;

    public CodeReviewPanel() {
        initLabelComboBox();
        initFeedbackTextField();
    }


    private void initFeedbackTextField() {
        feedbackTextField = new JTextField(20);
        this.add(feedbackTextField);
    }
    private void initLabelComboBox() {
        List<String> labelNameList = Arrays.asList("refactor", "feat", "fix","docs","style","test");
        labelComboBox = createInitializedLabelComboBox(labelNameList);
        labelComboBox.setToolTipText("Label");
        this.add(labelComboBox);
    }

    private ComboBox<String> createInitializedLabelComboBox(List<String> labelNameList) {
        ComboBox<String> comboBox = new ComboBox<>(new DefaultComboBoxModel<>(labelNameList.toArray(String[]::new)));
        comboBox.setRenderer(new LabelComboboxRender());
        comboBox.setEditable(true);
        comboBox.setMaximumRowCount(DEFAULT_COMBO_BOX_DISPLAY_COUNT);
        configureAutoCompletion(comboBox);
        return comboBox;
    }

    private void configureAutoCompletion(ComboBox<String> comboBox) {
        AutoCompleteDecorator.decorate(comboBox);
    }


    public FeedbackContext getFeedbackContext() {
        return FeedbackContext.builder()
                .feedback(feedbackTextField.getText())
                .label(getSelectedLabel())
                .build();
    }
    private String getSelectedLabel() {
        return labelComboBox.getItem();
    }

}
