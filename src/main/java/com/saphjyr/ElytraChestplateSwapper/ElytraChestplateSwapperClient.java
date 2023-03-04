package com.saphjyr.ElytraChestplateSwapper;

import org.lwjgl.glfw.GLFW;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

public class ElytraChestplateSwapperClient implements ClientModInitializer {
    
    public static SwapKeyBinding keyBinding;
    
    @Override
    public void onInitializeClient() {

        // Create and log the key binding
        keyBinding = new SwapKeyBinding("key.ecs.swap", GLFW.GLFW_KEY_GRAVE_ACCENT, "category.ecs");
        KeyBindingHelper.registerKeyBinding(keyBinding);

        
        
    }

    
}