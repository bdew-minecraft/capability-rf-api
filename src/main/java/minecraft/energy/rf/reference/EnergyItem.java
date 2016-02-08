/*
 * Copyright (c) bdew, 2013 - 2016
 * https://github.com/bdew/pressure
 *
 * This mod is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://bdew.net/minecraft-mod-public-license/
 */

package minecraft.energy.rf.reference;

import minecraft.energy.rf.RFCapabilities;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

public class EnergyItem extends Item {
    private int max;
    private int maxReceive;
    private int maxExtract;

    public EnergyItem(int max, int maxReceive, int maxExtract) {
        this.max = max;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
    }

    private class Storage extends SimpleEnergyStorage implements ICapabilityProvider, INBTSerializable<NBTTagInt> {

        public Storage() {
            super(0, max, maxReceive, maxExtract);
        }

        @Override
        public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
            return maxReceive > 0 && capability == RFCapabilities.RECEIVER
                    || maxExtract > 0 && capability == RFCapabilities.PROVIDER;
        }

        @Override
        public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
            if (maxReceive > 0 && capability == RFCapabilities.RECEIVER) return (T) this;
            if (maxExtract > 0 && capability == RFCapabilities.PROVIDER) return (T) this;
            return null;
        }

        @Override
        public NBTTagInt serializeNBT() {
            return new NBTTagInt(getEnergyStored());
        }

        @Override
        public void deserializeNBT(NBTTagInt nbt) {
            if (nbt != null)
                setEnergyStored(nbt.getInt());
        }
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        return new Storage();
    }
}
