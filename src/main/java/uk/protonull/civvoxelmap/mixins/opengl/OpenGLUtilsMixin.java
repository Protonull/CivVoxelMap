package uk.protonull.civvoxelmap.mixins.opengl;

import com.mamiyaotaru.voxelmap.util.OpenGL;
import org.lwjgl.opengl.GL30;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(OpenGL.Utils.class)
public abstract class OpenGLUtilsMixin {
    @ModifyConstant(
        method = "setupFramebuffer",
        constant = @Constant(intValue = GL30.GL_DRAW_FRAMEBUFFER),
        remap = false
    )
    private static int cvm_modify_constant$useCorrectTarget(
        final int value
    ) {
        // Apparently, the only valid target is GL_RENDERBUFFER
        // https://docs.gl/gl4/glBindRenderbuffer
        return GL30.GL_RENDERBUFFER;
    }
}
