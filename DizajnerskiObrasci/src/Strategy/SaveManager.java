package Strategy;

public class SaveManager {
	
	private Save strategy;
	
	public void save (String filePath) {
		strategy.saveFile(filePath);
	}

	public Save getStrategy() {
		return strategy;
	}

	public void setStrategy(Save strategy) {
		this.strategy = strategy;
	}
}
