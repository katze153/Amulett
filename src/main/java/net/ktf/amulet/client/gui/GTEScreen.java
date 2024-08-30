package net.ktf.amulet.client.gui;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;

import net.ktf.amulet.world.inventory.GTEMenu;
import net.ktf.amulet.network.GTEButtonMessage;
import net.ktf.amulet.AmuletMod;

import java.util.HashMap;

import com.mojang.blaze3d.systems.RenderSystem;

public class GTEScreen extends AbstractContainerScreen<GTEMenu> {
	private final static HashMap<String, Object> guistate = GTEMenu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	Button button_m;

	public GTEScreen(GTEMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 176;
		this.imageHeight = 166;
	}

	private static final ResourceLocation texture = new ResourceLocation("amulet:textures/screens/gte.png");

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(guiGraphics);
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int gx, int gy) {
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		guiGraphics.blit(texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

		guiGraphics.blit(new ResourceLocation("amulet:textures/screens/pluss.png"), this.leftPos + 75, this.topPos + 19, 0, 0, 16, 16, 16, 16);

		RenderSystem.disableBlend();
	}

	@Override
	public boolean keyPressed(int key, int b, int c) {
		if (key == 256) {
			this.minecraft.player.closeContainer();
			return true;
		}
		return super.keyPressed(key, b, c);
	}

	@Override
	public void containerTick() {
		super.containerTick();
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
	}

	@Override
	public void init() {
		super.init();
		button_m = Button.builder(Component.translatable("gui.amulet.gte.button_m"), e -> {
			if (true) {
				AmuletMod.PACKET_HANDLER.sendToServer(new GTEButtonMessage(0, x, y, z));
				GTEButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 67, this.topPos + 39, 30, 20).build();
		guistate.put("button:button_m", button_m);
		this.addRenderableWidget(button_m);
	}
}
