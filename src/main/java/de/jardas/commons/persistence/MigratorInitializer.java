package de.jardas.commons.persistence;

import java.io.File;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import de.jardas.commons.init.Initializer;
import de.jardas.migrator.DatabaseAdapter;
import de.jardas.migrator.Migrator;
import de.jardas.migrator.source.MigrationDirectory;

public abstract class MigratorInitializer implements Initializer {
	@Autowired
	private Environment env;
	@Autowired
	private DataSource dataSource;

	@Override
	public void initialize() {
		try {
			final String schema = env.getRequiredProperty("db.schema");
			final DatabaseAdapter adapter = createDatabaseAdapter(dataSource, schema);

			try {
				final File migrationsDir = new File(getClass().getResource("/db/migrations").toURI());
				new Migrator().setDatabaseAdapter(adapter)
						.addMigrationResources(MigrationDirectory.read(migrationsDir)).execute();
			} finally {
				adapter.close();
			}
		} catch (final Exception e) {
			throw new IllegalStateException("Error running database migrations: " + e, e);
		}
	}

	protected abstract DatabaseAdapter createDatabaseAdapter(DataSource dataSource, final String schema);

	@Override
	public int getOrder() {
		return HIGHEST_PRECEDENCE;
	}
}
