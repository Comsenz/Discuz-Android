package cn.tencent.DiscuzMob.enums;
/**
 * 转成人性化时间的集中常用显示方式
 * larry create on 2014-6-20
 */
public enum TimeStyle {
	
	/**
	 * 展示方式：1、今天；2、昨天；3、M月d日；4、yyyy年M月d日 HH:mm
	 */
	TIMEA(0),
	/**
	 * 展示方式：1、HH：mm；2、昨天 HH：mm；3、M月d日 HH：mm；4、yyyy年M月d日 HH:mm
	 */
	TIMEB(1),
	/**
	 * 展示方式：1、今天 HH：mm；2、昨天 HH：mm；3、M月d日 HH：mm；4、yyyy年M月d日 HH:mm
	 */
	TIMEC(2),
	/**
	 * 展示方式：1、今天；2、昨天；3、M月d日；4、yyyy年M月d日 
	 */
	TIMED(3);
	
	private int value;
	
	private TimeStyle(int value){
		this.value = value;
	}
	
	public int getValue(){
		return value;
	}
}
