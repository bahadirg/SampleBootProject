package com.sampleapp.repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.primefaces.model.FilterMeta;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.sampleapp.config.StaticConfigurations;

import lombok.Data;

//https://www.baeldung.com/spring-data-jpa-method-in-all-repositories

//Custom BaseRepository (Optional) class for extension by all repositories
@Data
public class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {
  
    private EntityManager entityManager;
  

    public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    // ...
  
    @Override
    public Long countEntities(int first, int pageSize, String sortField, String sortOrder, Map<String, FilterMeta> filters, Map<String, String> aliases, Criterion extraCriterion)
			throws SecurityException, NoSuchFieldException {
		
		Long count = 0L;
		

		Session session = entityManager.unwrap(Session.class);
		
		
		try {
//			session = sessionFactory.openSession();
			Criteria crit = session.createCriteria(getDomainClass());
	
			crit = prepareCriteria(first, pageSize, sortField, sortOrder, filters, aliases, extraCriterion, crit);

			crit.setProjection(Projections.rowCount());
		
			if (crit.list() != null) {
				count = (Long) crit.list().get(0);
			}
			
		} catch (Exception e) {
			e.printStackTrace();	
		} finally {
					
			if(session != null){
				session.close();
			}
		}

		return count;
	}

	@Override
	public List<T> findEntities(int first, int pageSize, String sortField, String sortOrder, Map<String, FilterMeta> filters, Map<String, String> aliases, Criterion extraCriterion)
			throws SecurityException, NoSuchFieldException {
		
		List<T> list = null;
		
		Session session = entityManager.unwrap(Session.class);
		
		try {

			Criteria crit = session.createCriteria(getDomainClass());
	
			crit = prepareCriteria(first, pageSize, sortField, sortOrder, filters, aliases, extraCriterion, crit);
	
			crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			list = crit.list();
		} catch (Exception e) {
			e.printStackTrace();	
		} finally {
					
			if(session != null){
				session.close();
			}
		}	
		
		return list;
	}

	private Criteria prepareCriteria(int first, int pageSize, String sortField, String sortOrder, Map<String, FilterMeta> filters, Map<String, String> aliases, Criterion extraCriterion, Criteria crit)
			throws NoSuchFieldException {
		if (aliases != null && !aliases.isEmpty()) {
			Iterator<Entry<String, String>> iterator = aliases.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, String> entry = iterator.next();
				crit.createAlias(entry.getKey(), entry.getValue(), Criteria.LEFT_JOIN);
			}
		}

		if (extraCriterion != null) {
			crit.add(extraCriterion);
		}

		if (sortField != null && !sortField.isEmpty()) {
			if (!sortOrder.equalsIgnoreCase("UNSORTED")) {
				if (sortOrder.equalsIgnoreCase("ASCENDING")) {
					crit = crit.addOrder(Order.asc(sortField));
				} else {
					crit = crit.addOrder(Order.desc(sortField));
				}
			}
		}

		if (filters != null && !filters.isEmpty()) {
			Iterator<Entry<String, FilterMeta>> iterator = filters.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, FilterMeta> entry = iterator.next();
				Class<?> type = getDomainClass().getDeclaredField(entry.getKey()).getType();

				if(entry.getValue().getFilterValue() == null) {   ////////////////////
					continue;
				}
				
				try {
					if (BigDecimal.class.isAssignableFrom(type)) {
						crit = crit.add(Restrictions.eq(entry.getKey(), new BigDecimal(entry.getValue().getFilterValue() + "")));  /////////////////
					} else if (type.isEnum() || Number.class.isAssignableFrom(type) || Double.class.isAssignableFrom(type)) {
						crit = crit.add(Restrictions.eq(entry.getKey(), type.getDeclaredMethod("valueOf", String.class).invoke(null, entry.getValue().getFilterValue())));          ////////////////////
					} else if (type.getCanonicalName().indexOf(StaticConfigurations.COMPANY_JAVA_PACKAGE) != -1) {  //"ttnet" - bu gui datatable non-primitive obje filtresinde ise yariyor 
                      // Long id = ((BaseModel)entry.getValue()).getId();
                      SimpleExpression idEq = Restrictions.eq(entry.getKey() + ".id", Long.parseLong(entry.getValue().getFilterValue() + ""));   //??????
                      crit = crit.add(idEq);
                  } else {
						crit = crit.add(Restrictions.like(entry.getKey(), String.valueOf(entry.getValue().getFilterValue()), MatchMode.START));    ////////////////////
					}
				} catch (Exception ex) {
				}
			}
		}

		if (first != -1) {
			crit = crit.setFirstResult(first);
		}

		if (pageSize != -1) {
			crit = crit.setMaxResults(pageSize);
		}
		return crit;
	}
}
