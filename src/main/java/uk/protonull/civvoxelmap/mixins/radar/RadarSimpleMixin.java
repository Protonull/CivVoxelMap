package uk.protonull.civvoxelmap.mixins.radar;

import com.mamiyaotaru.voxelmap.RadarSettingsManager;
import com.mamiyaotaru.voxelmap.RadarSimple;
import com.mamiyaotaru.voxelmap.util.Contact;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import uk.protonull.civvoxelmap.features.config.ExtraRadarSettings;

@Mixin(RadarSimple.class)
public abstract class RadarSimpleMixin {
    @Final
    @Shadow(remap = false)
    public RadarSettingsManager options;

    @ModifyVariable(
        method = "calculateMobs",
        at = @At(
            value = "STORE",
            target = "Ljava/lang/Iterable;iterator()Ljava/util/Iterator;"
        ),
        remap = false
    )
    private @NotNull Iterator<Entity> civvoxelmap$filterEntities(
        final @NotNull Iterator<Entity> iterator
    ) {
        return ExtraRadarSettings.filterEntities(iterator, (ExtraRadarSettings.Accessor) this.options);
    }

    @Shadow
    protected abstract boolean isHostile(
        @NotNull Entity entity
    );

    @Redirect(
        method = "calculateMobs",
        at = @At(
            value = "INVOKE",
            target = "Ljava/util/ArrayList;sort(Ljava/util/Comparator;)V"
        ),
        remap = false
    )
    private void civvoxelmap$sortEntities(
        final @NotNull ArrayList<Contact> entities,
        final @NotNull Comparator<Contact> elevationComparator
    ) {
        final var extra = (ExtraRadarSettings.Accessor) this.options;

        if (!extra.hideElevation()) {
            entities.sort(elevationComparator);
        }

        if (extra.useBetterRadarSort()) {
            entities.sort(ExtraRadarSettings.radarEntitiesComparator(this::isHostile));
        }
    }
}
