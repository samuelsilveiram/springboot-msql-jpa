package br.com.updateapp.updateapp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateAppVerify{

	private EntityManager entityManager;
	
	@Autowired
	public UpdateAppVerify(EntityManager entityManager) {	
		this.entityManager = entityManager;
	}	
	
	public void executaAPorraDaConsulta() {
		
		try {
			Stream<String> lines = new BufferedReader(new FileReader("clientes.csv")).lines();
			
			List<String> collect = lines.collect(Collectors.toList());
			
			for (String tenant : collect) {
				TenantContext.setCurrentTenant(tenant);
				Query query = entityManager.createNativeQuery("select property_value from CONFIGURACAO_AGILE_PROMOTER where property_name = 'app.url'");
				String singleResult = (String) query.getSingleResult();
				System.out.println(singleResult);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
