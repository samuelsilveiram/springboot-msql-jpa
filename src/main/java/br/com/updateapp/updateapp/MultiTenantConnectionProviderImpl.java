package br.com.updateapp.updateapp;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.hibernate.service.spi.Stoppable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MultiTenantConnectionProviderImpl implements MultiTenantConnectionProvider, Stoppable {
	private static final long serialVersionUID = -7956730596993490133L;

	@Autowired
	private DataSource dataSource;

	@Override
	public Connection getAnyConnection() throws SQLException {
		return dataSource.getConnection();
	}

	@Override
	public void releaseAnyConnection(Connection connection) throws SQLException {
		connection.close();
	}

	@Override
	public Connection getConnection(String tenantIdentifier) throws SQLException {
		final Connection connection = getAnyConnection();
		Statement statement = connection.createStatement();
		try {
			if (tenantIdentifier != null) {
				statement.execute(alterSchemaBy(tenantIdentifier));
			} else {
				statement.execute(alterSchemaBy(CurrentTenantIdentifierResolverImpl.DEFAULT_TENANT_ID));
			}
		} catch (SQLException e) {
			throw new SQLException();
		}finally {
			statement.close();
		}
		return connection;
	}

	private String alterSchemaBy(String tenantIdentifier) {
		return "USE ".concat(CurrentTenantIdentifierResolverImpl.DEFAULT_TENANT_PREFIX).concat(tenantIdentifier).toUpperCase();
	}

	@Override
	public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
		Statement statement = connection.createStatement();
		try {
			statement.execute(alterSchemaBy(CurrentTenantIdentifierResolverImpl.DEFAULT_TENANT_ID));
		} catch (SQLException e) {
			//TODO Rever esta exceção
			throw new SQLException();
		}finally {
			statement.close();
		}
		connection.close();
	}

	@Override
	public boolean isUnwrappableAs(Class unwrapType) {
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> unwrapType) {
		return null;
	}

	@Override	
	public void stop() {
	}

	@Override
	public boolean supportsAggressiveRelease() {
		return true;
	}

	
}