package br.com.updateapp.updateapp;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
public class UpdateAppVerify {

	@PersistenceContext
	private EntityManager entityManager;
	
}
