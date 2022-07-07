package xyz.devpelux.terravibe.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.devpelux.terravibe.blockentity.ShredderBlockEntity;
import xyz.devpelux.terravibe.core.ModInfo;
import xyz.devpelux.terravibe.core.Util;

/** Shreds a bunch of items to obtain something. */
public class ShredderBlock extends BlockWithEntity {
	/** Identifier of the block. */
	public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "shredder");

	/** Settings of the block. */
	public static final Settings SETTINGS;

	/** Orientation of the block. */
	public static final DirectionProperty FACING;

	/** Voxel shape of the block. */
	private static final VoxelShape VOXEL_SHAPE;

	/** Initializes a new {@link ShredderBlock}. */
	public ShredderBlock(Settings settings) {
		super(settings);
		setDefaultState(getStateManager().getDefaultState().with(FACING, Direction.NORTH));
	}

	/** Registers the properties of the block. */
	@Override
	protected void appendProperties(StateManager.@NotNull Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(FACING);
	}

	/**
	 * Executed when the block is used.
	 * Displays the UI of the block.
	 */
	@Override
	public ActionResult onUse(BlockState state, @NotNull World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (!world.isClient) {
			//Creates the screen handler for the UI.
			NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);

			if (screenHandlerFactory != null) {
				//Displays the UI.
				player.openHandledScreen(screenHandlerFactory);
			}
		}
		return ActionResult.SUCCESS;
	}

	/** Creates the block entity for the block. */
	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new ShredderBlockEntity(pos, state);
	}

	/** Gets the render type of the block. */
	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	/** Gets the placement state of the block. */
	@Override
	public BlockState getPlacementState(@NotNull ItemPlacementContext ctx) {
		//When the player places the block, gets the opposite direction.
		return this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
	}

	/** Rotates the block in the specified direction. */
	@Override
	public BlockState rotate(@NotNull BlockState state, @NotNull BlockRotation rotation) {
		return state.with(FACING, rotation.rotate(state.get(FACING)));
	}

	/** Mirrors the block in the specified direction. */
	@Override
	public BlockState mirror(@NotNull BlockState state, @NotNull BlockMirror mirror) {
		return state.rotate(mirror.getRotation(state.get(FACING)));
	}

	/** Gets the outline shape of the block. */
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return VOXEL_SHAPE;
	}

	static {
		SETTINGS = FabricBlockSettings.of(Material.METAL, MapColor.ORANGE)
				.requiresTool()
				.strength(3.0F, 6.0F)
				.sounds(BlockSoundGroup.COPPER);
		FACING = Properties.HORIZONTAL_FACING;
		VOXEL_SHAPE = Util.combineVoxelShapes(
				Block.createCuboidShape(2, 14, 2, 14, 16, 14),
				Block.createCuboidShape(3, 12, 3, 13, 14, 13),
				Block.createCuboidShape(4, 0, 4, 12, 12, 12)
		);
	}
}
