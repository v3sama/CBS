package com.cbs.services;

import com.cbs.model.User;
import com.cbs.repository.UserRepository;
import com.cbs.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserService  {

    private final UserRepository userRepository;


  //  private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        
        //this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    
    public void saveUser(User user) {
		userRepository.save(user);
	}

    public User findByConfirmationToken(String confirmationToken) {
		return userRepository.findByConfirmationToken(confirmationToken);
	}

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByUsername(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    public User findById(Long id) {
        return userRepository.getOne(id);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);

    }

    public void update(User user) {
        userRepository.saveAndFlush(user);
    }

    public void add(User user) {
    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    	
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    	//user.setPassword(user.getPassword().);
        userRepository.save(user);
    }

	public void deactivate(Long userId) {
		User user = userRepository.getOne(userId);
		user.setActive(false);
		userRepository.save(user);
	}

	public void activate(Long userId) {
		User user = userRepository.getOne(userId);
		user.setActive(true);
		userRepository.save(user);
	}

}

