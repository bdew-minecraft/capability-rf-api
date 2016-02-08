/*
 * Copyright (c) bdew, 2013 - 2016
 * https://github.com/bdew/pressure
 *
 * This mod is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://bdew.net/minecraft-mod-public-license/
 */

package minecraft.energy.rf.reference;

import minecraft.energy.rf.IEnergyProvider;
import minecraft.energy.rf.IEnergyReceiver;
import minecraft.energy.rf.IMutableStorage;

public class SimpleEnergyStorage implements IMutableStorage, IEnergyProvider, IEnergyReceiver {
    private int stored;
    private int maxStored;
    private int maxReceive;
    private int maxExtract;

    public SimpleEnergyStorage(int stored, int maxStored, int maxReceive, int maxExtract) {
        this.stored = stored;
        this.maxStored = maxStored;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
    }

    public SimpleEnergyStorage(int stored, int maxStored) {
        this(stored, maxStored, maxStored, maxStored);
    }

    public SimpleEnergyStorage(int maxStored) {
        this(0, maxStored, maxStored, maxStored);
    }

    public SimpleEnergyStorage() {
        this(0, 0, 0, 0);
    }

    @Override
    public int getEnergyStored() {
        return stored;
    }

    @Override
    public int getMaxEnergyStored() {
        return maxStored;
    }

    @Override
    public void setEnergyStored(int value) {
        stored = value;
        if (stored > maxStored) stored = maxStored;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int energyExtracted = Math.min(stored, Math.min(this.maxExtract, maxExtract));

        if (!simulate) {
            stored -= energyExtracted;
        }

        return energyExtracted;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int energyReceived = Math.min(maxStored - stored, Math.min(this.maxReceive, maxReceive));

        if (!simulate) {
            stored += energyReceived;
        }

        return energyReceived;
    }
}
