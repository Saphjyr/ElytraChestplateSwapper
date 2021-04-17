package com.saphjyr.ElytraChestplateSwapper.mixins;

import com.saphjyr.ElytraChestplateSwapper.ElytraChestplateSwapperClient;
import com.saphjyr.ElytraChestplateSwapper.InventoryUtils;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;


@Environment(EnvType.CLIENT)
@Mixin(Keyboard.class)
public class KeyboardMixin {
    
    @Inject(method = "onKey", at = @At(value = "HEAD"))
	private void onKey(long window, int key, int scancode, int i, int j, CallbackInfo callbackInfo) {
        if (ElytraChestplateSwapperClient.keyBinding.getKey().getCode() == key) {
            if (ElytraChestplateSwapperClient.keyBinding.isPressedBypass())
                ElytraChestplateSwapperClient.keyBinding.setPressedBypass(false);
            else {
                ElytraChestplateSwapperClient.keyBinding.setPressedBypass(true);
                InventoryUtils.swapChestplate(MinecraftClient.getInstance());
            }
                
        }
	}
}
