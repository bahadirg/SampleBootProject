package com.sampleapp.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sampleapp.controller.MailController;
import com.sampleapp.enumerations.Role;
import com.sampleapp.enumerations.UserState;
import com.sampleapp.lazydatamodel.UserLazyDataModel;
import com.sampleapp.model.User;
import com.sampleapp.repository.UserRepository;
import com.sampleapp.util.PatternBank;
import com.sampleapp.util.RegexUtil;

import lombok.Data;
import lombok.extern.java.Log;

@Log
@Data
@Component(value = "userBean")
@Scope("session")
public class UserBean implements Serializable {

	private UserLazyDataModel userModel;
	
	private SelectItem[] roleOptions;
	private SelectItem[] userStateOptions;
	
	private SelectItem[] newUserRoleOptions;
	private SelectItem[] newUserStateOptions;

	private User newUser = new User();
	private User selectedUser;
	
	private SelectItem[] userRoleOptions;
	
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
    private MailController mailController;
	
	
	public UserBean() {}
	
	
	
	@PostConstruct
	public void initModel() {
		
		Role[] roles = Role.values();
		roleOptions = new SelectItem[roles.length + 1];
		roleOptions[0] = new SelectItem("", "Hepsi");
		for (int i = 1; i < roleOptions.length; i++) {
			roleOptions[i] = new SelectItem(roles[i - 1], roles[i - 1].getName());
		}

		UserState[] states = UserState.values();
		userStateOptions = new SelectItem[states.length + 1];
		userStateOptions[0] = new SelectItem("", "Hepsi");
		for (int i = 1; i < userStateOptions.length; i++) {
			userStateOptions[i] = new SelectItem(states[i - 1], states[i - 1].getName());
		}
		
		newUserRoleOptions = new SelectItem[roles.length];
		for (int i = 0; i < roles.length; i++) {
			newUserRoleOptions[i] = new SelectItem(roles[i], roles[i].getName());
		}
		
		newUserStateOptions = new SelectItem[states.length];
		for (int i = 0; i < states.length; i++) {
			newUserStateOptions[i] = new SelectItem(states[i], states[i].getName());
		}

		userModel = new UserLazyDataModel(userRepository);
	}

	public void prepareForNewUser() throws SecurityException, NoSuchFieldException {
		
		newUser = new User();
		newUser.setRole(Role.ROLE_ORDINARY);
		newUser.setState(UserState.ACTIVE);
		
		return;
	}

	public void saveNewUser() {
		
		try {
			
			User user = userRepository.findByUsername(newUser.getUsername());
			
			if(user != null){
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Bu kullan??c?? ad??yla sistemde bir kullan??c?? mevcut. L??tfen kullan??c?? ad??n?? de??i??tiriniz", "");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return;
			}
			
			String message = checkValidity(newUser);
			if(message != null) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, "");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return;
			}
			
			userRepository.save(newUser); 
			
			userModel = new UserLazyDataModel(userRepository);
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Kullan??c?? ba??ar??yla olu??turuldu", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			
			return;
			
		} catch (Throwable e){
			log.severe(e.getMessage());
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Kullan??c?? olu??turulurken hata olu??tu", e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
	}

	public void saveSelectedUser() {
		try {
			
			String message = checkValidity(selectedUser);
			if(message != null) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, "");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				return;
			}
			
			userRepository.save(selectedUser);

			userModel = new UserLazyDataModel(userRepository);
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Kullan??c?? ba??ar??yla g??ncellendi", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			selectedUser = null;
			
			return;
			
		} catch (Exception e) {
			log.severe(e.getMessage());
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Kullan??c?? g??ncellenirken hata olu??tu", e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
			selectedUser = null;
			return;
		}
	}

	public void deleteUser() {
		try {
			if(selectedUser == null) {
				System.out.println("selectedUser is nulllll!!!");
				return;
			}
			
			userRepository.delete(selectedUser); 
			
			userModel = new UserLazyDataModel(userRepository);
			
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Kullan??c?? ba??ar??yla silindi", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		} catch (Exception e) {
			log.severe(e.getMessage());
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Kullan??c?? silinirken hata olu??tu", e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}
	}
	
	private String checkValidity(User user) {
		
		String message = null;
		
		if(user.getFirstName() == null || user.getFirstName().trim().equals("")) {
			message = "??sim bo?? olamaz";
			return message;
		}
		
		if(user.getLastName() == null || user.getLastName().trim().equals("")) {
			message = "Soyad bo?? olamaz";
			return message;
		}
		
		if(user.getUsername() == null || user.getUsername().trim().equals("")) {
			message = "Kullan??c?? ad?? bo?? olamaz";
			return message;
		}
		
		if(user.getEmail() == null || user.getEmail().trim().equals("")) {
			message = "Email bo?? olamaz";
			return message;
		}
		
		if(RegexUtil.validatePattern(user.getEmail(), PatternBank.EMAIL) == false) {
			message = "Email format hatal??";
			return message;
		}
		
		return message;
	}
}
