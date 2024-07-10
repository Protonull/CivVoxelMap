package uk.protonull.civvoxelmap;

import org.jetbrains.annotations.NotNull;

public final class Helpers {
    @SuppressWarnings("unchecked")
    public static <T> @NotNull T hardCast(
        final @NotNull Object object
    ) {
        return (T) (Object) object;
    }
}
