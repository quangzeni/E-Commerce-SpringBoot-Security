package ra.rest_apidemo.service;

import org.springframework.stereotype.Service;
import ra.rest_apidemo.dto.request.SignUpRequest;
import ra.rest_apidemo.dto.response.SignUpResponse;

import java.util.Map;
import java.util.Objects;

@Service
public interface UserService {
//    Map<String, Objects> findByEmailorStatus(int page, int size, String userEmail,String direction, String orderBy);
    SignUpResponse register(SignUpRequest signUpRequest);
}
