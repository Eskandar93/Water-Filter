package com.waterfilter.water.user;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.waterfilter.water.config.security.JwtService;
import com.waterfilter.water.employee.Employee;
import com.waterfilter.water.exception.DublicateResourceException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final AuthenticationManager authenticationManager;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;


  // register
  public void register(UserRequest userRequest){
    if(userRepository.findByPhoneNumber(userRequest.getPhoneNumber()).isPresent()){
      throw new DublicateResourceException("User with phone number: "+ userRequest.getPhoneNumber() +" is already exist");
    }

    Employee user = new Employee();
    user.setUsername(userRequest.getUsername());
    user.setEmail(userRequest.getEmail());
    user.setPhoneNumber(userRequest.getPhoneNumber());
    // encode password
    user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

    userRepository.save(user);
  }
  // login
  public String verifyUser(UserRequest userRequest){
    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword())
      );

      if(authentication.isAuthenticated()){
        return jwtService.generateToken(userRequest.getUsername());
      }

      return "Failuer";
  }

  // forget password
  // logout

}
