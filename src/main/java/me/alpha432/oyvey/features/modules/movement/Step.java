package me.alpha432.oyvey.features.modules.movement;

import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.features.settings.Setting;
import net.minecraft.entity.attribute.EntityAttributes;

public class Step extends Module {
    private final Setting<Float> height = register(new Setting<>("Height", 2f, 1f, 3f, v -> true));
    public Step() {
        super("Step", "step..", Category.MOVEMENT, true, false, false);
    }

    @Override public void onDisable() {
        if (nullCheck()) return;
        mc.player.getAttributeInstance(EntityAttributes.GENERIC_STEP_HEIGHT).setBaseValue(0.6F);

    }

    @Override public void onTick() {
        if (nullCheck()) return;
        mc.player.getAttributeInstance(EntityAttributes.GENERIC_STEP_HEIGHT).setBaseValue(height.getValue());
    }
}
