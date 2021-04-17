package com.saphjyr.ElytraChestplateSwapper;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.InputUtil.Key;

/**
 * SwapKeyBinding
 */
public class SwapKeyBinding extends KeyBinding {

    private Key key;

    private boolean pressedBypass;

    public SwapKeyBinding(String translationKey, int defaultkey, String category) {
        super(
            translationKey, // The translation key of the keybinding's name
            InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
            defaultkey, // The keycode of the key
            category // The translation key of the keybinding's category.
        );
        key = this.getDefaultKey();
        pressedBypass = false;
    }


    @Override
    public void setBoundKey(Key boundKey) {
        key = boundKey;
        super.setBoundKey(boundKey);
    }

    public Key getKey() {
        return key;
    }

    public boolean isPressedBypass() {
        return pressedBypass;
    }

    public void setPressedBypass(boolean pressed) {
        pressedBypass = pressed;
        if (pressed) InventoryUtils.swapChestplate(MinecraftClient.getInstance());
    }
    
}