package com.sandi.web.utils.common;

import com.sandi.web.utils.config.Global;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.*;

/**
 * Jedis Cache 工具类
 *
 * @author
 * @version 2014-6-29
 */
public class JedisUtils {

    private static Logger log = LoggerFactory.getLogger(JedisUtils.class);
    private static JedisConnectionFactory factory = SpringContextHolder.getBean(JedisConnectionFactory.class);
    private static final String moduleName = Global.getConfig("module_name");
    /**
     * 获取缓存
     *
     * @param key 键
     * @return 值
     */
    public static String get(String key) {
        return get(moduleName,key);
    }

    /**
     * 获取缓存
     *
     * @param key 键
     * @return 值
     */
    public static String get(String redisName,String key) {
        String value = null;
        try {
            if(factory.isCluster()){
                JedisCluster jedisCluster = factory.getJedisCluster(redisName);
                if (jedisCluster.exists(key)) {
                    value = jedisCluster.get(key);
                    value = StringUtils.isNotBlank(value) && !"nil".equalsIgnoreCase(value) ? value : null;
                }
            }else{
                Jedis jedis = factory.getJedisPool(redisName).getResource();
                if (jedis.exists(key)) {
                    value = jedis.get(key);
                    value = StringUtils.isNotBlank(value) && !"nil".equalsIgnoreCase(value) ? value : null;
                }
            }
            log.debug("get {} = {}", key, value);
        } catch (Exception e) {
            log.warn("get {} = {}", key, value, e);
        }
        return value;
    }


    /**
     * 设置缓存
     *
     * @param key          键
     * @param value        值
     * @return
     */
    public static String set(String key, String value) {
        return set(moduleName,key,value);
    }
    /**
     * 设置缓存
     *
     * @param key          键
     * @param value        值
     * @return
     */
    public static String set(String redisName,String key, String value) {
        String result = null;
        try {
            if(factory.isCluster()){
                JedisCluster jedisCluster = factory.getJedisCluster(redisName);
                result = jedisCluster.set(key, value);
            }else {
                Jedis jedis = factory.getJedisPool(redisName).getResource();
                result = jedis.set(key, value);
            }
            log.debug("set {} = {}", key, value);
        } catch (Exception e) {
            log.warn("set {} = {}", key, value, e);
        }
        return result;
    }

    /**
     * 设置缓存
     *
     * @param key          键
     * @param value        值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static String set(String key, String value, int cacheSeconds) {
        return set(moduleName,key,value,cacheSeconds);
    }
    /**
     * 设置缓存
     *
     * @param key          键
     * @param value        值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static String set(String redisName,String key, String value, int cacheSeconds) {
        String result = null;
        try {
            if(factory.isCluster()){
                JedisCluster jedisCluster = factory.getJedisCluster(redisName);
                result = jedisCluster.set(key, value);
                if (cacheSeconds != 0) {
                    jedisCluster.expire(key, cacheSeconds);
                }
            }else {
                Jedis jedis = factory.getJedisPool(redisName).getResource();
                result = jedis.set(key, value);
                if (cacheSeconds != 0) {
                    jedis.expire(key, cacheSeconds);
                }
            }
            log.debug("set {} = {}", key, value);
        } catch (Exception e) {
            log.warn("set {} = {}", key, value, e);
        }
        return result;
    }

    /**
     * 获取缓存
     *
     * @param key 键
     * @return 值
     */
    public static Object getObject(String key) {
        return getObject(moduleName,key);
    }
    /**
     * 获取缓存
     *
     * @param key 键
     * @return 值
     */
    public static Object getObject(String redisName,String key) {
        Object value = null;
        try {
            if(factory.isCluster()){
                JedisCluster jedisCluster = factory.getJedisCluster(redisName);
                if (jedisCluster.exists(getBytesKey(key))) {
                    value = toObject(jedisCluster.get(getBytesKey(key)));
                }
            }else {
                Jedis jedis = factory.getJedisPool(redisName).getResource();
                if (jedis.exists(getBytesKey(key))) {
                    value = toObject(jedis.get(getBytesKey(key)));
                }
            }
            log.debug("getObject {} = {}", key, value);
        } catch (Exception e) {
            log.warn("getObject {} = {}", key, value, e);
        }
        return value;
    }

    /**
     * 获取缓存,并刷新过期时间
     *
     * @param key
     * @param cacheSeconds
     * @return
     */
    public static Object getObject(String key, int cacheSeconds) {
        return getObject(moduleName,key,cacheSeconds);
    }

    /**
     * 获取缓存,并刷新过期时间
     *
     * @param key
     * @param cacheSeconds
     * @return
     */
    public static Object getObject(String redisName,String key, int cacheSeconds) {
        Object value = null;
        try {
            if(factory.isCluster()){
                JedisCluster jedisCluster = factory.getJedisCluster(redisName);
                value = getObject(key);
                if (value != null && cacheSeconds > 0) {
                    jedisCluster.expire(key, cacheSeconds);
                }
            }else {
                Jedis jedis = factory.getJedisPool(redisName).getResource();
                value = getObject(key);
                if (value != null && cacheSeconds > 0) {
                    jedis.expire(key, cacheSeconds);
                }
            }
            log.debug("getObject {} = {}", key, value);
        } catch (Exception e) {
            log.warn("getObject {} = {}", key, value, e);
        }
        return value;
    }

    /**
     * 设置缓存
     *
     * @param key          键
     * @param value        值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static String setObject(String key, Object value, int cacheSeconds) {
        return setObject(moduleName,key,value,cacheSeconds);
    }
    /**
     * 设置缓存
     *
     * @param key          键
     * @param value        值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static String setObject(String redisName,String key, Object value, int cacheSeconds) {
        String result = null;
        try {
            if(factory.isCluster()){
                JedisCluster jedisCluster = factory.getJedisCluster(redisName);
                result = jedisCluster.set(getBytesKey(key), toBytes(value));
                if (cacheSeconds != 0) {
                    jedisCluster.expire(key, cacheSeconds);
                }
            }else {
                Jedis jedis = factory.getJedisPool(redisName).getResource();
                result = jedis.set(getBytesKey(key), toBytes(value));
                if (cacheSeconds != 0) {
                    jedis.expire(key, cacheSeconds);
                }
            }
            log.debug("setObject {} = {}", key, value);
        } catch (Exception e) {
            log.warn("setObject {} = {}", key, value, e);
        }
        return result;
    }

    /**
     * 获取List缓存
     *
     * @param key 键
     * @return 值
     */
    public static List<String> getList(String key) {
        return getList(moduleName,key);
    }
    /**
     * 获取List缓存
     *
     * @param key 键
     * @return 值
     */
    public static List<String> getList(String redisName,String key) {
        List<String> value = null;
        try {
            if(factory.isCluster()){
                JedisCluster jedisCluster = factory.getJedisCluster(redisName);
                if (jedisCluster.exists(key)) {
                    value = jedisCluster.lrange(key, 0, -1);
                }
            }else {
                Jedis jedis = factory.getJedisPool(redisName).getResource();
                if (jedis.exists(key)) {
                    value = jedis.lrange(key, 0, -1);
                }
            }
            log.debug("getList {} = {}", key, value);
        } catch (Exception e) {
            log.warn("getList {} = {}", key, value, e);
        }
        return value;
    }

    /**
     * 设置List缓存
     *
     * @param key          键
     * @param value        值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static long setList(String key, List<String> value, int cacheSeconds) {
        return setList(moduleName,key,value,cacheSeconds);
    }

    /**
     * 设置List缓存
     *
     * @param key          键
     * @param value        值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static long setList(String redisName,String key, List<String> value, int cacheSeconds) {
        long result = 0;
        try {
            if(factory.isCluster()){
                JedisCluster jedisCluster = factory.getJedisCluster(redisName);
                if (jedisCluster.exists(key)) {
                    jedisCluster.del(key);
                }
                result = jedisCluster.rpush(key, value.toArray(new String[0]));
                if (cacheSeconds != 0) {
                    jedisCluster.expire(key, cacheSeconds);
                }
            }else {
                Jedis jedis = factory.getJedisPool(redisName).getResource();
                if (jedis.exists(key)) {
                    jedis.del(key);
                }
                result = jedis.rpush(key, value.toArray(new String[0]));
                if (cacheSeconds != 0) {
                    jedis.expire(key, cacheSeconds);
                }
            }
            log.debug("setList {} = {}", key, value);
        } catch (Exception e) {
            log.warn("setList {} = {}", key, value, e);
        }
        return result;
    }

    /**
     * 向List缓存中添加值
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public static long listAdd(String key, String value) {
        return listAdd(moduleName,key,value);
    }

    /**
     * 向List缓存中添加值
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public static long listAdd(String key, String[] value) {
        return listAdd(moduleName,key,value);
    }
    /**
     * 向List缓存中添加值
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public static long listAdd(String redisName,String key, String... value) {
        long result = 0;
        try {
            if(factory.isCluster()){
                JedisCluster jedisCluster = factory.getJedisCluster(redisName);
                if (value != null && value.length > 0) {
                    result = jedisCluster.rpush(key, value);
                }
            }else {
                Jedis jedis = factory.getJedisPool(redisName).getResource();
                if (value != null && value.length > 0) {
                    result = jedis.rpush(key, value);
                }
            }
            log.debug("listAdd {} = {}", key, value);
        } catch (Exception e) {
            log.warn("listAdd {} = {}", key, value, e);
        }
        return result;
    }

    /**
     * 从list中弹出一个值
     *
     * @param key 键
     */
    public static String listPop(String key) {
        return listPop(moduleName,key);
    }
    /**
     * 从list中弹出一个值
     *
     * @param key 键
     */
    public static String listPop(String redisName,String key) {
        String result = null;
        try {
            if(factory.isCluster()){
                JedisCluster jedisCluster = factory.getJedisCluster(redisName);
                result = jedisCluster.lpop(key);
            }else {
                Jedis jedis = factory.getJedisPool(redisName).getResource();
                result = jedis.lpop(key);
            }
            log.debug("listPop {} = {}", key);
        } catch (Exception e) {
            log.warn("listPop {} = {}", key, e);
        }
        return result;
    }

    /**
     * 获取list中有多少数据
     *
     * @param key 键
     */
    public static long listSize(String key) {
        return listSize(moduleName,key);
    }
    /**
     * 获取list中有多少数据
     *
     * @param key 键
     */
    public static long listSize(String redisName,String key) {
        long result = 0;
        try {
            if(factory.isCluster()){
                JedisCluster jedisCluster = factory.getJedisCluster(redisName);
                result = jedisCluster.llen(key);
            }else {
                Jedis jedis = factory.getJedisPool(redisName).getResource();
                result = jedis.llen(key);
            }
            log.debug("listPop {} = {}", key);
        } catch (Exception e) {
            log.warn("listPop {} = {}", key, e);
        }
        return result;
    }

    /**
     * 获取List缓存
     *
     * @param key 键
     * @return 值
     */
    public static List<Object> getObjectList(String key) {
        return getObjectList(moduleName,key);
    }
    /**
     * 获取List缓存
     *
     * @param key 键
     * @return 值
     */
    public static List<Object> getObjectList(String redisName,String key) {
        List<Object> value = null;
        try {
            if(factory.isCluster()){
                JedisCluster jedisCluster = factory.getJedisCluster(redisName);
                if (jedisCluster.exists(getBytesKey(key))) {
                    List<byte[]> list = jedisCluster.lrange(getBytesKey(key), 0, -1);
                    value = new ArrayList();
                    for (byte[] bs : list) {
                        value.add(toObject(bs));
                    }
                    log.debug("getObjectList {} = {}", key, value);
                }
            }else {
                Jedis jedis = factory.getJedisPool(redisName).getResource();
                if (jedis.exists(getBytesKey(key))) {
                    List<byte[]> list = jedis.lrange(getBytesKey(key), 0, -1);
                    value = new ArrayList();
                    for (byte[] bs : list) {
                        value.add(toObject(bs));
                    }
                    log.debug("getObjectList {} = {}", key, value);
                }
            }
        } catch (Exception e) {
            log.warn("getObjectList {} = {}", key, value, e);
        }
        return value;
    }

    /**
     * 设置List缓存
     *
     * @param key          键
     * @param value        值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static long setObjectList(String key, List<Object> value, int cacheSeconds) {
        return setObjectList(moduleName,key,value,cacheSeconds);
    }
    /**
     * 设置List缓存
     *
     * @param key          键
     * @param value        值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static long setObjectList(String redisName,String key, List<Object> value, int cacheSeconds) {
        long result = 0;
        try {
            if(factory.isCluster()){
                JedisCluster jedisCluster = factory.getJedisCluster(redisName);
                if (jedisCluster.exists(getBytesKey(key))) {
                    jedisCluster.del(key);
                }
                byte[][] bytes = new byte[value.size()][];
                for (int i = 0; i < value.size(); i++) {
                    bytes[i] = toBytes(value.get(i));
                }
                result = jedisCluster.rpush(getBytesKey(key), bytes);
                if (cacheSeconds != 0) {
                    jedisCluster.expire(key, cacheSeconds);
                }
            }else {
                Jedis jedis = factory.getJedisPool(redisName).getResource();
                if (jedis.exists(getBytesKey(key))) {
                    jedis.del(key);
                }
                byte[][] bytes = new byte[value.size()][];
                for (int i = 0; i < value.size(); i++) {
                    bytes[i] = toBytes(value.get(i));
                }
                result = jedis.rpush(getBytesKey(key), bytes);
                if (cacheSeconds != 0) {
                    jedis.expire(key, cacheSeconds);
                }
            }
            log.debug("setObjectList {} = {}", key, value);
        } catch (Exception e) {
            log.warn("setObjectList {} = {}", key, value, e);
        }
        return result;
    }
    /**
     * 向List缓存中添加值
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public static long listObjectAdd(String key, Object... value) {
        return listObjectAdd(moduleName,key,value);
    }


    /**
     * 向List缓存中添加值
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public static long listObjectAdd(String redisName,String key, Object... value) {
        long result = 0;
        try {
            if(factory.isCluster()){
                JedisCluster jedisCluster = factory.getJedisCluster(redisName);
                byte[][] bytes = new byte[value.length][];
                for (int i = 0; i < value.length; i++) {
                    bytes[i] = toBytes(value[i]);
                }
                result = jedisCluster.rpush(getBytesKey(key), bytes);
            }else {
                Jedis jedis = factory.getJedisPool(redisName).getResource();
                byte[][] bytes = new byte[value.length][];
                for (int i = 0; i < value.length; i++) {
                    bytes[i] = toBytes(value[i]);
                }
                result = jedis.rpush(getBytesKey(key), bytes);
            }
            log.debug("listObjectAdd {} = {}", key, value);
        } catch (Exception e) {
            log.warn("listObjectAdd {} = {}", key, value, e);
        }
        return result;
    }

    /**
     * 获取缓存
     *
     * @param key 键
     * @return 值
     */
    public static Set<String> getSet(String key) {
        return getSet(moduleName,key);
    }
    /**
     * 获取缓存
     *
     * @param key 键
     * @return 值
     */
    public static Set<String> getSet(String redisName,String key) {
        Set<String> value = null;
        try {
            if(factory.isCluster()){
                JedisCluster jedisCluster = factory.getJedisCluster(redisName);
                if (jedisCluster.exists(key)) {
                    value = jedisCluster.smembers(key);
                }
            }else {
                Jedis jedis = factory.getJedisPool(redisName).getResource();
                if (jedis.exists(key)) {
                    value = jedis.smembers(key);
                }
            }
            log.debug("getSet {} = {}", key, value);
        } catch (Exception e) {
            log.warn("getSet {} = {}", key, value, e);
        }
        return value;
    }
    /**
     * 设置Set缓存
     *
     * @param key          键
     * @param value        值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static long setSet(String key, Set<String> value, int cacheSeconds) {
        return setSet(moduleName,key,value,cacheSeconds);
    }
    /**
     * 设置Set缓存
     *
     * @param key          键
     * @param value        值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static long setSet(String redisName,String key, Set<String> value, int cacheSeconds) {
        long result = 0;
        try {
            if(factory.isCluster()){
                JedisCluster jedisCluster = factory.getJedisCluster(redisName);
                if (jedisCluster.exists(key)) {
                    jedisCluster.del(key);
                }
                result = jedisCluster.sadd(key, (String[]) value.toArray());
                if (cacheSeconds != 0) {
                    jedisCluster.expire(key, cacheSeconds);
                }
            }else {
                Jedis jedis = factory.getJedisPool(redisName).getResource();
                if (jedis.exists(key)) {
                    jedis.del(key);
                }
                result = jedis.sadd(key, (String[]) value.toArray());
                if (cacheSeconds != 0) {
                    jedis.expire(key, cacheSeconds);
                }
            }
            log.debug("setSet {} = {}", key, value);
        } catch (Exception e) {
            log.warn("setSet {} = {}", key, value, e);
        }
        return result;
    }

    /**
     * 向Set缓存中添加值
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public static long setSetAdd(String key, String... value) {
        return setSetAdd(moduleName,key,value);
    }
    /**
     * 向Set缓存中添加值
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public static long setSetAdd(String redisName,String key, String... value) {
        long result = 0;
        try {
            if(factory.isCluster()){
                JedisCluster jedisCluster = factory.getJedisCluster(redisName);
                result = jedisCluster.sadd(key, value);
            }else {
                Jedis jedis = factory.getJedisPool(redisName).getResource();
                result = jedis.sadd(key, value);
            }
            log.debug("setSetAdd {} = {}", key, value);
        } catch (Exception e) {
            log.warn("setSetAdd {} = {}", key, value, e);
        }
        return result;
    }

    /**
     * 获取缓存
     *
     * @param key 键
     * @return 值
     */
    public static Set<Object> getObjectSet(String key) {
        return getObjectSet(moduleName,key);
    }
    /**
     * 获取缓存
     *
     * @param key 键
     * @return 值
     */
    public static Set<Object> getObjectSet(String redisName,String key) {
        Set<Object> value = null;
        try {
            if(factory.isCluster()){
                JedisCluster jedisCluster = factory.getJedisCluster(redisName);
                if (jedisCluster.exists(getBytesKey(key))) {
                    value = new HashSet();
                    Set<byte[]> set = jedisCluster.smembers(getBytesKey(key));
                    for (byte[] bs : set) {
                        value.add(toObject(bs));
                    }
                    log.debug("getObjectSet {} = {}", key, value);
                }
            }else {
                Jedis jedis = factory.getJedisPool(redisName).getResource();
                if (jedis.exists(getBytesKey(key))) {
                    value = new HashSet();
                    Set<byte[]> set = jedis.smembers(getBytesKey(key));
                    for (byte[] bs : set) {
                        value.add(toObject(bs));
                    }
                    log.debug("getObjectSet {} = {}", key, value);
                }
            }
        } catch (Exception e) {
            log.warn("getObjectSet {} = {}", key, value, e);
        }
        return value;
    }
    /**
     * 设置Set缓存
     *
     * @param key          键
     * @param value        值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static long setObjectSet(String key, Set<Object> value, int cacheSeconds) {
        return setObjectSet(moduleName,key,value,cacheSeconds);
    }
    /**
     * 设置Set缓存
     *
     * @param key          键
     * @param value        值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static long setObjectSet(String redisName,String key, Set<Object> value, int cacheSeconds) {
        long result = 0;
        try {
            if(factory.isCluster()){
                JedisCluster jedisCluster = factory.getJedisCluster(redisName);
                if (jedisCluster.exists(getBytesKey(key))) {
                    jedisCluster.del(key);
                }
                byte[][] bytes = new byte[value.size()][];
                Iterator iter = value.iterator();
                int idx = 0;
                while (iter.hasNext()) {
                    bytes[idx++] = toBytes(iter.next());
                }
                result = jedisCluster.sadd(getBytesKey(key), bytes);
                if (cacheSeconds != 0) {
                    jedisCluster.expire(key, cacheSeconds);
                }
            }else {
                Jedis jedis = factory.getJedisPool(redisName).getResource();
                if (jedis.exists(getBytesKey(key))) {
                    jedis.del(key);
                }
                byte[][] bytes = new byte[value.size()][];
                Iterator iter = value.iterator();
                int idx = 0;
                while (iter.hasNext()) {
                    bytes[idx++] = toBytes(iter.next());
                }
                result = jedis.sadd(getBytesKey(key), bytes);
                if (cacheSeconds != 0) {
                    jedis.expire(key, cacheSeconds);
                }
            }
            log.debug("setObjectSet {} = {}", key, value);
        } catch (Exception e) {
            log.warn("setObjectSet {} = {}", key, value, e);
        }
        return result;
    }
    /**
     * 向Set缓存中添加值
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public static long setSetObjectAdd(String key, Object... value) {
        return setSetObjectAdd(moduleName,key,value);
    }
    /**
     * 向Set缓存中添加值
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public static long setSetObjectAdd(String redisName,String key, Object... value) {
        long result = 0;
        try {
            if(factory.isCluster()){
                JedisCluster jedisCluster = factory.getJedisCluster(redisName);
                byte[][] bytes = new byte[value.length][];
                for (int i = 0; i < value.length; i++) {
                    bytes[i] = toBytes(value[i]);
                }
                result = jedisCluster.rpush(getBytesKey(key), bytes);
            }else {
                Jedis jedis = factory.getJedisPool(redisName).getResource();
                byte[][] bytes = new byte[value.length][];
                for (int i = 0; i < value.length; i++) {
                    bytes[i] = toBytes(value[i]);
                }
                result = jedis.rpush(getBytesKey(key), bytes);
            }
            log.debug("setSetObjectAdd {} = {}", key, value);
        } catch (Exception e) {
            log.warn("setSetObjectAdd {} = {}", key, value, e);
        }
        return result;
    }
    /**
     * 获取Map缓存
     *
     * @param key 键
     * @return 值
     */
    public static Map<String, String> getMap(String key) {
        return getMap(moduleName,key);
    }
    /**
     * 获取Map缓存
     *
     * @param key 键
     * @return 值
     */
    public static Map<String, String> getMap(String redisName,String key) {
        Map<String, String> value = null;
        try {
            if(factory.isCluster()){
                JedisCluster jedisCluster = factory.getJedisCluster(redisName);
                if (jedisCluster.exists(key)) {
                    value = jedisCluster.hgetAll(key);
                }
            }else {
                Jedis jedis = factory.getJedisPool(redisName).getResource();
                if (jedis.exists(key)) {
                    value = jedis.hgetAll(key);
                }
            }
            log.debug("getMap {} = {}", key, value);
        } catch (Exception e) {
            log.warn("getMap {} = {}", key, value, e);
        }
        return value;
    }
    /**
     * 设置Map缓存
     *
     * @param key          键
     * @param value        值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static String setMap(String key, Map<String, String> value, int cacheSeconds) {
        return setMap(moduleName,key,value,cacheSeconds);
    }
    /**
     * 设置Map缓存
     *
     * @param key          键
     * @param value        值
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    public static String setMap(String redisName,String key, Map<String, String> value, int cacheSeconds) {
        String result = null;
        try {
            if(factory.isCluster()){
                JedisCluster jedisCluster = factory.getJedisCluster(redisName);
                if (jedisCluster.exists(key)) {
                    jedisCluster.del(key);
                }
                result = jedisCluster.hmset(key, value);
                if (cacheSeconds != 0) {
                    jedisCluster.expire(key, cacheSeconds);
                }
            }else {
                Jedis jedis = factory.getJedisPool(redisName).getResource();
                if (jedis.exists(key)) {
                    jedis.del(key);
                }
                result = jedis.hmset(key, value);
                if (cacheSeconds != 0) {
                    jedis.expire(key, cacheSeconds);
                }
            }
            log.debug("setMap {} = {}", key, value);
        } catch (Exception e) {
            log.warn("setMap {} = {}", key, value, e);
        }
        return result;
    }
    /**
     * 向Map缓存中添加值
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public static String mapPut(String key, Map<String, String> value) {
        return mapPut(moduleName,key,value);
    }
    /**
     * 向Map缓存中添加值
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public static String mapPut(String redisName,String key, Map<String, String> value) {
        String result = null;
        try {
            if(factory.isCluster()){
                JedisCluster jedisCluster = factory.getJedisCluster(redisName);
                result = jedisCluster.hmset(key, value);
            }else {
                Jedis jedis = factory.getJedisPool(redisName).getResource();
                result = jedis.hmset(key, value);
            }
            log.debug("mapPut {} = {}", key, value);
        } catch (Exception e) {
            log.warn("mapPut {} = {}", key, value, e);
        }
        return result;
    }
    /**
     * 移除Map缓存中的值
     *
     * @param key    键
     * @param mapKey 值
     * @return
     */
    public static long mapRemove(String key, String mapKey) {
        return mapRemove(moduleName,key,mapKey);
    }
    /**
     * 移除Map缓存中的值
     *
     * @param key    键
     * @param mapKey 值
     * @return
     */
    public static long mapRemove(String redisName,String key, String mapKey) {
        long result = 0;
        try {
            if(factory.isCluster()){
                JedisCluster jedisCluster = factory.getJedisCluster(redisName);
                result = jedisCluster.hdel(key, mapKey);
            }else {
                Jedis jedis = factory.getJedisPool(redisName).getResource();
                result = jedis.hdel(key, mapKey);
            }
            log.debug("mapRemove {}  {}", key, mapKey);
        } catch (Exception e) {
            log.warn("mapRemove {}  {}", key, mapKey, e);
        }
        return result;
    }
    /**
     * 判断Map缓存中的Key是否存在
     *
     * @param key    键
     * @param mapKey 值
     * @return
     */
    public static boolean mapExists(String key, String mapKey) {
        return mapExists(moduleName,key,mapKey);
    }
    /**
     * 判断Map缓存中的Key是否存在
     *
     * @param key    键
     * @param mapKey 值
     * @return
     */
    public static boolean mapExists(String redisName,String key, String mapKey) {
        boolean result = false;
        try {
            if(factory.isCluster()){
                JedisCluster jedisCluster = factory.getJedisCluster(redisName);
                result = jedisCluster.hexists(key, mapKey);
            }else {
                Jedis jedis = factory.getJedisPool(redisName).getResource();
                result = jedis.hexists(key, mapKey);
            }
            log.debug("mapExists {}  {}", key, mapKey);
        } catch (Exception e) {
            log.warn("mapExists {}  {}", key, mapKey, e);
        }
        return result;
    }

    /**
     * 删除缓存
     *
     * @param key 键
     * @return
     */
    public static long del(String key) {
        return del(moduleName,key);
    }
    /**
     * 删除缓存
     *
     * @param key 键
     * @return
     */
    public static long del(String redisName,String key) {
        long result = 0;
        try {
            if(factory.isCluster()){
                JedisCluster jedisCluster = factory.getJedisCluster(redisName);
                if (jedisCluster.exists(key)) {
                    result = jedisCluster.del(key);
                    log.debug("del {}", key);
                } else {
                    log.debug("del {} not exists", key);
                }
            }else {
                Jedis jedis = factory.getJedisPool(redisName).getResource();
                if (jedis.exists(key)) {
                    result = jedis.del(key);
                    log.debug("del {}", key);
                } else {
                    log.debug("del {} not exists", key);
                }
            }
        } catch (Exception e) {
            log.warn("del {}", key, e);
        }
        return result;
    }
    /**
     * 删除缓存
     *
     * @param key 键
     * @return
     */
    public static long delObject(String key) {
        return delObject(moduleName,key);
    }
    /**
     * 删除缓存
     *
     * @param key 键
     * @return
     */
    public static long delObject(String redisName,String key) {
        long result = 0;
        try {
            if(factory.isCluster()){
                JedisCluster jedisCluster = factory.getJedisCluster(redisName);
                if (jedisCluster.exists(getBytesKey(key))) {
                    result = jedisCluster.del(getBytesKey(key));
                    log.debug("delObject {}", key);
                } else {
                    log.debug("delObject {} not exists", key);
                }
            }else {
                Jedis jedis = factory.getJedisPool(redisName).getResource();
                if (jedis.exists(getBytesKey(key))) {
                    result = jedis.del(getBytesKey(key));
                    log.debug("delObject {}", key);
                } else {
                    log.debug("delObject {} not exists", key);
                }
            }
        } catch (Exception e) {
            log.warn("delObject {}", key, e);
        }
        return result;
    }
    /**
     * 缓存是否存在
     *
     * @param key 键
     * @return
     */
    public static boolean exists(String key) {
        return exists(moduleName,key);
    }
    /**
     * 缓存是否存在
     *
     * @param key 键
     * @return
     */
    public static boolean exists(String redisName,String key) {
        boolean result = false;
        try {
            if(factory.isCluster()){
                JedisCluster jedisCluster = factory.getJedisCluster(redisName);
                result = jedisCluster.exists(key);
            }else {
                Jedis jedis = factory.getJedisPool(redisName).getResource();
                result = jedis.exists(key);
            }
            log.debug("exists {}", key);
        } catch (Exception e) {
            log.warn("exists {}", key, e);
        }
        return result;
    }
    /**
     * 缓存是否存在
     *
     * @param key 键
     * @return
     */
    public static boolean existsObject(String key) {
        return existsObject(moduleName,key);
    }
    /**
     * 缓存是否存在
     *
     * @param key 键
     * @return
     */
    public static boolean existsObject(String redisName,String key) {
        boolean result = false;
        try {
            if(factory.isCluster()){
                JedisCluster jedisCluster = factory.getJedisCluster(redisName);
                result = jedisCluster.exists(getBytesKey(key));
            }else {
                Jedis jedis = factory.getJedisPool(redisName).getResource();
                result = jedis.exists(getBytesKey(key));
            }
            log.debug("existsObject {}", key);
        } catch (Exception e) {
            log.warn("existsObject {}", key, e);
        }
        return result;
    }


    /**
     * 获取byte[]类型Key
     *
     * @param object
     * @return
     */
    private static byte[] getBytesKey(Object object) {
        if (object instanceof String) {
            return StringUtils.getBytes((String) object);
        } else {
            return ObjectUtils.serialize(object);
        }
    }

    /**
     * Object转换byte[]类型
     *
     * @param object
     * @return
     */
    private static byte[] toBytes(Object object) {
        return ObjectUtils.serialize(object);
    }

    /**
     * byte[]型转换Object
     *
     * @param bytes
     * @return
     */
    public static Object toObject(byte[] bytes) {
        return ObjectUtils.unserialize(bytes);
    }
    public static Set<String> getKeys(String pattern) {
        return getKeys(moduleName,pattern);
    }
    public static Set<String> getKeys(String redisName,String pattern) {
        Set<String> result = new HashSet<String>();
        try {
            if(factory.isCluster()){
                JedisCluster jedisCluster = factory.getJedisCluster(redisName);
                Map<String, JedisPool> clusterNodes = jedisCluster.getClusterNodes();
                Set<String> keys= clusterNodes.keySet();
                for(String key : keys){
                    JedisPool jedisPool = clusterNodes.get(key);
                    Jedis jedis = jedisPool.getResource();
                    result.addAll(jedis.keys(pattern));
                }
            }else {
                Jedis jedis = factory.getJedisPool(redisName).getResource();
                result = jedis.keys(pattern);
            }

        } catch (Exception e) {
            log.error("", e);
        }
        return result;
    }

    public static void removeKeys(Set<String> keys) {
        removeKeys(moduleName,keys);
    }

    public static void removeKeys(String moduleName,Set<String> keys) {
        try {
            for (String key : keys) {
                del(key);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
