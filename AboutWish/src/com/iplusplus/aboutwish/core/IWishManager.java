package com.iplusplus.aboutwish.core;

import com.iplusplus.aboutwish.Wish;

public interface IWishManager {
	
	/**
	 * �ύԸ��ʵ��
	 * @param wish Ը��
	 * @return TODO
	 */
	public boolean post(Wish wish);
	
	/**
	 * ֧��Ը��ʵ��
	 * @param wish Ը��
	 * @return ���µ�֧������
	 */
	public int support(Wish wish);
	
	/**
	 * ת��Ը��ʵ��
	 * @param wish Ը��
	 * @return ���µ�ת������
	 */
	public int replay(Wish wish);
	
	/**
	 * Ը���嵥��Ը��������
	 * @return Ը���嵥��Ը��������
	 */
	public int getWishCount();
	
	/**
	 * ��ȡָ��λ�õ�Ը��
	 * @param position λ��
	 * @return Ը��
	 */
	public Wish getWishAt(int position);

}
