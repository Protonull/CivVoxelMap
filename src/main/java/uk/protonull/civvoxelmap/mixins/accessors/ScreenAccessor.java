package uk.protonull.civvoxelmap.mixins.accessors;

import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Screen.class)
public interface ScreenAccessor {
    @Invoker("addRenderableWidget")
    <T extends GuiEventListener & Renderable & NarratableEntry> @NotNull T cvm_invoker$addRenderableWidget(
        @NotNull T widget
    );

    @Invoker("clearWidgets")
    void cvm_invoker$clearWidgets();

    @Invoker("clearFocus")
    void cvm_invoker$clearFocus();
}
