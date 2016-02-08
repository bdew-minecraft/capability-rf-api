package minecraft.energy.rf;

/**
 * Implement this capability on Tile Entities and Items which should receive energy.
 */
public interface IEnergyReceiver extends IEnergyStorage {
    /**
     * Add energy to an IEnergyReceiver, internal distribution is left entirely to the IEnergyReceiver.
     *
     * @param maxReceive Maximum amount of energy to receive.
     * @param simulate   If TRUE, the charge will only be simulated.
     * @return Amount of energy that was (or would have been, if simulated) received.
     */
    int receiveEnergy(int maxReceive, boolean simulate);
}
