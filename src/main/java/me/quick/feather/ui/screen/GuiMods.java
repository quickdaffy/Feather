package me.quick.feather.ui.screen;

import me.quick.feather.Feather;
import me.quick.feather.api.mod.FeatherModBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

public class GuiMods extends GuiScreen {
    public GuiModList modList;

    public void initGui() {
        this.modList = new GuiModList(mc, this);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.modList.drawScreen(mouseX, mouseY, partialTicks);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        this.modList.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);

        this.modList.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public void handleInput() throws IOException {
        this.modList.handleMouseInput();
        super.handleInput();
    }
}
