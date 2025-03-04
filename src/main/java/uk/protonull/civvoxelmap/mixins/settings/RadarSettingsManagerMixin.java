package uk.protonull.civvoxelmap.mixins.settings;

import com.mamiyaotaru.voxelmap.RadarSettingsManager;
import java.io.PrintWriter;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.protonull.civvoxelmap.features.config.ExtraRadarSettings;

@Mixin(RadarSettingsManager.class)
public abstract class RadarSettingsManagerMixin implements ExtraRadarSettings.Accessor {
    // ============================================================
    // Defaults
    // ============================================================

    @Shadow(remap = false)
    public boolean showHelmetsPlayers;
    @Shadow(remap = false)
    public boolean showHelmetsMobs;

    @Inject(
        method = "<init>",
        at = @At("TAIL"),
        remap = false
    )
    private void civvoxelmap$setExtraSettingDefaults(
        final @NotNull CallbackInfo ci
    ) {
        this.showHelmetsPlayers = false;
        this.showHelmetsMobs = false;
    }

    // ============================================================
    // Custom Options
    // ============================================================

    @Unique
    private static final String HIDE_ELEVATION_KEY = "HideElevation";
    @Unique
    private static final String HIDE_SNEAKING_KEY = "HideSneaking";
    @Unique
    private static final String HIDE_INVISIBLE_KEY = "HideInvisible";
    @Unique
    private static final String RADAR_SORT_KEY = "BetterRadarSort";

    @Unique
    private boolean civvoxelmap$hideElevation = true;
    @Unique
    private boolean civvoxelmap$hideSneaking = false;
    @Unique
    private boolean civvoxelmap$hideInvisible = false;
    @Unique
    private boolean civvoxelmap$useBetterRadarSort = true;

    @Unique
    @Override
    public boolean hideElevation() {
        return this.civvoxelmap$hideElevation;
    }

    @Unique
    @Override
    public void hideElevation(
        final boolean hideElevation
    ) {
        this.civvoxelmap$hideElevation = hideElevation;
    }

    @Unique
    @Override
    public boolean hideSneaking() {
        return this.civvoxelmap$hideSneaking;
    }

    @Unique
    @Override
    public void hideSneaking(
        final boolean hideSneaking
    ) {
        this.civvoxelmap$hideSneaking = hideSneaking;
    }

    @Unique
    @Override
    public boolean hideInvisible() {
        return this.civvoxelmap$hideInvisible;
    }

    @Unique
    @Override
    public void hideInvisible(
        final boolean hideInvisible
    ) {
        this.civvoxelmap$hideInvisible = hideInvisible;
    }

    @Unique
    @Override
    public boolean useBetterRadarSort() {
        return this.civvoxelmap$useBetterRadarSort;
    }

    @Unique
    @Override
    public void useBetterRadarSort(
        final boolean useBetterRadarSort
    ) {
        this.civvoxelmap$useBetterRadarSort = useBetterRadarSort;
    }

    @ModifyVariable(
        method = "loadSettings",
        at = @At(
            value = "STORE",
            target = "Ljava/lang/String;split(Ljava/lang/String;)[Ljava/lang/String;"
        ),
        remap = false
    )
    private @NotNull String @NotNull [] civvoxelmap$loadExtraSettings(
        final @NotNull String @NotNull [] row
    ) {
        if (HIDE_ELEVATION_KEY.equals(row[0])) {
            hideElevation(Boolean.parseBoolean(row[1]));
            return new String[] { "", "" };
        }
        if (HIDE_SNEAKING_KEY.equals(row[0])) {
            hideSneaking(Boolean.parseBoolean(row[1]));
            return new String[] { "", "" };
        }
        if (HIDE_INVISIBLE_KEY.equals(row[0])) {
            hideInvisible(Boolean.parseBoolean(row[1]));
            return new String[] { "", "" };
        }
        if (RADAR_SORT_KEY.equals(row[0])) {
            useBetterRadarSort(Boolean.parseBoolean(row[1]));
            return new String[] { "", "" };
        }
        return row;
    }

    @ModifyVariable(
        method = "saveAll",
        at = @At("TAIL"),
        argsOnly = true,
        remap = false
    )
    private @NotNull PrintWriter civvoxelmap$saveExtraSettings(
        final @NotNull PrintWriter out
    ) {
        out.println(HIDE_ELEVATION_KEY + ":" + hideElevation());
        out.println(HIDE_SNEAKING_KEY + ":" + hideSneaking());
        out.println(HIDE_INVISIBLE_KEY + ":" + hideInvisible());
        out.println(RADAR_SORT_KEY + ":" + useBetterRadarSort());
        return out;
    }
}
