package xyz.devpelux.terravibe.item;

import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/** Item with a color provider. */
public class ColoredItem extends Item implements ItemColorProvider {
    /** Color provider. */
    private final ItemColorProvider provider;

    /** Initializes a new {@link ColoredItem}. */
    public ColoredItem(Settings settings, @NotNull ItemColorProvider provider) {
        super(settings);
        this.provider = provider;
    }

    /** Gets the color of the item. */
    @Override
    public int getColor(ItemStack stack, int tintIndex) {
        return provider.getColor(stack, tintIndex);
    }
}
