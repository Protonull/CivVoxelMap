package uk.protonull.civvoxelmap.mixins.policies;

import com.mamiyaotaru.voxelmap.VoxelConstants;
import com.mamiyaotaru.voxelmap.VoxelMap;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(VoxelMap.class)
public abstract class LegacyPolicyContraventionMixin {
	@Unique
	private static final String DISABLE_RADAR_POLICY = "§3 §6 §3 §6 §3 §6 §e";

	@Redirect(
		method = "checkPermissionMessages",
		at = @At(
			value = "INVOKE",
			target = "Ljava/lang/String;contains(Ljava/lang/CharSequence;)Z",
			ordinal = 1
		),
		remap = false
	)
	private static boolean civvoxelmap$contraveneServerRadarPolicy(
		final @NotNull String message,
		final @NotNull CharSequence contained
	) {
		if (message.contains(DISABLE_RADAR_POLICY)) {
			VoxelConstants.getLogger().info("Server legacy-deny-all radar policy ignored!");
		}
		return false;
	}
}
