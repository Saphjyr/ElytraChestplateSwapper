package com.saphjyr.ElytraChestplateSwapper;


import org.lwjgl.glfw.GLFW;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.LiteralText;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.client.MinecraftClient;
public class ElytraChestplateSwapperClient implements ClientModInitializer {
    
    // The KeyBinding declaration and registration are commonly executed here statically
    private static KeyBinding keyBinding;
    private Inventory inv;
    @Override
    public void onInitializeClient() {
        
        // Event registration will be executed inside this method
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.ecs.swap", // The translation key of the keybinding's name
            InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
            GLFW.GLFW_KEY_GRAVE_ACCENT, // The keycode of the key
            "category.ecs" // The translation key of the keybinding's category.
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            
            while (keyBinding.wasPressed()) {
                inv = MinecraftClient.getInstance().player.inventory;
                client.player.sendMessage(new LiteralText("Key 1 was pressed!" + inv.getStack(1).toString()), false);
                inv.removeStack(1, 3);
                ItemStack temp = inv.getStack(1).copy();
                temp.setCount(3);
                inv.setStack(2, temp);

            }
        });
    }
}