package uk.protonull.civvoxelmap;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class CivVoxelMapUtils {
    @Contract("!null -> !null")
    public static @Nullable String getPlainContents(
        final Component component
    ) {
        return component == null ? null : component.getString().replaceAll("§.", "");
    }

    public static @NotNull Component ofChildren(
        final @NotNull Component @NotNull ... children
    ) {
        final MutableComponent result = Component.empty();
        result.getSiblings().addAll(List.of(children));
        return result;
    }
}
