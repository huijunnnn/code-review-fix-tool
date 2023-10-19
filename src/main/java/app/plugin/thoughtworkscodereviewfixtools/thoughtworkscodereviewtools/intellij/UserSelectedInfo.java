package app.plugin.thoughtworkscodereviewfixtools.thoughtworkscodereviewtools.intellij;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.VisualPosition;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import java.util.Optional;

import static com.intellij.openapi.actionSystem.CommonDataKeys.VIRTUAL_FILE;
import static org.apache.commons.lang3.StringUtils.defaultString;

public class UserSelectedInfo {
    private final Project project;

    //所选文件
    private final VirtualFile file;

    //编辑器实例
    private final Editor editor;

    public UserSelectedInfo(AnActionEvent actionEvent) {
        this.project = actionEvent.getProject();
        this.file = actionEvent.getData(VIRTUAL_FILE);
        this.editor = actionEvent.getData(CommonDataKeys.EDITOR);
    }

    public Project getProject() {
        return project;
    }

    public String getProjectName() {
        return defaultString(project.getName());
    }

    //返回所选文件相对于项目的规范路径。如果未选择文件，则返回空字符串。
    public String getSelectedFilePath() {
        return Optional.ofNullable(file)
                .map(VirtualFile::getCanonicalPath)
                .map(this::getRelativePath)
                .orElse("");
    }

    //返回当前在编辑器中选择的文本。如果未选择任何文本，则返回空字符串。
    public String getSelectedText() {
        return editor != null ? editor.getSelectionModel().getSelectedText() : "";
    }

    //采用规范路径并返回项目内文件的相对路径。它包括所选文本的起始行和结束行作为路径的一部分。
    private String getRelativePath(String canonicalPath) {
        int projectNameIndex = canonicalPath.indexOf(getProjectName());
        boolean isExistedFolded = this.editor.getFoldingModel().getAllFoldRegions().length > 0;
        return canonicalPath.substring(projectNameIndex + getProjectName().length() + 1)
                + ' '
                + getSelectedTextStartLine(isExistedFolded)
                + '-'
                + getSelectTextEndLine(isExistedFolded);
    }

    private int getSelectedTextStartLine(boolean isExistedFolded) {
        VisualPosition selectionStartPosition = this.editor.getSelectionModel().getSelectionStartPosition();
        return isExistedFolded ? this.editor.visualToLogicalPosition(selectionStartPosition).line + 1 : selectionStartPosition.getLine() + 1;
    }

    private int getSelectTextEndLine(boolean isExistedFolded) {
        VisualPosition selectionEndPosition = this.editor.getSelectionModel().getSelectionEndPosition();
        return isExistedFolded ? this.editor.visualToLogicalPosition(selectionEndPosition).line + 1 : selectionEndPosition.getLine() + 1;
    }
}
