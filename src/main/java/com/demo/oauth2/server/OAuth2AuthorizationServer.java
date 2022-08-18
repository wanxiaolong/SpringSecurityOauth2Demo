package com.demo.oauth2.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //为了方便测试，在内存中创建client的账号和密码，实际开发中应放在数据库中
        clients.inMemory()
                //client账户
                .withClient("clientapp")
                //client密码
                .secret(passwordEncoder.encode("secret"))
                //配置回调地址，选填
                .redirectUris("http://localhost:9000/callback")
                //授权模式：授权码
                .authorizedGrantTypes("authorization_code")
                //可授权的scope，默认为空，表示用户有所有权限
                .scopes("read_userinfo", "read_contacts")
                .accessTokenValiditySeconds(3600);

                //.and().withClient()... //可用.and()方法配置另一个client
    }
}
