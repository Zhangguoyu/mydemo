package com.iplusplus.aboutwish.core;

import com.iplusplus.aboutwish.Wish;
import com.iplusplus.aboutwish.core.WishService.ResultInfo;

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
	 * @return TODO
	 */
	public ResultInfo requestWishs();
	
	/**
	 * 提交愿望实体
	 * @param wish 愿望
	 * @return TODO
	 */
	public ResultInfo postWish(Wish wish);
	
	/**
	 * 支持愿望实体
	 * @param wish 愿望
	 * @return TODO
	 */
	public ResultInfo postSupport(Wish wish);
	
	/**
	 * 转发愿望实体
	 * @param wish 愿望
	 * @return TODO
	 */
	public ResultInfo postReplay(Wish wish);

}
