package cn.tencent.DiscuzMob.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BitmapUtil {
//	获得单人圆形头像
	public static Bitmap getSingleCover(String path){
		Bitmap bmp = null;
		Paint paint = new Paint();
		Bitmap bit =  BitmapFactory.decodeFile(path);
		if(bit == null){
			return null;
		} else {
			Bitmap srcBitmap1 = toRoundBitmap(bit);
			if(srcBitmap1 == null) return null;
			bmp = Bitmap.createBitmap(srcBitmap1.getHeight()+10, srcBitmap1.getHeight()+10, Config.ARGB_8888);
			Canvas canvas = new Canvas(bmp);
			canvas.drawColor(Color.TRANSPARENT);
			canvas.drawBitmap(srcBitmap1, 5,5, paint);
			canvas.save(Canvas.ALL_SAVE_FLAG);// 保存
			canvas.restore();// 存储
			srcBitmap1.recycle();
			return getRoundedCornerBitmap(getSamePic(bmp,false), 12);
		}
		
	}
//	获取群组头像
	public static Bitmap getGroupCover(ArrayList<String> paths){
		Bitmap bmp = null;
		Paint paint = new Paint();
		if(paths.size() == 1){
			Bitmap bit =  BitmapFactory.decodeFile(paths.get(0));
			if(bit == null){
				return null;
			}
			bmp = Bitmap.createBitmap(bit.getHeight()+10, bit.getHeight()+10, Config.ARGB_8888);
			Canvas canvas = new Canvas(bmp);
			canvas.drawColor(Color.TRANSPARENT);
			canvas.drawBitmap(bit, 5,5, paint);
			canvas.save(Canvas.ALL_SAVE_FLAG);// 保存
			canvas.restore();// 存储
			bit.recycle();
			bit.recycle();
		}else if(paths.size() == 2){
			Bitmap bit1 =  BitmapFactory.decodeFile(paths.get(0));
			Bitmap bit2 =  BitmapFactory.decodeFile(paths.get(1));
			if(bit1 == null){
				return null;
			}
			if(bit2 == null){
				return null;
			}
			bmp = Bitmap.createBitmap(bit1.getHeight()*2+15, bit1.getHeight()*2+15, Config.ARGB_8888);
			Canvas canvas = new Canvas(bmp);
			canvas.drawColor(Color.TRANSPARENT);
			canvas.drawBitmap(bit1, 5, (bmp.getHeight()-bit1.getHeight())/2, paint);
			canvas.drawBitmap(bit2, bit2.getHeight()+10, (bmp.getHeight()-bit2.getHeight())/2, paint);
			canvas.save(Canvas.ALL_SAVE_FLAG);// 保存
			canvas.restore();// 存储
			bit1.recycle();
			bit2.recycle();
		}else if(paths.size() ==3 ){
			Bitmap bit1 =  BitmapFactory.decodeFile(paths.get(0));
			Bitmap bit2 =  BitmapFactory.decodeFile(paths.get(1));
			Bitmap bit3 =  BitmapFactory.decodeFile(paths.get(2));
			if(bit1 == null){
				return null;
			}
			if(bit2 == null){
				return null;
			}
			if(bit3 == null){
				return null;
			}
			bmp = Bitmap.createBitmap(bit1.getHeight()*2+15, bit1.getHeight()*2+15, Config.ARGB_8888);
			Canvas canvas = new Canvas(bmp);
			canvas.drawColor(Color.TRANSPARENT);
			canvas.drawBitmap(bit1, (bmp.getHeight()-bit1.getHeight())/2, 5, paint);
			canvas.drawBitmap(bit2, 5,bit2.getHeight()+10, paint);
			canvas.drawBitmap(bit3, bit3.getHeight()+10,bit3.getHeight()+10, paint);
			canvas.save(Canvas.ALL_SAVE_FLAG);// 保存
			canvas.restore();// 存储
			bit1.recycle();
			bit2.recycle();
			bit3.recycle();
		}else{
			Bitmap bit1 =  BitmapFactory.decodeFile(paths.get(0));
			Bitmap bit2 =  BitmapFactory.decodeFile(paths.get(1));
			Bitmap bit3 =  BitmapFactory.decodeFile(paths.get(2));
			Bitmap bit4 =  BitmapFactory.decodeFile(paths.get(3));
			if(bit1 == null){
				return null;
			}
			if(bit2 == null){
				return null;
			}
			if(bit3 == null){
				return null;
			}
			if(bit4 == null){
				return null;
			}
			bmp = Bitmap.createBitmap(bit1.getHeight()*2+15, bit1.getHeight()*2+15, Config.ARGB_8888);
			Canvas canvas = new Canvas(bmp);
			canvas.drawColor(Color.TRANSPARENT);
			canvas.drawBitmap(bit1, 5, 5, paint);
			canvas.drawBitmap(bit2, bit2.getHeight()+10, 5, paint);
			canvas.drawBitmap(bit3, 5, bit3.getHeight()+10, paint);
			canvas.drawBitmap(bit4, bit4.getHeight()+10, bit4.getHeight()+10, paint);
			canvas.save(Canvas.ALL_SAVE_FLAG);// 保存
			canvas.restore();// 存储
			bit1.recycle();
			bit2.recycle();
			bit3.recycle();
			bit4.recycle();
		}
		return getRoundedCornerBitmap(getSamePic(bmp,true), 12);
	}
	
	/**
	 *  设置圆角
	 * @param bitmap
	 * @param roundPx 导角幅度
	 * @return
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, final float roundPx) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
//		final float roundPx = 12;
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	/**
	 * 转换图片成圆形
	 * 
	 * @param bitmap
	 *            传入Bitmap对象
	 * @return
	 */
	public static Bitmap toRoundBitmap(Bitmap bitmap) {
		if(bitmap == null ) return null;
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;
		float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
		if (width <= height) {
			roundPx = width / 2;
			top = 0;
			bottom = width;
			left = 0;
			right = width;
			height = width;
			dst_left = 0;
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		} else {
			roundPx = height / 2;
			float clip = (width - height) / 2;
			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;
			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}

		Bitmap aaa = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(aaa);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect src = new Rect((int) left, (int) top, (int) right,	
				(int) bottom);
		final Rect dst = new Rect((int) dst_left, (int) dst_top,
				(int) dst_right, (int) dst_bottom);
		final RectF rectF = new RectF(dst);

		paint.setAntiAlias(true);

		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, src, dst, paint);
		canvas.save();
		return aaa;
	}
	public static Bitmap getSamePic(Bitmap bit,Boolean isGroup ){
		int width = bit.getWidth();
		int height = bit.getHeight();
		int newWidth = 0;
		int newHeight = 0;
		// 设置想要的大小
		if(isGroup){
		 newWidth = 140;
		 newHeight = 140;
		}else{
		 newWidth = 145;
		 newHeight = 145;
		}
		// 计算缩放比例
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 取得想要缩放的matrix参数
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		// 得到新的图片
		return Bitmap.createBitmap(bit, 0, 0, width, height, matrix, true);
	}
	
	public static Bitmap getBitmapAutoFitByPath(String path, int width,
			int height) {
		File f = new File(path);
		Bitmap resizeBmp = null;
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		opts.inPreferredConfig = Config.RGB_565;
		BitmapFactory.decodeFile(f.getPath(), opts);
		float optswidth = opts.outWidth;
		float optsheight = opts.outHeight;
		float wSacle = optswidth / width;
		float hSacle = optsheight / height;
		float sacle = 1f;
		if (wSacle > 1f || hSacle > 1f) {
			sacle = Math.max(wSacle, hSacle);
		}
		opts.inSampleSize = (int) Math.ceil(sacle);
		;
		opts.inJustDecodeBounds = false;
		resizeBmp = BitmapFactory.decodeFile(f.getPath(), opts);
		return resizeBmp;

	}
	public static Bitmap scaleBitmap(Bitmap srcBitmap, int width, int height) {
		if (srcBitmap == null && width<1 && height<1) {
			return null;
		}
		int w = srcBitmap.getWidth();//
		int h = srcBitmap.getHeight();
		if(w>width ||h>height){
			float scaleh  = 1f;
			float scalew  = 1f;
			scaleh = (height*1.0f) / h;//
			scalew = ( width*1.0f) / w;
			float sacle=1f;
			if(scaleh>1f && scaleh>1f){
				sacle=Math.max(scaleh, scalew);
			}else {
				sacle=Math.min(scaleh, scalew);
			}

			Matrix m = new Matrix();//
			m.postScale(sacle, sacle);//
			Bitmap resizedBitmap = Bitmap.createBitmap(srcBitmap, 0, 0, w, h, m, true);//
			return resizedBitmap;
		}else{
			return srcBitmap;
		}

	}
	public static Bitmap decodeSampledBitmapFromResource(String  res, int reqWidth, int reqHeight) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(res, options);
		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(res, options);
	}

	public static int calculateInSampleSize(
			BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			// Calculate ratios of height and width to requested height and width
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			// Choose the smallest ratio as inSampleSize value, this will guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}



}
