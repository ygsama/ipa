package io.github.ygsama.oauth2server.security;

import io.github.ygsama.oauth2server.utils.SerializeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

/**
 * 从Redis里获取Token
 */
@Service
public class RedisTokenService {

    private static final Logger logger = LoggerFactory.getLogger(RedisTokenService.class);

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    //对于部分 不强制验证token的请求，会导致获取到用户为匿名用户，这里从redis中获取
    public OAuth2Authentication readAccessToken(String Authorization) {
        logger.info("Oauth2-根据token:{} 获取User",Authorization);
        RedisConnection conn = redisConnectionFactory.getConnection();
        String token=Authorization.replaceAll("Bearer ","");
        byte[] key= ("auth:" + token).getBytes();
        try {
            byte[] data=conn.get(key);
            if (data!=null){
                OAuth2Authentication authentication= (OAuth2Authentication) SerializeUtils.deserialize(data);
                logger.info("从redis中获取到的username:{}",authentication.getName());
                return authentication;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (conn!=null){
                conn.close();
            }
        }
        return null;
    }
}
