package cn.tencent.DiscuzMob.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.tencent.DiscuzMob.ui.activity.ShowPhotoActivity;
import cn.tencent.DiscuzMob.utils.DateUtils;
import cn.tencent.DiscuzMob.utils.RedNetPreferences;
import cn.tencent.DiscuzMob.manager.SmileManager;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.utils.BitmapUtil;
import cn.tencent.DiscuzMob.R;

/**
 * Created by cg on 2017/5/17.
 */

public class LiveAdapter extends BaseAdapter {
    JSONArray jsonObjects = new JSONArray();
    private String data;
    private ArrayList<String> imgLists = new ArrayList<>();
    private Activity activity;
    private int size;
    private ArrayList<String> strings = null;
    List<Drawable> drawables = null;

    public LiveAdapter(FragmentActivity activity) {
        this.activity = activity;
    }

    public void clearData() {
        jsonObjects.clear();
        notifyDataSetChanged();
    }


    public void addData(JSONArray jsonObjects) {
        this.jsonObjects.addAll(jsonObjects);
        notifyDataSetChanged();
    }

    public void setData(JSONArray jsonObjects) {
        this.jsonObjects.clear();
        this.jsonObjects.addAll(jsonObjects);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return jsonObjects == null ? 0 : jsonObjects.size();
    }

    @Override
    public Object getItem(int position) {
        return jsonObjects == null ? null : jsonObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private List<String> uris;
    static List<Integer> integers = new ArrayList<>();

    public static int readWord(String input, String word, int offset, int count) {
        offset = input.indexOf(word, offset);
        if (offset != -1) {
            readWord(input, word, ++offset, ++count);
            integers.add(offset);
        } else {
        }
        return offset;
    }

    private List<String> htmlImg = new ArrayList<>();

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(parent.getContext(), R.layout.live_item, null);
            viewHolder.tv_subject = (TextView) convertView.findViewById(R.id.tv_subject);
            viewHolder.tv_data = (TextView) convertView.findViewById(R.id.tv_data);
            viewHolder.tv_photoCount = (TextView) convertView.findViewById(R.id.tv_photoCount);
            viewHolder.ll_img = (RelativeLayout) convertView.findViewById(R.id.ll_img);
            viewHolder.iv_img1 = (ImageView) convertView.findViewById(R.id.iv_img1);
            viewHolder.iv_img2 = (ImageView) convertView.findViewById(R.id.iv_img2);
            viewHolder.iv_img3 = (ImageView) convertView.findViewById(R.id.iv_img3);
            viewHolder.iv_img1.setScaleType(ImageView.ScaleType.FIT_XY);
            viewHolder.iv_img2.setScaleType(ImageView.ScaleType.FIT_XY);
            viewHolder.iv_img3.setScaleType(ImageView.ScaleType.FIT_XY);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final JSONObject jsonObject = (JSONObject) jsonObjects.get(position);
        Long dateline = jsonObject.getLong("dbdateline");
        String time = DateUtils.getTime(dateline);
        String s = DateUtils.timeslashData(String.valueOf(dateline));

        if (data != null && data.equals(s)) {
            viewHolder.tv_data.setVisibility(View.GONE);
        } else {
            viewHolder.tv_data.setVisibility(View.VISIBLE);
            viewHolder.tv_data.setText(s);
            data = s;
        }
        String message = jsonObject.getString("message");
        String str = time + " ";
        SpannableStringBuilder builder = new SpannableStringBuilder(str);
        builder.setSpan(new BackgroundColorSpan(Color.parseColor("#90ccff")), 0, time.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//        Spanned spanned = Html.fromHtml(message);
        builder.append(Html.fromHtml(message));
        if (message.contains("http")) {
            integers.clear();
            readWord(str + message, "<", 0, 0);
            htmlImg.clear();
            String[] split = message.split("/>");
            for (int i1 = 0; i1 < split.length; i1++) {
                if (split[i1].contains("src")) {
                    String substring = split[i1].substring(10, split[i1].length() - 2);
                    htmlImg.add(substring);
                }
            }
            drawables = new ArrayList<>();
            for (int j = 0; j < htmlImg.size(); j++) {
                Drawable drawable = pickImg(htmlImg.get(j));
                drawables.add(drawable);
            }
            for (int i1 = 0; i1 < integers.size(); i1++) {
                Drawable drawable = drawables.get(i1);
                if (drawable != null) {
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth() / 2, drawable.getIntrinsicHeight() / 2);
                    ImageSpan ds = new ImageSpan(drawable);
                    Collections.sort(integers);
                    builder.setSpan(ds, integers.get(i1) - 1, integers.get(i1) , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }

            }
        }
        Pattern p = Pattern.compile("\\#\\[[0-9]{2,3}\\_[0-9]{2,3}\\]");
        Matcher m = p.matcher(message);
        if (m.find()) {
            size = 8;
        }
        integers.clear();
        readWord(str + message, "#", str.length(), 0);
        if (integers.size() > 0) {
            strings = new ArrayList<>();
            for (int i1 = 0; i1 < integers.size(); i1++) {
                String substring = (str + message).substring(integers.get(i1) - 1, integers.get(i1) - 1 + size);
                strings.add(substring);
            }
            drawables = new ArrayList<>();
            for (int i1 = 0; i1 < strings.size(); i1++) {
                Drawable drawable = pickImg(strings.get(i1));

                drawables.add(drawable);
            }
            for (int i1 = 0; i1 < integers.size(); i1++) {
                Drawable drawable = drawables.get(i1);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth() / 2, drawable.getIntrinsicHeight() / 2);
                ImageSpan ds = new ImageSpan(drawable);
                builder.setSpan(ds, integers.get(i1) - 1, integers.get(i1) - 1 + size, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        viewHolder.tv_subject.setText(builder);
        JSONObject attachments = jsonObject.getJSONObject("attachments");
        JSONArray imagelist = jsonObject.getJSONArray("imagelist");
        if (null != imagelist && imagelist.size() > 0 && RedNetPreferences.getImageSetting() != 1) {
            imgLists.clear();
            if (!((String) imagelist.get(0)).contains("filename")) {
                if (uris != null) {
                    uris = null;
                }
                for (int i = 0; i < imagelist.size(); i++) {
                    JSONObject object = attachments.getJSONObject(imagelist.get(i) + "");
                    String url = object.getString("url");
                    String attachment = object.getString("attachment");
                    String imgUrl = AppNetConfig.IMGURL1 + url + attachment;
                    imgLists.add(imgUrl);
                }
            } else {
                uris = new ArrayList<>();
                for (int i = 0; i < imagelist.size(); i++) {
                    JSONObject object = attachments.getJSONObject(imagelist.get(i) + "");
                    String attachment = object.getString("attachment");
                    uris.add(attachment);
                }
            }

            if (imagelist.size() > 0 && RedNetPreferences.getImageSetting() != 1) {
                viewHolder.ll_img.setVisibility(View.VISIBLE);
                if (imagelist.size() == 1) {
                    viewHolder.iv_img1.setVisibility(View.VISIBLE);
                    viewHolder.iv_img2.setVisibility(View.GONE);
                    viewHolder.iv_img3.setVisibility(View.GONE);
                    viewHolder.tv_photoCount.setVisibility(View.GONE);
                    ;
                    if (uris != null && uris.size() == 1) {
                        Bitmap bit = BitmapUtil.decodeSampledBitmapFromResource(uris.get(0), 50, 50);
                        viewHolder.iv_img1.setImageBitmap(bit);
                    } else {
                        Picasso.with(parent.getContext())
                                .load(imgLists.get(0))
                                .into(viewHolder.iv_img1);
                    }


                } else if (imagelist.size() == 2) {
                    viewHolder.iv_img1.setVisibility(View.VISIBLE);
                    viewHolder.iv_img2.setVisibility(View.VISIBLE);
                    viewHolder.iv_img3.setVisibility(View.GONE);
                    viewHolder.tv_photoCount.setVisibility(View.GONE);
                    if (uris != null && uris.size() == 2) {
                        Bitmap bitmap1 = BitmapUtil.decodeSampledBitmapFromResource(uris.get(0), 50, 50);
                        Bitmap bitmap2 = BitmapUtil.decodeSampledBitmapFromResource(uris.get(1), 50, 50);
                        viewHolder.iv_img1.setImageBitmap(bitmap1);
                        viewHolder.iv_img2.setImageBitmap(bitmap2);

                    } else {
                        Picasso.with(parent.getContext())
                                .load(imgLists.get(0))
                                .into(viewHolder.iv_img1);
                        Picasso.with(parent.getContext())
                                .load(imgLists.get(1))
                                .into(viewHolder.iv_img2);
                    }


                } else if (imagelist.size() == 3) {
                    viewHolder.iv_img1.setVisibility(View.VISIBLE);
                    viewHolder.iv_img2.setVisibility(View.VISIBLE);
                    viewHolder.iv_img3.setVisibility(View.VISIBLE);
                    viewHolder.tv_photoCount.setVisibility(View.GONE);
                    if (uris != null && uris.size() == 3) {
                        Bitmap bitmap1 = BitmapUtil.decodeSampledBitmapFromResource(uris.get(0), 50, 50);
                        Bitmap bitmap2 = BitmapUtil.decodeSampledBitmapFromResource(uris.get(1), 50, 50);
                        Bitmap bitmap3 = BitmapUtil.decodeSampledBitmapFromResource(uris.get(2), 50, 50);
                        viewHolder.iv_img1.setImageBitmap(bitmap1);
                        viewHolder.iv_img2.setImageBitmap(bitmap2);
                        viewHolder.iv_img3.setImageBitmap(bitmap3);
                    } else {
                        Picasso.with(parent.getContext())
                                .load(imgLists.get(0))
                                .into(viewHolder.iv_img1);
                        Picasso.with(parent.getContext())
                                .load(imgLists.get(1))
                                .into(viewHolder.iv_img2);
                        Picasso.with(parent.getContext())
                                .load(imgLists.get(2))
                                .into(viewHolder.iv_img3);
                    }

                } else {
                    viewHolder.iv_img1.setVisibility(View.VISIBLE);
                    viewHolder.iv_img2.setVisibility(View.VISIBLE);
                    viewHolder.iv_img3.setVisibility(View.VISIBLE);
                    viewHolder.tv_photoCount.setVisibility(View.VISIBLE);
                    if (uris != null && uris.size() > 3) {
                        Bitmap bitmap1 = BitmapUtil.decodeSampledBitmapFromResource(uris.get(0), 50, 50);
                        Bitmap bitmap2 = BitmapUtil.decodeSampledBitmapFromResource(uris.get(1), 50, 50);
                        Bitmap bitmap3 = BitmapUtil.decodeSampledBitmapFromResource(uris.get(2), 50, 50);
                        viewHolder.iv_img1.setImageBitmap(bitmap1);
                        viewHolder.iv_img2.setImageBitmap(bitmap2);
                        viewHolder.iv_img3.setImageBitmap(bitmap3);
                    } else {
                        Picasso.with(parent.getContext())
                                .load(imgLists.get(0))
                                .into(viewHolder.iv_img1);
                        Picasso.with(parent.getContext())
                                .load(imgLists.get(1))
                                .into(viewHolder.iv_img2);
                        Picasso.with(parent.getContext())
                                .load(imgLists.get(2))
                                .into(viewHolder.iv_img3);
                    }

                    int i = imagelist.size() - 3;
                    viewHolder.tv_photoCount.setText("+" + i);
                }

                viewHolder.iv_img1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("TAG", "position=" + position);
                        Intent intent = new Intent(activity, ShowPhotoActivity.class);
                        intent.putExtra("position", 0);
                        intent.putExtra("jsonObject", jsonObject.toString());
                        intent.putExtra("form", "live");
                        parent.getContext().startActivity(intent);
                    }
                });
                viewHolder.iv_img2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("TAG", "position=" + position);
                        Intent intent = new Intent(activity, ShowPhotoActivity.class);
                        intent.putExtra("position", 1);
                        intent.putExtra("jsonObject", jsonObject.toString());
                        intent.putExtra("form", "live");
                        parent.getContext().startActivity(intent);
                    }
                });

                viewHolder.iv_img3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("TAG", "position=" + position);
                        Intent intent = new Intent(activity, ShowPhotoActivity.class);
                        intent.putExtra("position", 2);
                        intent.putExtra("jsonObject", jsonObject.toString());
                        intent.putExtra("form", "live");
                        parent.getContext().startActivity(intent);
                    }
                });


            } else {
                viewHolder.ll_img.setVisibility(View.GONE);
            }

        } else {
            viewHolder.ll_img.setVisibility(View.GONE);
        }


        return convertView;
    }

    private Drawable pickImg(String s) {
        int resId = android.R.drawable.screen_background_light_transparent;
        if (!TextUtils.isEmpty(s)) {
            if (s.contains("{")) {
                for (int i = 0; i < SmileManager.CODE_DECODE1.length; i++) {
                    if (s.contains(SmileManager.CODE_DECODE1[i])) {
                        resId = SmileManager.EMOJ[i];
                        break;
                    }
                }
            } else if (s.contains("#[")) {
                for (int i = 0; i < SmileManager.CODE_DECODE.length; i++) {
                    if (s.contains(SmileManager.CODE_DECODE[i])) {
                        resId = SmileManager.EMOJ[i];
                        break;
                    }
                }
            } else if (s.contains("http")) {
                for (int i = 0; i < SmileManager.CODE_DECODE2.length; i++) {
                    if (s.contains(SmileManager.CODE_DECODE2[i])) {
                        resId = SmileManager.EMOJ[i];
                        break;
                    }
                }
            }

        }
        Drawable drawable = null;
        if (resId != 0) {
            Bitmap bit = BitmapFactory.decodeResource(activity.getResources(), resId);
            drawable = resizeImage(bit, 140, 140);
            if (drawable != null) {
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable
                        .getIntrinsicHeight());
            }
        }
        return drawable;
    }

    public static Drawable resizeImage(Bitmap bitmap, int w, int h) {
        if (bitmap == null) {
            return null;
        }
        Bitmap BitmapOrg = bitmap;
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
                height, matrix, true);
        return new BitmapDrawable(resizedBitmap);
    }

    class ViewHolder {
        TextView tv_subject, tv_data, tv_photoCount;
        RelativeLayout ll_img;
        private ImageView iv_img1, iv_img2, iv_img3;
    }
}
