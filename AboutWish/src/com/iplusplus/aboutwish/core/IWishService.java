package com.iplusplus.aboutwish.core;

import com.iplusplus.aboutwish.Wish;

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
	 * @return Ը���嵥
	 */
	public Wish[] getWishs();
	
	/**
	 * �ύԸ��ʵ��
	 * @param wish Ը��
	 * @return �������
	 */
	public int post(Wish wish);
	
	/**
	 * ֧��Ը��ʵ��
	 * @param wish Ը��
	 * @return �������
	 */
	public int support(Wish wish);
	
	/**
	 * ת��Ը��ʵ��
	 * @param wish Ը��
	 * @return �������
	 */
	public int replay(Wish wish);

}
