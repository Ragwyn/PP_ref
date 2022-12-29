package ru.petrashova.web.services;


//import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.petrashova.web.models.User;
import ru.petrashova.web.repositories.RoleRepository;
import ru.petrashova.web.repositories.UserRepository;

import java.util.List;
import java.util.Optional;


/*
Содержит методы для бизнес-логики приложения. Этот класс реализует интерфейс UserDetailsService
(необходим для Spring Security), в котором нужно переопределить один метод loadUserByUsername().
 */

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService { // Загружает пользователя по логину
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;

    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByLogin(login);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        return user.get();
    }
    public User findUserById(int userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }
    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public boolean deleteUser(int userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(int id) {
        return userRepository.findById(id).get();
    }

    @Transactional
    @Override
    public void save(User user) {

        userRepository.save(user);
    }

    @Transactional
    @Override
    public void update(User updatedUser) {

        userRepository.save(updatedUser);
    }

    @Transactional
    @Override
    public void delete(int id) {
        userRepository.deleteById(id);
    }

}
