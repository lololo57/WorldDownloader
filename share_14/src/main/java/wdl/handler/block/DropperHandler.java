/*
 * This file is part of World Downloader: A mod to make backups of your
 * multiplayer worlds.
 * http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/2520465
 *
 * Copyright (c) 2014 nairol, cubic72
 * Copyright (c) 2017-2018 Pokechu22, julialy
 *
 * This project is licensed under the MMPLv2.  The full text of the MMPL can be
 * found in LICENSE.md, or online at https://github.com/iopleke/MMPLv2/blob/master/LICENSE.md
 * For information about this the MMPLv2, see http://stopmodreposts.org/
 *
 * Do not redistribute (in modified or unmodified form) without prior permission.
 */
package wdl.handler.block;

import java.util.function.BiConsumer;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.DispenserContainer;
import net.minecraft.tileentity.DropperTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import wdl.ReflectionUtils;
import wdl.handler.HandlerException;

public class DropperHandler extends BlockHandler<DropperTileEntity, DispenserContainer> {
	public DropperHandler() {
		super(DropperTileEntity.class, DispenserContainer.class, "container.dropper");
	}

	@Override
	public ITextComponent handle(BlockPos clickedPos, DispenserContainer container,
			DropperTileEntity blockEntity, IBlockReader world,
			BiConsumer<BlockPos, DropperTileEntity> saveMethod) throws HandlerException {
		IInventory dropperInventory = ReflectionUtils.findAndGetPrivateField(
				container, IInventory.class);
		String title = getCustomDisplayName(dropperInventory);
		saveContainerItems(container, blockEntity, 0);
		saveMethod.accept(clickedPos, blockEntity);
		if (title != null) {
			blockEntity.setCustomName(customName(title));
		}
		return new TranslationTextComponent("wdl.messages.onGuiClosedInfo.savedTileEntity.dropper");
	}
}
