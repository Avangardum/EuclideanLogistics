/*
 * Copyright (C) 2025 Euclidean Logistics contributors. Licensed under GNU AGPL v3. See License.md.
 */

package net.avangardum.euclideanlogistics.helpers;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MixinHelpers {
    public static <T> @NotNull T asNotNull(@Nullable T value) {
        // noinspection DataFlowIssue
        return value;
    }
}
