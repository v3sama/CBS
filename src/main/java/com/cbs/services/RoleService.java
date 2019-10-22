package com.cbs.services;


import com.cbs.model.Role;
import com.cbs.repository.RoleRepository;
import com.cbs.services.RoleService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class RoleService {

	private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    
    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }

    public Role getRoleByID(Long id) {
        return roleRepository.getOne(id);
    }

    public void deleteRoleByID(Long id) {
        if(getRoleByID(id) != null) {
            roleRepository.deleteById(id);
        }
    }

    public void addRole(Role role) {
        roleRepository.saveAndFlush(role);
    }

	public Role getRoleById(Long id) {
		return roleRepository.getOne(id);
	}

	public Role findByName(String name) {
		// TODO Auto-generated method stub
		return roleRepository.getRoleByName(name);
	}

	
	

	
}
