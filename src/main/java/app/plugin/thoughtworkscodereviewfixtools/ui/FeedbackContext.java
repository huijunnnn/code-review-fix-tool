package app.plugin.thoughtworkscodereviewfixtools.ui;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FeedbackContext {
    private String feedback;
    private String label;
}
