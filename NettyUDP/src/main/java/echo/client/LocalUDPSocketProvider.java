package echo.client;

import echo.utils.ConfigEntity;
import echo.utils.Log;

import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 *  * Copyright (C) 2016 鍗虫椂閫氳缃(52im.net) The MobileIMSDK Project.
 *  * All rights reserved.
 *  * Project URL:https://github.com/JackJiang2011/MobileIMSDK
 *  *
 *  * 鍗虫椂閫氳缃(52im.net) - 鍗虫椂閫氳鎶鏈ぞ鍖! PROPRIETARY/CONFIDENTIAL.
 *  * Use is subject to license terms.
 *  *
 *  * LocalUDPSocketProvider.java at 2016-2-20 11:25:57, code by Jack Jiang.
 *  * You can contact author with jack.jiang@52im.net or jb2011@163.com.
 *  *
 */
public class LocalUDPSocketProvider
{
	private static final String TAG = LocalUDPSocketProvider.class.getSimpleName();
	private static LocalUDPSocketProvider instance = null;
	private DatagramSocket localUDPSocket = null;

	public static LocalUDPSocketProvider getInstance()
	{
		if (instance == null)
			instance = new LocalUDPSocketProvider();
		return instance;
	}
	
	public void initSocket()
	{
		try
		{
			//UDP鏈湴鐩戝惉绔彛锛堝鏋滀负0灏嗚〃绀虹敱绯荤粺鍒嗛厤锛屽惁鍒欎娇鐢ㄦ寚瀹氱鍙ｏ級
			this.localUDPSocket = new DatagramSocket(ConfigEntity.localUDPPort);
			/**
			 * 璋冪敤connect涔嬪悗锛屾瘡娆end鏃禗atagramPacket灏变笉闇瑕佽璁＄洰鏍囦富鏈虹殑ip鍜宲ort浜
			 * 娉ㄦ剰锛歝onnect鏂规硶涓瀹氳鍦―atagramSocket.receive()鏂规硶涔嬪墠璋冪敤锛
			 * 涓嶇劧鏁磗end鏁版嵁灏嗕細琚敊璇湴闃诲銆傝繖鎴栬鏄畼鏂笰PI鐨刡ug锛屼篃鎴栬鏄皟
			 * 鐢ㄨ鑼冨氨搴旇杩欐牱锛屼絾娌℃湁鎵惧埌瀹樻柟鏄庣‘鐨勮鏄
			 */
			this.localUDPSocket.connect(
					InetAddress.getByName(ConfigEntity.serverIP), ConfigEntity.serverUDPPort);
			this.localUDPSocket.setReuseAddress(true);
			Log.d(TAG, "new DatagramSocket()宸叉垚鍔熷畬鎴.");
		}
		catch (Exception e)
		{
			Log.w(TAG, "localUDPSocket鍒涘缓鏃跺嚭閿欙紝鍘熷洜鏄細" + e.getMessage(), e);
		}
	}

	public DatagramSocket getLocalUDPSocket()
	{
		return this.localUDPSocket;
	}
}