package ra.rest_apidemo.serviceImp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ra.rest_apidemo.dto.request.SignInRequest;
import ra.rest_apidemo.dto.request.SignUpRequest;
import ra.rest_apidemo.dto.response.SignInResponse;
import ra.rest_apidemo.dto.response.SignUpResponse;
import ra.rest_apidemo.dto.response.UserDTOResponse;
import ra.rest_apidemo.model.ERoles;
import ra.rest_apidemo.model.Roles;
import ra.rest_apidemo.model.User;
import ra.rest_apidemo.repository.RolesRepository;
import ra.rest_apidemo.repository.UserRepository;
import ra.rest_apidemo.security.jwt.JwtProvider;
import ra.rest_apidemo.security.principle.CustomUserDetail;
import ra.rest_apidemo.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;


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
        user.setStatus(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User userCreated = userRepository.save(user);
        SignUpResponse signUpResponse = modelMapper.map(userCreated, SignUpResponse.class);

        List<String> listUserRoles = new ArrayList<>();
        userCreated.getListRoles().stream().forEach(roles -> {
            listUserRoles.add(roles.getName().name());
        });
        signUpResponse.setListRoles(listUserRoles);
        return signUpResponse;
    }

    @Override
    public SignInResponse login(SignInRequest signInRequest) {
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(signInRequest.getUserName(),signInRequest.getPassword())
//        );
//        User user = userRepository.findByUserName(signInRequest.getUserName()).orElseThrow();
//        String token = jwtProvider.generateToken((UserDetails) user);
//        return new SignInResponse(user.getUserName(),user.getPassword(),
//                user.getEmail(),user.getFullName(),((UserDetails) user).getAuthorities(),
//                token);

        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    signInRequest.getUserName(), signInRequest.getPassword()
            ));
        } catch (Exception ex) {
            throw new RuntimeException("UserName or Password is not correct");
        }
        CustomUserDetail userDetail = (CustomUserDetail) authentication.getPrincipal();
        String accessToken = jwtProvider.generateAccessToken(userDetail);
        String refreshToken = jwtProvider.generateRefreshToken(userDetail);

        return new SignInResponse(userDetail.getUsername(),userDetail.getPassword(),
                userDetail.getEmail(),userDetail.getFullName(),userDetail.getAuthorities(),
                accessToken,refreshToken);
    }

    @Override
    public List<UserDTOResponse> findAll() {
        List<User> userList = userRepository.findAll();
        List<UserDTOResponse> userDTOResponseList = userList.stream().map(user ->
        modelMapper.map(user,UserDTOResponse.class)).collect(Collectors.toList());
        return userDTOResponseList;
    }
}
