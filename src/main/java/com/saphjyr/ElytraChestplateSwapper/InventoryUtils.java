package com.saphjyr.ElytraChestplateSwapper;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.screen.slot.SlotActionType;

public class InventoryUtils {
    public static void swapChestplate(MinecraftClient client){
                
        // Slots to swap to
        int elytraSlot = -1;
        int chestplateSlot = -1;

        // List inventory slots
        int[] range = new int[36];
        for (int i = 0; i<9; i++) range[i] = 8-i;
        for (int i = 9; i<36; i++) range[i] = 35-(i-9);

        // Find elytraSlot and chestplateSlot
        for(int slot : range) {

            ItemStack stack = client.player.inventory.getStack(slot);
            
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
        ItemStack wearedItemStack =  client.player.inventory.getStack(38);
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
        if(slot2 < 9) slot2 += 36;

        // Take the item to swap to
        ClickSlotC2SPacket packet1 = new ClickSlotC2SPacket(0, slot2, 0, SlotActionType.PICKUP, client.player.inventory.getStack(slot), (short)0);
        client.getNetworkHandler().sendPacket(packet1);

        // Put it in the armor slot
        ClickSlotC2SPacket packet2 = new ClickSlotC2SPacket(0, 6, 0, SlotActionType.PICKUP, client.player.inventory.getStack(38), (short)0);
        client.getNetworkHandler().sendPacket(packet2);

        // Put back what was in the armor slot (can be air)
        ClickSlotC2SPacket packet3 = new ClickSlotC2SPacket(0, slot2, 0, SlotActionType.PICKUP, client.player.inventory.getStack(slot), (short)0);
        client.getNetworkHandler().sendPacket(packet3);
    }
}
