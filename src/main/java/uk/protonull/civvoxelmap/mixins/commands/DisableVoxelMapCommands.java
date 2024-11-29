package uk.protonull.civvoxelmap.mixins.commands;

import com.mamiyaotaru.voxelmap.VoxelConstants;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(VoxelConstants.class)
public abstract class DisableVoxelMapCommands {
    /**
     * @author Protonull
     * @reason VoxelMap's default command handling is prone to error. It is replaced with {@link ReplaceVoxelMapCommands}!
     */
    @Overwrite(remap = false)
    public static boolean onSendChatMessage(
        final @NotNull String message
    ) {
        return true;
    }
}
