package cn.evolvefield.mods.botapi.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.events.GuiEventListener;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2021/12/26 18:55
 * Version: 1.0
 */
public interface IDrawableGuiListener extends GuiEventListener {

    void render(PoseStack stack);

}
