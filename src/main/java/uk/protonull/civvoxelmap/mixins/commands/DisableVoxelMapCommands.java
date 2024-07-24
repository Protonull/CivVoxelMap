package uk.protonull.civvoxelmap.mixins.commands;

import com.mamiyaotaru.voxelmap.fabricmod.FabricModVoxelMap;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(FabricModVoxelMap.class)
public abstract class DisableVoxelMapCommands {
    /**
     * @author Protonull
     * @reason VoxelMap's default command handling is prone to error. It is replaced with {@link ReplaceVoxelMapCommands}!
     */
    @Overwrite(remap = false)
    public boolean onSendChatMessage(
        final @NotNull String message
    ) {
        return true;
    }
}
