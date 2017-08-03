package com.sandi.web.utils.api.cache;


public interface ICfgCacheLoadFSV {
   /**
    * 根据条件返回需要重载的列表
    * @param requestJson
    * @return
    * @throws Exception
    */
	public String reloadCacheList(String requestJson);
	/**
	 * 进行重载
	 * @param requestJson
	 * @return
	 * @throws Exception
	 */
	public String reloadCache(String requestJson);
	/**
	 * 获取缓存数据
	 * @param requestJson
	 * @return
	 * @throws Exception
	 */
	public String reloadDetailData(String requestJson);
	/**
	 * 从缓存中删除
	 * @param requestJson
	 * @return
	 * @throws Exception
	 */
	public String deleteFromCache(String requestJson);
	/**
	 * 加载主键或者权限缓存的数据
	 * @param requestJson
	 * @return
	 * @throws Exception
	 */
	public String loadIdOrSysCacheData(String requestJson)throws Exception;
}
