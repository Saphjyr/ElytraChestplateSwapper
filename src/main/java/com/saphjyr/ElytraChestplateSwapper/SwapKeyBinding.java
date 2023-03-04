package com.saphjyr.ElytraChestplateSwapper;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.option.KeyBinding;
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

    @Override
	public void setPressed(boolean pressed) {
		super.setPressed(pressed);
		if(pressed) onPressed();
	}

    public void onPressed() {
        InventoryUtils.swapChestplate(MinecraftClient.getInstance());
    }

    public boolean isPressedBypass() {
        return pressedBypass;
    }

    public void setPressedBypass(boolean pressed) {
        pressedBypass = pressed;
        if (pressed) onPressBypass();
    }

    public void onPressBypass() {

        try {
            MinecraftClient client = MinecraftClient.getInstance();
        
            // If the the inventory screen, trigger swap
            if (client.currentScreen instanceof InventoryScreen) {
                InventoryUtils.swapChestplate(client);
            }

            //If in the creative screen, only trigger when in the inventory tab
            if (client.currentScreen instanceof CreativeInventoryScreen) {
                CreativeInventoryScreen cis = (CreativeInventoryScreen)client.currentScreen;
                if(cis.isInventoryTabSelected()) {
                    InventoryUtils.swapChestplate(client);
                }
            }
            
        } catch (Exception e) {
            System.out.println("ECS creative inventory swapping is not compatible with this version of Minecraft");
        }
        
            
    }
    
}