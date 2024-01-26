package ra.rest_apidemo.serviceImp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ra.rest_apidemo.dto.request.SignUpRequest;
import ra.rest_apidemo.dto.response.SignUpResponse;
import ra.rest_apidemo.model.ERoles;
import ra.rest_apidemo.model.Roles;
import ra.rest_apidemo.model.User;
import ra.rest_apidemo.repository.RolesRepository;
import ra.rest_apidemo.repository.UserRepository;
import ra.rest_apidemo.service.UserService;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

//    @Override
//    public Map<String, Objects> findByEmailorStatus(int page, int size, String userEmail, String direction, String orderBy) {
//        return null;
//    }

    @Override
    public SignUpResponse register(SignUpRequest signUpRequest) {
        Set<Roles> setRoles = new HashSet<>();
        signUpRequest.getListRoles().forEach(role -> {
            switch (role) {
                case "admin":
                    setRoles.add(rolesRepository.findByName(ERoles.ROLE_ADMIN).orElseThrow(() ->
                            new RuntimeException("No administrator rights exist")));
                    break;
                case "moderator":
                    setRoles.add(rolesRepository.findByName(ERoles.ROLE_MODERATOR).orElseThrow(() ->
                            new RuntimeException("No administrator rights exist")));
                    break;
                case "user":
                default:
                    setRoles.add(rolesRepository.findByName(ERoles.ROLE_USER).orElseThrow(() ->
                            new RuntimeException("No administrator rights exist")));


            }
        });
        User user = modelMapper.map(signUpRequest, User.class);
        user.setListRoles(setRoles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User userCreated = userRepository.save(user);

        return modelMapper.map(userCreated,SignUpResponse.class);
    }
}
