package xyz.devpelux.terravibe.screenhandler;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import xyz.devpelux.terravibe.core.Terravibe;

/**
 * Screen for the shredder block UI: handles the rendering of the UI background texture.
 */
public class ShredderScreen extends HandledScreen<ScreenHandler> {
	/**
	 * Identifier of the gui texture.
	 */
	private static final Identifier TEXTURE = Terravibe.identified("textures/gui/shredder.png");

	/**
	 * Initializes a new instance.
	 */
	public ShredderScreen(ScreenHandler handler, PlayerInventory inventory, Text title) {
		super(handler, inventory, title);
	}

	/**
	 * Initializes the component.
	 */
	@Override
	protected void init() {
		super.init();
		//Centers the title.
		titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
	}

	/**
	 * Renders the UI background texture.
	 */
	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		renderBackground(context);
		super.render(context, mouseX, mouseY, delta);
		drawMouseoverTooltip(context, mouseX, mouseY);
	}

	/**
	 * Draws the UI background texture.
	 */
	@Override
	protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
		//Sets the shader, color, texture, then draws the texture at the center of the display.
		RenderSystem.setShader(GameRenderer::getPositionTexProgram);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, TEXTURE);
		int x = (width - backgroundWidth) / 2;
		int y = (height - backgroundHeight) / 2;
		context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
	}
}
