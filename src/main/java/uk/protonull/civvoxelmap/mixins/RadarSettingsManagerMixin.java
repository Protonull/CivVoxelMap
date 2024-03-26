package uk.protonull.civvoxelmap.mixins;

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
import uk.protonull.civvoxelmap.config.ExtraRadarSettings;

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
    private void cvm_inject$constructor(
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
    private boolean cvm_unique$hideElevation = true;
    @Unique
    private boolean cvm_unique$hideSneaking = false;

    @Unique
    @Override
    public boolean hideElevation() {
        return this.cvm_unique$hideElevation;
    }

    @Unique
    @Override
    public void hideElevation(
        final boolean hideElevation
    ) {
        this.cvm_unique$hideElevation = hideElevation;
    }

    @Unique
    @Override
    public boolean hideSneaking() {
        return this.cvm_unique$hideSneaking;
    }

    @Unique
    @Override
    public void hideSneaking(
        final boolean hideSneaking
    ) {
        this.cvm_unique$hideSneaking = hideSneaking;
    }

    @ModifyVariable(
        method = "loadSettings",
        at = @At(
            value = "STORE",
            target = "Ljava/lang/String;split(Ljava/lang/String;)[Ljava/lang/String;"
        ),
        remap = false
    )
    private @NotNull String @NotNull [] cvm_modify_variable$loadExtraSettings(
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
        return row;
    }

    @ModifyVariable(
        method = "saveAll",
        at = @At("TAIL"),
        argsOnly = true,
        remap = false
    )
    private @NotNull PrintWriter cvm_modify_variable$saveExtraSettings(
        final @NotNull PrintWriter out
    ) {
        out.println(HIDE_ELEVATION_KEY + ":" + hideElevation());
        out.println(HIDE_SNEAKING_KEY + ":" + hideSneaking());
        return out;
    }
}
