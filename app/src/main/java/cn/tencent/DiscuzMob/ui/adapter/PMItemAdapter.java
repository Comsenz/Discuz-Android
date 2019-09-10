package cn.tencent.DiscuzMob.ui.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.tencent.DiscuzMob.widget.AsyncRoundedImageView;
import cn.tencent.DiscuzMob.base.RedNet;
import cn.tencent.DiscuzMob.base.RedNetApp;
import cn.tencent.DiscuzMob.manager.SmileManager;
import cn.tencent.DiscuzMob.model.ConversationBean;
import cn.tencent.DiscuzMob.net.AppNetConfig;
import cn.tencent.DiscuzMob.utils.RednetUtils;
import cn.tencent.DiscuzMob.utils.cache.CacheUtils;
import cn.tencent.DiscuzMob.R;

/**
 * Created by kurt on 2015/6/19.
 * 短消息列表
 */
public class PMItemAdapter extends BaseAdapter {

    private Activity context;
    private List<ConversationBean.VariablesBean.ListBean> mMessageList;
    private String formhash;

    public PMItemAdapter(Activity context) {
        this.context = context;
    }

    public void set(List<ConversationBean.VariablesBean.ListBean> messages, String formhash) {
        this.formhash = formhash;
        if (messages != null && !messages.isEmpty()) {
            this.mMessageList = messages;
            notifyDataSetChanged();
        }
    }

    public void append(List<ConversationBean.VariablesBean.ListBean> messages) {
        if (messages != null && !messages.isEmpty()) {
            if (mMessageList == null) {
                mMessageList = messages;
            } else {
                mMessageList.addAll(0, messages);
            }
            notifyDataSetChanged();
        }
    }

    public void cleanDate() {
        if (mMessageList != null) {
            mMessageList.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mMessageList == null ? 0 : mMessageList.size();
    }

    @Override
    public Object getItem(int position) {
        return mMessageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static List<Integer> integers = new ArrayList<>();

    public static int readWord(String input, String word, int offset, int count) {
        offset = input.indexOf(word, offset);
        if (offset != -1) {
            System.out.println(word + " 在第 " + offset + " 个位置出现过.");
            Log.e("TAG", "offset=" + offset);
            readWord(input, word, ++offset, ++count);
            integers.add(offset);
        } else {
            System.out.println(word + " 总共出现了：" + count + " 次.");
        }
        return offset;
    }

    int size;
    List<Drawable> drawables = null;
    List<String> strings = null;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pm_item, parent, false);
            holder.fromView = convertView.findViewById(R.id.pm_item_from);
            holder.mineView = convertView.findViewById(R.id.pm_item_mine);
            holder.mFromAvatar = (AsyncRoundedImageView) convertView.findViewById(R.id.msg_from_preview);
            holder.mFromMessage = (TextView) convertView.findViewById(R.id.pm_from_msg_content);
            holder.mFromDateline = (TextView) convertView.findViewById(R.id.pm_from_msg_dateline);
            holder.mMineAvatar = (AsyncRoundedImageView) convertView.findViewById(R.id.msg_mine_preview);
            holder.mMineMessage = (TextView) convertView.findViewById(R.id.pm_mine_msg_content);
            holder.mMineDateline = (TextView) convertView.findViewById(R.id.pm_mine_msg_dateline);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ConversationBean.VariablesBean.ListBean msg = mMessageList.get(position);
        if (msg.getMsgfromid().equals(RedNetApp.getInstance().getUid())) {
            holder.fromView.setVisibility(View.GONE);
            holder.mineView.setVisibility(View.VISIBLE);
            holder.mMineAvatar.load(msg.getFromavatar(), R.drawable.ic_header_def);
            Pattern p = Pattern.compile("\\#\\[[0-9]{2,3}\\_[0-9]{2,3}\\]");
            if (msg.getMessage() != null) {
                Matcher m = p.matcher(msg.getMessage());
                SpannableStringBuilder builder = new SpannableStringBuilder();
                builder.append(msg.getMessage());
                integers.clear();
                readWord(msg.getMessage(), "#", 0, 0);
                if (m.find()) {
                    size = 8;

                }

                if (integers.size() > 0) {
                    strings = new ArrayList<>();
                    for (int i1 = 0; i1 < integers.size(); i1++) {
                        String substring = msg.getMessage().substring(integers.get(i1) - 1, integers.get(i1) - 1 + size);
                        strings.add(substring);
                    }
                    drawables = new ArrayList<>();
                    for (int i1 = 0; i1 < strings.size(); i1++) {
                        Drawable drawable = pickImg(strings.get(i1));
                        drawables.add(drawable);
                    }
                    for (int i1 = 0; i1 < strings.size(); i1++) {
                        ImageSpan ds = new ImageSpan(drawables.get(i1));
                        builder.setSpan(ds, integers.get(i1) - 1, integers.get(i1) - 1 + size, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }


                holder.mMineMessage.setText(builder);
                holder.mMineDateline.setText(Html.fromHtml(msg.getVdateline()));
            }
        } else {
            holder.mineView.setVisibility(View.GONE);
            holder.fromView.setVisibility(View.VISIBLE);
            Pattern p = Pattern.compile("\\#\\[[0-9]{2,3}\\_[0-9]{2,3}\\]");
            if (msg.getMessage() != null) {

                Matcher m = p.matcher(msg.getMessage());
                List<Drawable> drawables = null;
                SpannableStringBuilder builder = new SpannableStringBuilder();
                builder.append(msg.getMessage());
                integers.clear();
                readWord(msg.getMessage(), "#", 0, 0);
                if (m.find()) {
                    size = 8;
                }
                if (integers.size() > 0) {
                    strings = new ArrayList<>();
                    for (int i1 = 0; i1 < integers.size(); i1++) {
                        String substring = msg.getMessage().substring(integers.get(i1) - 1, integers.get(i1) - 1 + size);
                        strings.add(substring);
                    }
                    drawables = new ArrayList<>();
                    for (int i1 = 0; i1 < strings.size(); i1++) {
                        Drawable drawable = pickImg(strings.get(i1));
                        drawables.add(drawable);
                    }
                    for (int i1 = 0; i1 < integers.size(); i1++) {
                        ImageSpan ds = new ImageSpan(drawables.get(i1));
                        builder.setSpan(ds, integers.get(i1) - 1, integers.get(i1) - 1 + size, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }

                holder.mFromMessage.setText(builder);
//            NetworkImageGetter networkImageGetter = new NetworkImageGetter();
//            holder.mFromMessage.setText(Html.fromHtml(msg.getMessage(), networkImageGetter, null));
                holder.mFromAvatar.load(msg.getFromavatar(), R.drawable.ic_header_def);
                holder.mFromDateline.setText(Html.fromHtml(msg.getVdateline()));

            }
        }
        holder.mFromMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG", "position=" + position);
                new AlertDialog.Builder(context).setTitle("温馨提示")//设置对话框标题

                        .setMessage("确定要删除这条消息吗?")//设置显示的内容

                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮

                            @Override

                            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                delete(position);
                            }

                        }).setNegativeButton("返回", new DialogInterface.OnClickListener() {//添加返回按钮


                    @Override

                    public void onClick(DialogInterface dialog, int which) {//响应事件

                        // TODO Auto-generated method stub

                        dialog.dismiss();

                    }

                }).show();//在按键响应事件中显示此对话框

            }
        });
        holder.mMineMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG", "position=" + position);
                new AlertDialog.Builder(context).setTitle("温馨提示")//设置对话框标题

                        .setMessage("确定要删除这条消息吗?")//设置显示的内容

                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮

                            @Override

                            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
//                                finish();
                                delete(position);
                            }

                        }).setNegativeButton("返回", new DialogInterface.OnClickListener() {//添加返回按钮


                    @Override

                    public void onClick(DialogInterface dialog, int which) {//响应事件

                        // TODO Auto-generated method stub

                        dialog.dismiss();

                    }

                }).show();//在按键响应事件中显示此对话框

            }
        });
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
            }

        }
        Drawable drawable = null;
        if (resId != 0) {
            Bitmap bit = BitmapFactory.decodeResource(context.getResources(), resId);
            drawable = resizeImage(bit, 160, 160);
            if (drawable != null) {
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable
                        .getIntrinsicHeight());
            }
        }
        return drawable;
    }

    private void delete(final int position) {
        RedNet.mHttpClient.newCall(new Request.Builder()
                .addHeader("Cookie", CacheUtils.getString(context, "cookiepre_auth") + ";" + CacheUtils.getString(context, "cookiepre_saltkey") + ";")
                .url(AppNetConfig.DELETEMESSAGE + "&pid=" + mMessageList.get(position).getPmid() + "&id=" + mMessageList.get(position).getTouid() + "&formhash=" + formhash + "&mobiletype=Android")
                .cacheControl(new CacheControl.Builder().noStore().noCache().build()).build())
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "删除失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            Log.e("TAG", "删除=" + jsonObject.toString());
                            if (null != jsonObject) {
                                JSONObject message = jsonObject.getJSONObject("Message");
                                if (null != message) {
                                    final String messagestr = message.getString("messagestr");//中文
                                    String messageval = message.getString("messageval");//英文
                                    if (null != messageval && messageval.contains("success")) {
//                                        load(false);
                                        context.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                mMessageList.remove(position);
                                                notifyDataSetChanged();
                                                RednetUtils.toast(RedNetApp.getInstance(), messagestr);
                                            }
                                        });

                                    } else {
                                        context.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                RednetUtils.toast(RedNetApp.getInstance(), messagestr);
                                            }
                                        });

                                    }
                                } else {
                                    context.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(context, "删除失败", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            } else {
                                context.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(context, "删除失败", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } catch (JSONException e) {
                            context.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context, "删除失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                            e.printStackTrace();
                        }
                    }
                });
    }

    static class ViewHolder {
        private View fromView;
        private View mineView;

        private AsyncRoundedImageView mFromAvatar;
        private TextView mFromMessage;
        private TextView mFromDateline;

        private AsyncRoundedImageView mMineAvatar;
        private TextView mMineMessage;
        private TextView mMineDateline;
    }

    Html.ImageGetter imgGetter = new Html.ImageGetter() {
        @Override
        public Drawable getDrawable(final String source) {


            int resId = android.R.drawable.screen_background_light_transparent;
            if (!TextUtils.isEmpty(source)) {
                for (int i = 0; i < SmileManager.CODE_DECODE.length; i++) {
                    if (source.contains(SmileManager.CODE_DECODE[i])) {
                        resId = SmileManager.EMOJ[i];
                        break;
                    }
                }
            }
            Drawable drawable = null;
            if (resId != 0) {
                Bitmap bit = BitmapFactory.decodeResource(context.getResources(), resId);
                drawable = resizeImage(bit, 140, 140);
                if (drawable != null) {
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable
                            .getIntrinsicHeight());
                }
            }
            return drawable;
        }
    };

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
        // if you want to rotate the Bitmap
        // matrix.postRotate(45);
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
                height, matrix, true);
        return new BitmapDrawable(resizedBitmap);
    }

}
