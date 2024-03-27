package uk.protonull.civvoxelmap.config;

import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public final class ExtraRadarSettings {
    public interface Accessor {
        boolean hideElevation();
        void hideElevation(boolean hideElevation);

        boolean hideSneaking();
        void hideSneaking(boolean hideSneaking);
    }

    public static @NotNull Iterator<Entity> filterOutSneakingPlayers(
        final @NotNull Iterator<Entity> iterator,
        final @NotNull Accessor extra
    ) {
        if (!extra.hideSneaking()) {
            return iterator;
        }
        final var entities = new ArrayList<Entity>();
        while (iterator.hasNext()) {
            final Entity entity = iterator.next();
            if (entity instanceof final Player player && player.isCrouching()) {
                continue;
            }
            entities.add(entity);
        }
        return entities.iterator();
    }
}
