package minecraft.energy.rf;

import minecraft.energy.rf.reference.SimpleEnergyStorage;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class RFCapabilities {
    @CapabilityInject(IEnergyProvider.class)
    public static Capability<IEnergyProvider> PROVIDER = null;

    @CapabilityInject(IEnergyReceiver.class)
    public static Capability<IEnergyReceiver> RECEIVER = null;

    private static class Storage<T extends IEnergyStorage> implements Capability.IStorage<T> {
        @Override
        public NBTBase writeNBT(Capability<T> capability, T instance, EnumFacing side) {
            return new NBTTagInt(instance.getEnergyStored());
        }

        @Override
        public void readNBT(Capability<T> capability, T instance, EnumFacing side, NBTBase nbt) {
            if (!(instance instanceof IMutableStorage))
                throw new RuntimeException("IEnergyStorage instance does not implement IMutableStorage");
            if (nbt != null && nbt instanceof NBTTagInt)
                ((IMutableStorage) instance).setEnergyStored(((NBTTagInt) nbt).getInt());
        }
    }

    static {
        CapabilityManager.INSTANCE.register(IEnergyProvider.class, new Storage<IEnergyProvider>(), SimpleEnergyStorage.class);
        CapabilityManager.INSTANCE.register(IEnergyReceiver.class, new Storage<IEnergyReceiver>(), SimpleEnergyStorage.class);
    }
}
