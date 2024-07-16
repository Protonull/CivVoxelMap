package uk.protonull.civvoxelmap.mixins.commands;

import com.mamiyaotaru.voxelmap.fabricmod.FabricModVoxelMap;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(FabricModVoxelMap.class)
public abstract class DisableVoxelMapCommands {
    //@Override
    public boolean onSendChatMessage(
        final @NotNull String message
    ) {
        return true;
    }
}
