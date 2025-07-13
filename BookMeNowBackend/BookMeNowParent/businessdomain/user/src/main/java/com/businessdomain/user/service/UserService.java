package com.businessdomain.user.service;
import com.businessdomain.user.dto.UserDTO;
import com.businessdomain.user.model.User;

import java.util.List;

public interface UserService {

    public List getAllUsers ();

    public UserDTO getUserById (Long id);

    public UserDTO getUserByEmail (String email);

    public UserDTO createUser (UserDTO userDTO);

    public UserDTO editUser (Long id, UserDTO userDTO);

    public void deleteUser (Long id);

}
