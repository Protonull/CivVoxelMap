package uk.protonull.civvoxelmap.mixins.policies;

import com.mamiyaotaru.voxelmap.RadarSettingsManager;
import com.mamiyaotaru.voxelmap.VoxelConstants;
import com.mamiyaotaru.voxelmap.packets.VoxelmapSettingsS2C;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(VoxelmapSettingsS2C.class)
public abstract class PacketPolicyContraventionMixin {
	@Redirect(
		method = "parsePacket",
		at = @At(
			value = "FIELD",
			target = "Lcom/mamiyaotaru/voxelmap/RadarSettingsManager;radarAllowed:Z",
			opcode = Opcodes.PUTFIELD
		),
		remap = false
	)
	private static void civvoxelmap$remapRadarAllowedPolicy(
		final @NotNull RadarSettingsManager instance,
		final boolean radarAllowed
	) {
		VoxelConstants.getLogger().info("Server packet-deny radarAllowed policy ignored!");
	}

	@Redirect(
		method = "parsePacket",
		at = @At(
			value = "FIELD",
			target = "Lcom/mamiyaotaru/voxelmap/RadarSettingsManager;radarPlayersAllowed:Z",
			opcode = Opcodes.PUTFIELD
		),
		remap = false
	)
	private static void civvoxelmap$remapRadarPlayersAllowedPolicy(
		final @NotNull RadarSettingsManager instance,
		final boolean radarPlayersAllowed
	) {
		VoxelConstants.getLogger().info("Server packet-deny radarPlayersAllowed policy ignored!");
	}

	@Redirect(
		method = "parsePacket",
		at = @At(
			value = "FIELD",
			target = "Lcom/mamiyaotaru/voxelmap/RadarSettingsManager;radarMobsAllowed:Z",
			opcode = Opcodes.PUTFIELD
		),
		remap = false
	)
	private static void civvoxelmap$remapRadarMobsAllowedPolicy(
		final @NotNull RadarSettingsManager instance,
		final boolean radarMobsAllowed
	) {
		VoxelConstants.getLogger().info("Server packet-deny radarMobsAllowed policy ignored!");
	}
}
