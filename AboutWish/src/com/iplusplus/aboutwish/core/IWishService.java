package com.iplusplus.aboutwish.core;

import com.iplusplus.aboutwish.Wish;

/**
 * 
 * @author Forcs
 * 
 * 我要管理服务，提供提交、支持和转发愿望实体的服务接口
 *
 */
public interface IWishService {
	
	/**
	 * 获取愿望
	 * @return 愿望清单
	 */
	public Wish[] getWishs();
	
	/**
	 * 提交愿望实体
	 * @param wish 愿望
	 * @return 操作结果
	 */
	public int post(Wish wish);
	
	/**
	 * 支持愿望实体
	 * @param wish 愿望
	 * @return 操作结果
	 */
	public int support(Wish wish);
	
	/**
	 * 转发愿望实体
	 * @param wish 愿望
	 * @return 操作结果
	 */
	public int replay(Wish wish);

}
