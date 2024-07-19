package me.alpha432.oyvey.mixin;

import me.alpha432.oyvey.event.impl.ChatEvent;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.alpha432.oyvey.util.traits.Util.EVENT_BUS;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientNetworkHandlerMixin {
    @Shadow
    private ClientWorld world;

    @Unique
    private boolean worldNotNull;

    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    private void sendChatMessageHook(String content, CallbackInfo ci) {
        ChatEvent event = new ChatEvent(content);
        EVENT_BUS.post(event);
        if (event.isCancelled()) ci.cancel();
    }
}