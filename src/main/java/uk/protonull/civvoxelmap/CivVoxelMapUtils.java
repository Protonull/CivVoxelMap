package uk.protonull.civvoxelmap;

import java.util.List;
import java.util.regex.Pattern;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class CivVoxelMapUtils {
    private static final Pattern LEGACY_FORMATTER_REGEX = Pattern.compile("§.");

    public static @Nullable String getPlainContents(
        final Component component
    ) {
        return component == null ? null : LEGACY_FORMATTER_REGEX.matcher(component.getString()).replaceAll("");
    }

    public static @Nullable Component getPlainComponent(
        final Component component
    ) {
        return switch (StringUtils.defaultIfEmpty(getPlainContents(component), null)) {
            case final String contents -> Component.literal(contents);
            case null -> null;
        };
    }

    public static @NotNull Component ofChildren(
        final @NotNull Component @NotNull ... children
    ) {
        final MutableComponent result = Component.empty();
        result.getSiblings().addAll(List.of(children));
        return result;
    }
}
