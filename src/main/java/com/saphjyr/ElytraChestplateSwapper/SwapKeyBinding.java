package com.saphjyr.ElytraChestplateSwapper;

import org.lwjgl.glfw.GLFW;

import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.InputUtil.Key;

/**
 * SwapKeyBinding
 */
public class SwapKeyBinding extends KeyBinding {

    private Key key;

    private boolean pressedBypass;

    public SwapKeyBinding() {
        super(
            "key.ecs.swap", // The translation key of the keybinding's name
            InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
            GLFW.GLFW_KEY_GRAVE_ACCENT, // The keycode of the key
            "category.ecs" // The translation key of the keybinding's category.
        );
        key = this.getDefaultKey();
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
    }
    
}