package xyz.beefox.shrinkydink;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShrinkyDink implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("modid");

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		AttackEntityCallback.EVENT.register((player, world, hand, entity, result)->{

			if(player.getMainHandStack().isOf(Registries.ITEM.get(new Identifier("minecraft:milk_bucket")))){
				if(entity instanceof PassiveEntity 
				|| entity instanceof ZombieEntity 
				|| entity instanceof PiglinEntity){
					if(entity instanceof PassiveEntity){
						((PassiveEntity) entity).setBaby(true);
					} else if(entity instanceof ZombieEntity){
						((ZombieEntity) entity).setBaby(true);
					} else if(entity instanceof PiglinEntity){
						((PiglinEntity) entity).setBaby(true);
					}
					world.playSound(null, ((LivingEntity)entity).getBlockPos(), SoundEvents.ITEM_BUCKET_EMPTY , SoundCategory.NEUTRAL);
					if(!player.isCreative()){
						player.setStackInHand(hand, new ItemStack(Registries.ITEM.get(new Identifier("minecraft:bucket")), 1));
					}
					return ActionResult.SUCCESS;
				}
			}
			return ActionResult.PASS;
		});

	}
}
