package net.dark_roleplay.gdarp.config.values;

public abstract class IConfigEntry<T> {

	private T defaultValue;
	private T activeValue;
	private T actualValue;


	public IConfigEntry(T defaultValue) {
		this.defaultValue = defaultValue;
	}
}
