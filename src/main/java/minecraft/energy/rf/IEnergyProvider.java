package minecraft.energy.rf;

/**
 * Implement this capability on Tile Entities and Items which should provide energy.
 */
public interface IEnergyProvider extends IEnergyStorage {
    /**
     * Remove energy from an IEnergyProvider, internal distribution is left entirely to the IEnergyProvider.
     *
     * @param maxExtract Maximum amount of energy to extract.
     * @param simulate   If TRUE, the extraction will only be simulated.
     * @return Amount of energy that was (or would have been, if simulated) extracted.
     */
    int extractEnergy(int maxExtract, boolean simulate);
}
