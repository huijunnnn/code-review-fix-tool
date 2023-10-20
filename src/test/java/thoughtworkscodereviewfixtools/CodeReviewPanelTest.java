package thoughtworkscodereviewfixtools;

import app.plugin.thoughtworkscodereviewfixtools.ui.CodeReviewPanel;
import app.plugin.thoughtworkscodereviewfixtools.ui.FeedbackContext;
import org.junit.Test;

import javax.swing.*;

import static org.junit.Assert.assertEquals;

public class CodeReviewPanelTest {
    @Test
    public void testGetFeedbackContext() {
        CodeReviewPanel codeReviewPanel = new CodeReviewPanel();
        JTextField textField = (JTextField) codeReviewPanel.getComponent(0);
        textField.setText("This is a test feedback");

        FeedbackContext expectedContext = FeedbackContext.builder()
                .feedback("This is a test feedback")
                .build();

        FeedbackContext actualContext = codeReviewPanel.getFeedbackContext();

        assertEquals(expectedContext.getFeedback(), actualContext.getFeedback());
    }
}
