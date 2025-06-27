package net.avangardum.euclideanlogistics.helpers;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MixinHelpers {
    public static <T> @NotNull T asNotNull(@Nullable T value) {
        // noinspection DataFlowIssue
        return value;
    }
}
