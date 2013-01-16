package no.runsafe.framework.configuration;

import no.runsafe.framework.event.IConfigurationChanged;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;
import java.util.Map;

public interface IConfiguration
{
	// Loads the configuration from disk. Prepares defaults if available.
	void load();

	// Replaces the current configuration values with the supplied defaults
	boolean restoreToDefaults();

	// Saves the current configuration file to disk
	void save();

	// Returns a configuration value as a string
	String getConfigValueAsString(String value);

	// Returns a configuration value as a boolean
	boolean getConfigValueAsBoolean(String value);

	// Returns a configuration value as an integer
	int getConfigValueAsInt(String value);

	// Returns a configuration value as a double
	double getConfigValueAsDouble(String value);

	// Returns a configuration value as a float
	float getConfigValueAsFloat(String value);

	List<String> getConfigValueAsList(String value);

	// Sets a configuration value with the specified key -> value
	void setConfigValue(String key, Object value);

	@Deprecated
	ConfigurationSection getSection(String path);

	void setListeners(List<IConfigurationChanged> subscribers);

	void setConfigFileProvider(IConfigurationFile provider);

	Map<String, String> getConfigValuesAsMap(String path);

	Map<String, Map<String, String>> getConfigSectionsAsMap(String path);

	Map<String, List<String>> getConfigSectionsAsList(String path);
}