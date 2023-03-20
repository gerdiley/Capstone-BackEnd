package it.epicode.capstone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import it.epicode.capstone.entity.Role;
import it.epicode.capstone.entity.User;
import it.epicode.capstone.repo.RoleRepo;
import it.epicode.capstone.repo.UserRepo;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class JwtApplication implements CommandLineRunner {

	@Autowired
	RoleRepo rp;

	@Autowired
	UserRepo ur;

	@Autowired
	PasswordEncoder pe;

	public static void main(String[] args) {
		SpringApplication.run(JwtApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

//			List<Role> roles = initRole();
//			initUser(roles);   
		
//		initAdmin();
//
	}

	private List<Role> initRole() {
		List<Role> result = new ArrayList<Role>();
		Role role = new Role();
		role.setName("ROLE_ADMIN");
		rp.save(role);
		result.add(role);
		System.out.println("Saved Role: " + role.getName());

		Role role1 = new Role();
		role1.setName("ROLE_USER");
		rp.save(role1);
		result.add(role1);
		System.out.println("Saved Role: " + role.getName());

		return result;
	}

	private User initUser(List<Role> roles) {
		User user = new User();
		user.setFullname("Mario Rossi");
		user.setUsername("mrossi");
		user.setPassword(pe.encode("test"));
		user.setRoleList(roles);
		user.setActive(true);
		ur.save(user);

		return user;
	}
	
	private void initAdmin() {
		
        List<Role> roles = new ArrayList<>();
        roles.add(rp.findById(1).get());
		
		User user = new User();
		user.setFullname("admin");
		user.setUsername("admin");
		user.setPassword(pe.encode("test"));
		user.setRoleList(roles);
		user.setActive(true);
		ur.save(user);
		
	}


}
