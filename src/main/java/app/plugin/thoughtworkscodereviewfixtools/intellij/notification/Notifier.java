package app.plugin.thoughtworkscodereviewfixtools.intellij.notification;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nullable;

public class Notifier {
    private static final String NOTIFICATION_GROUP_ID = "Custom Notification Group";

    private Notifier() {
        // 私有构造函数，防止实例化
    }

    public static void notifyInfo(@Nullable Project project, String content) {
        createNotification(content, NotificationType.INFORMATION).notify(project);
    }

    public static void notifyError(@Nullable Project project, String content) {
        createNotification(content, NotificationType.ERROR).notify(project);
    }

    private static Notification createNotification(String content, NotificationType type) {
        return NotificationGroupManager
                .getInstance().
                getNotificationGroup(NOTIFICATION_GROUP_ID)
                .createNotification(content, type);
    }
}

