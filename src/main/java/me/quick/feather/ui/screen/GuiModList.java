package me.quick.feather.ui.screen;

import me.quick.feather.Feather;
import me.quick.feather.api.mod.FeatherModBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiListExtended;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class GuiModList extends GuiListExtended {

    private Minecraft mc;
    private final GuiListExtended.IGuiListEntry[] listEntries;

    public GuiModList(Minecraft mc, GuiMods parent) {
        super(mc, 300, parent.height - 60, 63, parent.height - 32, 50);
        this.mc = mc;

        this.left += 10;
        this.right += 10;

        this.listEntries = new GuiListExtended.IGuiListEntry[Feather.INSTANCE.modManager.mods.size()];

        int i = 0;

        for (FeatherModBase m : Feather.INSTANCE.modManager.mods) {
            this.listEntries[i++] = new ModEntry(m);
        }
    }

    @Override
    public IGuiListEntry getListEntry(int index) {
        return this.listEntries[index];
    }

    @Override
    protected int getSize() {
        return this.listEntries.length;
    }

    public class ModEntry implements GuiListExtended.IGuiListEntry {

        private FeatherModBase mod;

        public ModEntry(FeatherModBase mod) {
            this.mod = mod;
        }

        @Override
        public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_) {

        }

        @Override
        public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {
            if (isSelected) {
                Gui.drawRect(x, y, x + listWidth, y + slotHeight, 0x59000000);
                Gui.drawHorizontalLine(x, x + listWidth, y, -1);
                Gui.drawHorizontalLine(x, x + listWidth, y + slotHeight, -1);
                Gui.drawVerticalLine(x, y, y + slotHeight, -1);
                Gui.drawVerticalLine(x + listWidth, y, y + slotHeight, -1);

                try {
                    GL11.glPushMatrix();
                    DynamicTexture texture = new DynamicTexture(mc.getResourcePackRepository().rprDefaultResourcePack.getPackImage());
                    GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getGlTextureId());
                    GL11.glColor3f(255, 255, 255);
                    Gui.drawModalRectWithCustomSizedTexture(x + 1, y + 1, 0, 0, 44, 44, 44, 44);
                    GL11.glPopMatrix();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                Gui.drawRect(x, y, x + listWidth, y + slotHeight, 0x59000000);

                try {
                    GL11.glPushMatrix();
                    DynamicTexture texture = new DynamicTexture(mc.getResourcePackRepository().rprDefaultResourcePack.getPackImage());
                    GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getGlTextureId());
                    GL11.glColor3f(255, 255, 255);
                    Gui.drawModalRectWithCustomSizedTexture(x + 1, y + 1, 0, 0, 44, 44, 44, 44);
                    GL11.glPopMatrix();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }



            mc.fontRendererObj.drawString(mod.name, x + 48, y + 2, -1);
            // TODO: make it so that if the description overflows it ends with ...
            mc.fontRendererObj.drawSplitString(mod.description, x + 48, y + 3 + mc.fontRendererObj.FONT_HEIGHT, listWidth - 48, -1);
        }

        @Override
        public boolean mousePressed(int slotIndex, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_) {
            return false;
        }

        @Override
        public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {

        }
    }
}
