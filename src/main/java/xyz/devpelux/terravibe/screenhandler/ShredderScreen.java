package xyz.devpelux.terravibe.screenhandler;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import xyz.devpelux.terravibe.core.Terravibe;

/**
 * Screen for the {@link ShredderScreenHandler}: handles the rendering of the UI background texture.
 */
public class ShredderScreen extends HandledScreen<ScreenHandler> {
	/**
	 * Identifier of the gui texture.
	 */
	private static final Identifier TEXTURE = new Identifier(Terravibe.ID, "textures/gui/shredder.png");

	/**
	 * Initializes a new {@link ShredderScreen}.
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
		// Centers the title
		titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
	}

	/**
	 * Draws the UI background texture.
	 */
	@Override
	protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, TEXTURE);
		int x = (width - backgroundWidth) / 2;
		int y = (height - backgroundHeight) / 2;
		drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);
	}

	/**
	 * Renders the UI background texture.
	 */
	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		renderBackground(matrices);
		super.render(matrices, mouseX, mouseY, delta);
		drawMouseoverTooltip(matrices, mouseX, mouseY);
	}
}
