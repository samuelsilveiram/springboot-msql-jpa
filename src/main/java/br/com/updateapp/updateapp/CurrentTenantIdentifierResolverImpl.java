package br.com.updateapp.updateapp;

import java.util.Objects;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {
	public static final String DEFAULT_TENANT_ID = "2";
	public static final String DEFAULT_TENANT_PREFIX = "";

	@Override
	public String resolveCurrentTenantIdentifier() {
		String tenantId = TenantContext.getCurrentTenant();
		return (!Objects.isNull(tenantId)) ? tenantId : DEFAULT_TENANT_ID;
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		return true;
	}
}
