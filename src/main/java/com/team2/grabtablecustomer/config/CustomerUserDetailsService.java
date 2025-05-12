package com.team2.grabtablecustomer.config;

import com.team2.grabtablecustomer.domain.user.entity.Membership;
import com.team2.grabtablecustomer.domain.user.entity.User;
import com.team2.grabtablecustomer.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerUserDetailsService implements UserDetailsService {

    /** 기본 form ui를 통한 로그인은 그대로 (아직 별도의 login 페이지를 생성 안했으므로)
     *  username, password가 user / console password 사용 X => UserDetailsService를 제공하므로
     *  form ui에 사용자가 입력한 username값이 loadUserByUsername()의 파라미터로 전달 (username)
     *  DB를 통해서 (JPA의 경우 UserRepository를 거쳐서) username으로 select username, password 를 가져와서
     *  UserDetails 구현 객체를 만들어서 return 해줘야한다
     *  UserDetails 구현 객체는 우선 Spring security에서 제공하는 org.springframework.security.core.userdetails.User 를 사용
     */

    /**  ROLE 고려 O (실제) => User Entity를 이용한 UserDetails
     */
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        // Security Config에서 롤을 파악하려면 접두사 "ROLE_" 처리를 해줘야 한다.
        List<SimpleGrantedAuthority> authorities = user.getMemberships().stream()
                .map(m -> "ROLE_" + m.getName())
                .map(SimpleGrantedAuthority::new)
                .toList();

        return CustomerUserDetails.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .authorities(authorities)
                .build();
    }
}
