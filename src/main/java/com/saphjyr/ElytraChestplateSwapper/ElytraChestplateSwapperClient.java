package com.saphjyr.ElytraChestplateSwapper;



import net.fabricmc.api.ClientModInitializer;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

public class ElytraChestplateSwapperClient implements ClientModInitializer {
    
    public static SwapKeyBinding keyBinding;
    @Override
    public void onInitializeClient() {
        keyBinding = new SwapKeyBinding();
        KeyBindingHelper.registerKeyBinding(keyBinding);
    }

    
}