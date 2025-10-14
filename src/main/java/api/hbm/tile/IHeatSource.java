package api.hbm.tile;

public interface IHeatSource {

	int getHeatStored();
	
	/**
	 * Removes heat from the system. Implementation has to include the checks preventing the heat from going into the negative.
	 * @param heat
	 */
    void useUpHeat(int heat);
}
