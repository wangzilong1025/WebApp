package com.sandi.web.utils.common;

import com.sandi.web.utils.config.PropertiesLoader;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;
import redis.clients.util.Pool;

import java.util.*;

/**
 * Created by dizl on 2016/11/20.
 */
public class JedisConnectionFactory {
    private static final Logger logger = LoggerFactory.getLogger(JedisConnectionFactory.class);

    private Map<String,Pool<Jedis>> jedisPools = new HashMap();
    private boolean isCluster = false;
    private Map<String,JedisCluster> jedisClusters = new HashMap();

    public JedisConnectionFactory(String filePath, String deployModel, int connectionTimeout, int soTimeout, int maxRedirections, String password, String masterName, GenericObjectPoolConfig poolConfig) {
        try {
            Map<String,List<String>> redisMap = parsePropertiesFile(filePath);
            if (redisMap == null || redisMap.size() <= 0) {
               throw new Exception("无法解析redis配置文件");
            }
            if(StringUtils.isBlank(password)){
                password = null;
            }
            if(soTimeout<=0){
                soTimeout = 2000;
            }
            if(connectionTimeout<=0){
                connectionTimeout = 2000;
            }
            if(maxRedirections<=0){
                maxRedirections = 5;
            }
            if (StringUtils.equalsIgnoreCase("standalone", deployModel)) {
                Set<String> keys = redisMap.keySet();
                Iterator<String> iterator = keys.iterator();
                if (iterator.hasNext()) {
                    String key = iterator.next();
                    List<String> redis = redisMap.get(key);
                    if(redis!=null && redis.size()>0){
                        String node = redis.get(0);
                        String[] arr = node.split(":");
                        if (arr.length != 2) {
                            throw new Exception("redis配置的主机和端口号有误" + node);
                        }
                        JedisPool jedisPool = new JedisPool(poolConfig, arr[0], Integer.valueOf(arr[1]), connectionTimeout, soTimeout, password, 0, null);
                        jedisPools.put(key,jedisPool);
                    }
                }
            } else if (StringUtils.equalsIgnoreCase("sentinel", deployModel)) {
                Set<String> keys = redisMap.keySet();
                Iterator<String> iterator = keys.iterator();
                while(iterator.hasNext()){
                    String key = iterator.next();
                    List<String> lists = redisMap.get(key);
                    Set set = new HashSet();
                    if(lists!=null && lists.size()>0){
                        for(String str : lists){
                            if(str!=null && StringUtils.isNotEmpty(str)){
                                set.add(str);
                            }
                        }
                    }
                    JedisSentinelPool jedisPool = new JedisSentinelPool(masterName, set, poolConfig, connectionTimeout, soTimeout, password, 0, null);
                    jedisPools.put(key,jedisPool);
                }
            } else if (StringUtils.equalsIgnoreCase("cluster", deployModel)) {
                Set<String> keys = redisMap.keySet();
                Iterator<String> iterator = keys.iterator();
                while(iterator.hasNext()){
                    String key = iterator.next();
                    List<String> lists = redisMap.get(key);
                    Set<HostAndPort> nodes = new HashSet<HostAndPort>();
                    if(lists!=null && lists.size()>0){
                        for(String str : lists){
                            String[] arr = str.split(":");
                            if (arr.length != 2) {
                                throw new Exception("redis配置的主机和端口号有误" + str);
                            }
                            nodes.add(new HostAndPort(arr[0], Integer.valueOf(arr[1])));
                        }
                    }
                    JedisCluster jedisCluster = new JedisCluster(nodes, connectionTimeout, soTimeout, maxRedirections, poolConfig);
                    jedisClusters.put(key,jedisCluster);
                }
                isCluster = true;
            }
        } catch (Exception e) {
            logger.error("连接redis失败", e);
        }
    }

    private Map<String,List<String>> parsePropertiesFile(String filePath) {
        PropertiesLoader loader = new PropertiesLoader(filePath);
        Properties properties = loader.getProperties();
        Collection  collection = null;
        Map<String,List<String>> retMap = new HashMap();
        if (properties != null) {
            Set keys = properties.keySet();
            Iterator iter = keys.iterator();
            while(iter.hasNext()){
                String key = iter.next().toString();
                String value = properties.get(key).toString();
                String[] strs = key.split("\\.");
                if(strs.length>0){
                    key = strs[0];
                }
                List lists = retMap.get(key);
                if(lists==null){
                    lists = new ArrayList();
                    retMap.put(key,lists);
                }
                lists.add(value);
            }
        }
        return retMap;
    }

    public Pool<Jedis> getJedisPool(String redisName){
        if(jedisPools.size()==1){
            return jedisPools.values().iterator().next();
        }
        return jedisPools.get(redisName);
    }

    public boolean isCluster() {
        return isCluster;
    }

    public JedisCluster getJedisCluster(String redisName) {
        if(jedisClusters.size()==1){
            return jedisClusters.values().iterator().next();
        }
        return jedisClusters.get(redisName);
    }
}