package app.plugin.thoughtworkscodereviewfixtools.thoughtworkscodereviewtools.ui;

import javax.swing.*;

public class CodeReviewPanel extends JPanel {

    private JTextField feedbackTextField;

    public CodeReviewPanel() {
        initFeedbackTextField();
    }

    private void initFeedbackTextField() {
        feedbackTextField = new JTextField(20);
        this.add(feedbackTextField);
    }
    public FeedbackContext getFeedbackContext() {
        return FeedbackContext.builder()
                .feedback(feedbackTextField.getText())
                .build();
    }





}
