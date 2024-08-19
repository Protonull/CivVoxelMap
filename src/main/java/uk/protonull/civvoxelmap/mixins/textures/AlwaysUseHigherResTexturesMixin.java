package uk.protonull.civvoxelmap.mixins.textures;

import com.mamiyaotaru.voxelmap.Map;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Map.class)
public abstract class AlwaysUseHigherResTexturesMixin {
    @ModifyVariable(
        method = "drawWaypoint",
        at = @At("HEAD"),
        ordinal = 2,
        argsOnly = true,
        remap = false
    )
    private int cvm$drawWaypoint$scale$neverUseSmallWaypointIcons(
        final int scScale
    ) {
        return Math.min(scScale, 3);
    }

    @ModifyArg(
        method = "drawWaypoint",
        at = @At(
            value = "INVOKE",
            target = "Lcom/mamiyaotaru/voxelmap/textures/TextureAtlas;getAtlasSprite(Ljava/lang/String;)Lcom/mamiyaotaru/voxelmap/textures/Sprite;"
        )
    )
    private @NotNull  String cvm$drawWaypoint$assets$neverUseSmallWaypointIcons(
        final @NotNull String path
    ) {
        return switch (path) {
            case "voxelmap:images/waypoints/markersmall.png" -> "voxelmap:images/waypoints/marker.png";
            case "voxelmap:images/waypoints/markerskullsmall.png" -> "voxelmap:images/waypoints/markerskull.png";
            case "voxelmap:images/waypoints/waypointskullsmall.png" -> "voxelmap:images/waypoints/waypointskull.png";
            case "voxelmap:images/waypoints/waypointsmall.png" -> "voxelmap:images/waypoints/waypoint.png";
            default -> path;
        };
    }
}
