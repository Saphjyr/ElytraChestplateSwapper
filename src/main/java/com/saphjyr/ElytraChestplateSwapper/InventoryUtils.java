package com.saphjyr.ElytraChestplateSwapper;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.SlotActionType;

public class InventoryUtils {
    public static void swapChestplate(MinecraftClient client){

        // Slots to swap to
        int elytraSlot = -1;
        int chestplateSlot = -1;

        // List inventory slots, in a special order so the selected slot is the most top-left chestplate
        int[] range = new int[37];
        for (int i = 0; i<9; i++) range[i] = 8-i;
        for (int i = 9; i<36; i++) range[i] = 35-(i-9);
        range[36] = 40; // Off hand

        // Find elytraSlot and chestplateSlot
        for(int slot : range) {

            ItemStack stack = client.player.getInventory().getStack(slot);
            
            if(stack.isEmpty() || !(stack.getItem() instanceof ArmorItem || stack.getItem() instanceof ElytraItem))
                continue;
            if (stack.getItem() instanceof ElytraItem) {
                elytraSlot = slot;
                continue;
            }

            ArmorItem armorItem = (ArmorItem)stack.getItem();
            if (armorItem.getSlotType() == EquipmentSlot.CHEST) {
                chestplateSlot = slot;
            }
        }

        // Swap with slot depending on currently weard item
        ItemStack wearedItemStack =  client.player.getInventory().getStack(38);
        if (wearedItemStack.isEmpty() && elytraSlot >= 0) {
            swap(elytraSlot, client);
        }
        else if (wearedItemStack.getItem() instanceof ElytraItem && chestplateSlot >=0) {
            swap(chestplateSlot, client);
        }
        else if (wearedItemStack.getItem() instanceof ArmorItem && elytraSlot >=0) {
            swap(elytraSlot, client);
        }
        
    }

    private static void swap(int slot, MinecraftClient client) {
        int slot2 = slot;
        if (slot2 == 40) slot2 = 45; // Off Hand offset
        if(slot2 < 9) slot2 += 36;   // Toolbar offset
        

        // Take the item to swap to
        client.interactionManager.clickSlot(0, slot2, 0, SlotActionType.PICKUP, client.player);

        // Put it in the armor slot
        client.interactionManager.clickSlot(0, 6, 0, SlotActionType.PICKUP, client.player);

        // Put back what was in the armor slot (can be air)
        client.interactionManager.clickSlot(0, slot2, 0, SlotActionType.PICKUP, client.player);
    }
}
