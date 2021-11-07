package com.sampleapp.lazydatamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.sampleapp.config.Configurations;
import com.sampleapp.model.User;
import com.sampleapp.repository.UserRepository;

import lombok.Data;
import lombok.extern.java.Log;

@Log
@Data
public class UserLazyDataModel extends LazyDataModel<User> {


	private UserRepository userRepository;

//	private String currentSortField;
//	private SortOrder currentSortOrder;
//	private Map<String, FilterMeta> currentFilters;

	public UserLazyDataModel() {
		super.setPageSize(Configurations.PAGE_SIZE);
	}

	public UserLazyDataModel(UserRepository userRepository) {
		super.setPageSize(Configurations.PAGE_SIZE);
		this.userRepository = userRepository;
	}

	@Override
	public List<User> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, FilterMeta> filters) {

//		currentSortField = sortField;
//		currentSortOrder = sortOrder;
//		currentFilters = filters;

		if (sortField == null) {
			sortField = "createTime";
			sortOrder = SortOrder.DESCENDING;
		}

		List<User> users = new ArrayList<User>();
		try {
			
			super.setRowCount(userRepository.countEntities(-1, -1, null, sortOrder.name(), filters, null, null).intValue());
			users = userRepository.findEntities(first, pageSize, sortField, sortOrder.name(), filters, null, null);
		} catch (Exception e) {
			log.severe(e.getMessage());
		}

		return users;
	}

	@Override
	public String getRowKey(User user) {
		return user.getId() + "";
	}

	@Override
	public User getRowData(String rowKey) {
		try {
			return userRepository.getOne(Long.parseLong(rowKey));
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void setPageSize(int pageSize) {
		super.setPageSize(pageSize);
	}

//	public void onFilter(AjaxBehaviorEvent event) {
//	}
}
