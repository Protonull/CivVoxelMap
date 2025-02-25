package uk.protonull.civvoxelmap.mixins.radar;

import com.llamalad7.mixinextras.sugar.Local;
import com.mamiyaotaru.voxelmap.Radar;
import com.mamiyaotaru.voxelmap.RadarSettingsManager;
import com.mamiyaotaru.voxelmap.util.Contact;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import uk.protonull.civvoxelmap.CivVoxelMapUtils;
import uk.protonull.civvoxelmap.features.config.ExtraRadarSettings;

@Mixin(Radar.class)
public abstract class RadarMixin {
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

    @Redirect(
        method = "calculateMobs",
        at = @At(
            value = "INVOKE",
            target = "Lcom/mamiyaotaru/voxelmap/util/Contact;setName(Ljava/lang/String;)V"
        ),
        remap = false
    )
    protected void civvoxelmap$betterSetContactName(
        final @NotNull Contact contact,
        final String ignored
    ) {
        contact.name = StringUtils.defaultIfEmpty(switch (contact.entity) {
            case final Player player -> CivVoxelMapUtils.getPlainContents(player.getName());
            case final Entity entity -> CivVoxelMapUtils.getPlainContents(entity.getCustomName());
            case null -> null;
        }, null);
    }

    /**
     * For some reason, the target method doesn't use "contact.name" despite testing whether it's null. This resets the
     * "name" variable to use the "contact.name" field, instead of the entity's display name.
     */
    @ModifyVariable(
        method = "renderMapMobs",
        at = @At("STORE")
    )
    protected String civvoxelmap$getNameThatWasSetEarlier(
        final String ignored,
        final @Local @NotNull Contact contact
    ) {
        return contact.name;
    }
}
