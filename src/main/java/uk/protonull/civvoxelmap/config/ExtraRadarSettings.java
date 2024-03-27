package uk.protonull.civvoxelmap.config;

import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public final class ExtraRadarSettings {
    /** {@link uk.protonull.civvoxelmap.mixins.RadarSettingsManagerMixin} */
    public interface Accessor {
        boolean hideElevation();
        void hideElevation(boolean hideElevation);

        boolean hideSneaking();
        void hideSneaking(boolean hideSneaking);

        boolean hideInvisible();
        void hideInvisible(boolean hideInvisible);
    }

    public static @NotNull Iterator<Entity> filterEntities(
        final @NotNull Iterator<Entity> iterator,
        final @NotNull Accessor extra
    ) {
        final var entities = new ArrayList<Entity>();
        while (iterator.hasNext()) {
            final Entity entity = iterator.next();
            if (extra.hideSneaking() && entity.isCrouching()) {
                continue;
            }
            if (extra.hideInvisible() && entity.isInvisible()) {
                continue;
            }
            entities.add(entity);
        }
        return entities.iterator();
    }
}
