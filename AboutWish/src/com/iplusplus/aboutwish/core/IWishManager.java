package com.iplusplus.aboutwish.core;

import com.iplusplus.aboutwish.Wish;

public interface IWishManager {
	
	/**
	 * 提交愿望实体
	 * @param wish 愿望
	 * @return TODO
	 */
	public boolean post(Wish wish);
	
	/**
	 * 支持愿望实体
	 * @param wish 愿望
	 * @return 最新的支持数量
	 */
	public int support(Wish wish);
	
	/**
	 * 转发愿望实体
	 * @param wish 愿望
	 * @return 最新的转发数量
	 */
	public int replay(Wish wish);
	
	/**
	 * 愿望清单中愿望的数量
	 * @return 愿望清单中愿望的数量
	 */
	public int getWishCount();
	
	/**
	 * 获取指定位置的愿望
	 * @param position 位置
	 * @return 愿望
	 */
	public Wish getWishAt(int position);

}
