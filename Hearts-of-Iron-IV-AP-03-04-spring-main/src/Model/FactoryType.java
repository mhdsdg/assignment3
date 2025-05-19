package Model;

public enum FactoryType {
	STEEL_FACTORY(10000, 0, 10, 30000),
	SULFUR_FACTORY(20000, 0, 20, 20000),
	FUEL_REFINERY(50000, 5000, 50, 100000);

	public final int manPowerCost;
	public final int steelCost;
	public final int productionPerManPower;
	public final int maxProduction;

	FactoryType(int manPowerCost, int steelCost, int productionPerManPower, int maxProduction) {
		this.manPowerCost = manPowerCost;
		this.steelCost = steelCost;
		this.productionPerManPower = productionPerManPower;
		this.maxProduction = maxProduction;
	}
}