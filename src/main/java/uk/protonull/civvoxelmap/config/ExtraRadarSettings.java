package uk.protonull.civvoxelmap.config;

import com.mamiyaotaru.voxelmap.util.Contact;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Predicate;
import net.minecraft.client.player.RemotePlayer;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import uk.protonull.civvoxelmap.mixins.settings.RadarSettingsManagerMixin;

public final class ExtraRadarSettings {
    /** {@link RadarSettingsManagerMixin} */
    public interface Accessor {
        boolean hideElevation();
        void hideElevation(boolean hideElevation);

        boolean hideSneaking();
        void hideSneaking(boolean hideSneaking);

        boolean hideInvisible();
        void hideInvisible(boolean hideInvisible);

        boolean useBetterRadarSort();
        void useBetterRadarSort(boolean useBetterRadarSort);
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

    // Ensures that players are rendered after hostile mobs and other entities
    public static @NotNull Comparator<Contact> radarEntitiesComparator(
        final @NotNull Predicate<Entity> isHostile
    ) {
        return (lhs, rhs) -> {
            final boolean lhsIsPlayer = lhs.entity instanceof RemotePlayer;
            final boolean rhsIsPlayer = rhs.entity instanceof RemotePlayer;
            if (lhsIsPlayer && rhsIsPlayer) {
                return Double.compare(lhs.distance, rhs.distance) * -1;
            }
            else if (lhsIsPlayer) {
                return 1; // Move lhs towards the end
            }
            else if (rhsIsPlayer) {
                return -1; // Move lhs toward the start
            }
            final boolean lhsIsHostile = isHostile.test(lhs.entity);
            final boolean rhsIsHostile = isHostile.test(rhs.entity);
            if (lhsIsHostile && rhsIsHostile) {
                return Double.compare(lhs.distance, rhs.distance) * -1;
            }
            else if (lhsIsHostile) {
                return 1; // Move lhs towards the end
            }
            else if (rhsIsHostile) {
                return -1; // Move lhs toward the start
            }
            return 0;
        };
    }
}
