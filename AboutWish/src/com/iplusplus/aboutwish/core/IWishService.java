package com.iplusplus.aboutwish.core;

import com.iplusplus.aboutwish.Wish;
import com.iplusplus.aboutwish.core.WishService.ResultInfo;

/**
 * 
 * @author Forcs
 * 
 * ��Ҫ��������ṩ�ύ��֧�ֺ�ת��Ը��ʵ��ķ���ӿ�
 *
 */
public interface IWishService {
	
	/**
	 * ��ȡԸ��
	 * @return TODO
	 */
	public ResultInfo requestWishs();
	
	/**
	 * �ύԸ��ʵ��
	 * @param wish Ը��
	 * @return TODO
	 */
	public ResultInfo postWish(Wish wish);
	
	/**
	 * ֧��Ը��ʵ��
	 * @param wish Ը��
	 * @return TODO
	 */
	public ResultInfo postSupport(Wish wish);
	
	/**
	 * ת��Ը��ʵ��
	 * @param wish Ը��
	 * @return TODO
	 */
	public ResultInfo postReplay(Wish wish);

}
