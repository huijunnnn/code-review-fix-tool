package app.plugin.thoughtworkscodereviewfixtools.ui;

import javax.swing.*;
import java.awt.*;

class LabelComboboxRender extends JLabel implements ListCellRenderer<String> {
    public LabelComboboxRender() {
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends String> list, String value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        setText(value);  // 只显示标签名称
        return this;
    }
}
