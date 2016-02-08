package minecraft.energy.rf.reference;

import minecraft.energy.rf.RFCapabilities;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

abstract public class EnergyTileEntity extends TileEntity {
    protected SimpleEnergyStorage energy;

    public EnergyTileEntity(int maxEnergy, int maxInput, int maxOutput) {
        energy = new SimpleEnergyStorage(0, maxEnergy, maxInput, maxOutput);
    }

    public abstract boolean canReceiveEnergyFrom(EnumFacing face);

    public abstract boolean canProvideEnergyTo(EnumFacing face);

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        compound.setInteger("energy", energy.getEnergyStored());
        super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        energy.setEnergyStored(compound.getInteger("energy"));
        super.readFromNBT(compound);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == RFCapabilities.PROVIDER && canProvideEnergyTo(facing)
                || capability == RFCapabilities.RECEIVER && canReceiveEnergyFrom(facing)
                || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == RFCapabilities.PROVIDER && canProvideEnergyTo(facing))
            return (T) energy;
        else if (capability == RFCapabilities.RECEIVER && canReceiveEnergyFrom(facing))
            return (T) energy;
        else
            return super.getCapability(capability, facing);
    }
}
