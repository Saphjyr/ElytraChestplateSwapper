package com.saphjyr.ElytraChestplateSwapper;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;
import net.fabricmc.api.Environment;

public class InventoryUtils {

    @Environment(net.fabricmc.api.EnvType.CLIENT)
    public static void swapChestplate(MinecraftClient client){

        // Check if client.player is not null
        if (client.player == null) return;

        // Check if it is a player
        if (!(client.player instanceof ClientPlayerEntity)) return;

        // Check if is it not dead
        if (client.player.isDead()) return;

        // Slots to swap to
        int elytraSlot = -1;
        int chestplateSlot = -1;

        int HOTBAR_SIZE = PlayerInventory.getHotbarSize(); // 9
        int MAIN_SIZE = PlayerInventory.MAIN_SIZE; // 36
        int TOTAL_SIZE = MAIN_SIZE + 1; // 37

        // List inventory slots, in a special order so the selected slot is the most top-left chestplate : 9 - 35 40 0 - 8
        int[] range = new int[TOTAL_SIZE];

        // Main inventory
        for (int i = 0; i < MAIN_SIZE - HOTBAR_SIZE; i++) {
            range[i] = i + HOTBAR_SIZE;
        }

        // Off hand
        range[MAIN_SIZE - HOTBAR_SIZE] = PlayerInventory.OFF_HAND_SLOT; // 40

        // Hotbar
        for (int i = 0; i < HOTBAR_SIZE; i++) {
            range[i + MAIN_SIZE - HOTBAR_SIZE + 1] = i;
        }

        // Find elytraSlot and chestplateSlot
        for(int slot : range) {

            // Get the itemstack in the slot
            ItemStack stack = client.player.getInventory().getStack(slot);

            // Check if the itemstack is empty
            if (stack.isEmpty()) {
                continue;
            } 
            else {

                // Check if the item is an elytra item
                if (isElytra(stack) && elytraSlot < 0) {
                    elytraSlot = slot;
                }

                // Check if the item is a chestplate item
                else if (isChestplate(stack) && chestplateSlot < 0) {
                    chestplateSlot = slot;
                }
            }


        }

        // Get the itemstack in the chestplate slot
        ItemStack wearedItemStack =  client.player.getInventory().getStack(38);
        if (wearedItemStack.isEmpty() && elytraSlot >= 0) {
            sendSwapPackets(elytraSlot, client);
        }
        else if (isElytra(wearedItemStack) && chestplateSlot >=0) {
            sendSwapPackets(chestplateSlot, client);
        }
        else if (isChestplate(wearedItemStack) && elytraSlot >=0) {
            sendSwapPackets(elytraSlot, client);
        }
        
    }

    private static boolean isElytra(ItemStack stack) {
        return stack.getItem() == Items.ELYTRA;
    }

    private static boolean isChestplate(ItemStack stack) {
        boolean isChestplate = false;
        if (stack.getItem() instanceof ArmorItem) {
            if (stack.getItem().getDefaultStack().getName().getString().toLowerCase().contains("chestplate")) {
                isChestplate = true;
            }
        }
        return isChestplate;
    }

    private static void sendSwapPackets(int slot, MinecraftClient client) {
        int sentSlot = slot;
        if (sentSlot == PlayerInventory.OFF_HAND_SLOT) sentSlot += 5; // Off Hand offset
        if(sentSlot < PlayerInventory.getHotbarSize()) sentSlot += PlayerInventory.MAIN_SIZE;   // Toolbar offset
        

        // Take the item to swap to
        client.interactionManager.clickSlot(0, sentSlot, 0, SlotActionType.PICKUP, client.player);

        // Put it in the armor slot
        client.interactionManager.clickSlot(0, 6, 0, SlotActionType.PICKUP, client.player);

        // Put back what was in the armor slot (can be air)
        client.interactionManager.clickSlot(0, sentSlot, 0, SlotActionType.PICKUP, client.player);
    }


}
