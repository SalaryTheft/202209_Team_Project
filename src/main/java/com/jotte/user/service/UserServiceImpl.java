package com.jotte.user.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.jotte.common.exception.BizDuplicateKeyException;
import com.jotte.common.exception.BizNotEffectedException;
import com.jotte.common.exception.BizNotFoundException;
import com.jotte.common.exception.BizPasswordNotMatchedException;
import com.jotte.common.vo.SearchVO;
import com.jotte.user.dao.IUserDAO;
import com.jotte.user.vo.GoogleOAuthRequestVO;
import com.jotte.user.vo.GoogleOAuthResponseVO;
import com.jotte.user.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final IUserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    /**
     * 이메일 또는 닉네임으로 계정 조회
     *
     * @param user 로그인 시도한 계정 정보(이메일 또는 닉네임과 비밀번호)
     * @return 일치하는 계정
     * @throws BizNotFoundException           이메일 또는 닉네임이 일치하는 계정이 없을 경우
     * @throws BizPasswordNotMatchedException 비밀번호가 일치하지 않을 경우
     */
    @Override
    public UserVO login(UserVO user) throws BizNotFoundException, BizPasswordNotMatchedException {
        UserVO originUser;
        if (user.getUserEmail().contains("@")) {
            originUser = userDAO.getUserByEmail(user.getUserEmail());
        } else {
            originUser = userDAO.getUserByNickname(user.getUserEmail());
        }
        if (originUser == null) {
            throw new BizNotFoundException();
        } else {
            if (passwordEncoder.matches(user.getUserPw(), originUser.getUserPw())) {
                return originUser;
            } else {
                throw new BizPasswordNotMatchedException();
            }
        }
    }

    /**
     * 회원 가입
     *
     * @param user 회원 가입 정보 (UUID는 자동 생성)
     * @return 가입된 회원 정보
     * @throws BizDuplicateKeyException 이메일 또는 닉네임이 중복될 경우
     * @throws BizNotEffectedException  회원 가입에 실패할 경우
     */
    @Override
    public UserVO register(UserVO user) throws BizDuplicateKeyException, BizNotEffectedException {
        if (userDAO.getUserByNickname(user.getUserNickname()) != null) {
            throw new BizDuplicateKeyException();
        } else if (userDAO.getUserByEmail(user.getUserEmail()) != null) {
            throw new BizDuplicateKeyException();
        } else {
            user.setUserUuid(java.util.UUID.randomUUID().toString());
            user.setUserPw(passwordEncoder.encode(user.getUserPw()));
            int result = userDAO.insertUser(user);
            if (result == 1) {
                try {
                    return getUserByEmail(user.getUserEmail());
                } catch (BizNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else {
                throw new BizNotEffectedException();
            }
        }
    }

    /**
     * UUID로 회원 조회
     *
     * @param userUuid 조회할 회원의 UUID
     * @return 조회된 회원 정보
     * @throws BizNotFoundException 회원이 존재하지 않을 경우
     */
    @Override
    public UserVO getUserByUuid(String userUuid) throws BizNotFoundException {
        UserVO user = userDAO.getUserByUuid(userUuid);
        if (user == null) {
            throw new BizNotFoundException();
        }
        return user;
    }

    @Override
    public UserVO getUserByEmail(String userEmail) throws BizNotFoundException {
        UserVO user = userDAO.getUserByEmail(userEmail);
        if (user == null) {
            throw new BizNotFoundException();
        }
        return user;
    }

    @Override
    public UserVO getUserByNickname(String userNickname) throws BizNotFoundException {
        UserVO user = userDAO.getUserByNickname(userNickname);
        if (user == null) {
            throw new BizNotFoundException();
        }
        return user;
    }

    @Override
    public List<UserVO> getUserList(SearchVO search) {
        search.setTotalRows(getUserCount(search));
        search.setPages();
        return userDAO.getUserList(search);
    }

    @Override
    public int getUserCount(SearchVO search) {
        return userDAO.getUserCount(search);
    }

    @Override
    public int insertUser(UserVO user) throws BizNotEffectedException {
        int cnt = userDAO.insertUser(user);
        if (cnt == 0) {
            throw new BizNotEffectedException();
        }
        return cnt;
    }

    @Override
    public int updateUser(UserVO user) throws BizNotEffectedException {
        user.setUserPw(passwordEncoder.encode(user.getUserPw()));
        int cnt = userDAO.updateUser(user);
        if (cnt == 0) {
            throw new BizNotEffectedException();
        }
        return cnt;
    }

    @Override
    public int deleteUser(UserVO user) throws BizNotEffectedException {
        int cnt = userDAO.deleteUser(user);
        if (cnt == 0) {
            throw new BizNotEffectedException();
        }
        return cnt;
    }

    @Override
    public String getGmail(String host, String code, boolean isSecure) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        GoogleOAuthRequestVO oauthReq = GoogleOAuthRequestVO.builder()
                .clientId("YOUR_CLIENT_ID_HERE")
                .clientSecret("YOUR_SECRET_HERE")
                .code(code)
                .redirectUri((host.contains("localhost") ? "http://" : "https://") + host + "/login/google/oauth")
                .grantType("authorization_code").build();

        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        ResponseEntity<String> resultEntity = restTemplate.postForEntity(
                UriComponentsBuilder.fromHttpUrl("https://oauth2.googleapis.com/token").build().toUriString(),
                oauthReq,
                String.class
        );

        GoogleOAuthResponseVO result = mapper.readValue(
                resultEntity.getBody(),
                new TypeReference<GoogleOAuthResponseVO>() {
                }
        );

        String jwtToken = result.getIdToken();
        String requestUrl = UriComponentsBuilder
                .fromHttpUrl("https://oauth2.googleapis.com/tokeninfo")
                .queryParam("id_token", jwtToken).encode().toUriString();
        String resultJson = restTemplate.getForObject(requestUrl, String.class);
        Map<String, String> userInfo = mapper.readValue(resultJson, new TypeReference<Map<String, String>>() {
        });

        // String accessToken = result.getAccessToken();
        return userInfo.get("email");
    }
}